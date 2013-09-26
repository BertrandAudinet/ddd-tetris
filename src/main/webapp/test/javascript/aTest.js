aTest = TestCase("aTest");

aTest.prototype.setUp = function() {
	// Load the template with jQuery
	$.ajax({
		type : 'GET',
		url : '/test/src/main/webapp/template/tetris-template.html',
		dataType : 'html',
		async : false,
		success : 
            function ( data, textStatus, jqXHR) {
				var content = jQuery('<div class="tetrisView"></div>').append( jQuery.parseHTML( data ) ); //.find(".tetris");
				//$(document).find("body").html(content);
				$(document).find("body").append(content);
        },
        error : 
            function (jqXHR, textStatus, errorThrown) {
                fail("Data did not load.");
        },
	});
	
	
};

aTest.prototype.testInit = function() {
	var view = new JQueryTetrisView($('.tetrisView'), true);
	assertNotNull(view);

};

aTest.prototype.testView_Init_LevelUpIsHidden = function() {
	var view = new JQueryTetrisView($('.tetrisView'), true);
	
	var actual = $('#levelUp').css('display');
	assertEquals('none', actual);
};

aTest.prototype.testInitView_LevelUp_DivIsDisplay = function() {
	var view = new JQueryTetrisView($('.tetrisView'), true);
	
	view.levelUp(1);
	
	var actual = $('#levelUp').css('display');
	assertEquals('block', actual);

};