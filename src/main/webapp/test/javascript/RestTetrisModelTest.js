jQuery(function($) {

	module('RestTetrisModelTest',{
	    setup: function() {
	    }
	});
	
	test('Given a model when is play new tetris then a tetris id is returned', function() {
		var model = new RestTetrisModel();
		
		var tetrisId = model.playNewTetris();
		
		notEqual(null, tetrisId);
	});

	test('Given a tetris when is started then a ', function() {
		var model = new RestTetrisModel();
		model.playNewTetris();
		
		var actual = null;
		model.addTetrisEventHandler(function(event) {
			actual = event;
			equals(actual, "");
		});
		model.start();
		
		equal(actual, "");
		
	});
	
});
