aTest = TestCase("aTest");

aTest.prototype.testInit = function() {
	
	// Load the template with jQuery
	$.ajax({
		type : 'GET',
		url : '/test/src/main/webapp/template/tetris-template.html',
		dataType : 'html',
		async : false,
		success : 
            function ( data, textStatus, jqXHR) {
				var content = jQuery('<div class="tetrisView"></div>').append( jQuery.parseHTML( data ) ).find(".tetris");
				$(document).find("body").html(content);
        },
        error : 
            function (jqXHR, textStatus, errorThrown) {
                fail("Data did not load.");
        },
	});
	
	var view = new JQueryTetrisView($('.tetrisView'), true);
	assertNotNull(view);

};