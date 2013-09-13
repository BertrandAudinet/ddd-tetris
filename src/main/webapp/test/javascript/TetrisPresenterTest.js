jQuery(function($) {

	module('TetrisPresenterTest',{
	    setup: function() {
	    }
	});
	
	test('Given a presenter when is initialized then a push start handler is added to the view', function() {
		var aView = new TetrisView();

		TetrisView.prototype.pushStartHandler = null;

		aView.addPushStartHandler = function(pushStartHandler) {
			this.pushStartHandler = pushStartHandler;
		};
		
		var aModel = new TetrisModel();
		var presenter = new TetrisPresenter(aView, aModel);

		presenter.init();
		
		notEqual(null, aView.pushStartHandler);
	});

	test('Given a presenter when you push start then the grid is cleared', function() {
		var aView = new TetrisView();
		
		TetrisView.prototype.isClear = false;
		
		aView.clearGrid = function() {
			this.isClear = true;
		};
		
		var aModel = new TetrisModel();
		aModel.playNewTetris = function() {
			return "T1";
		};
		var presenter = new TetrisPresenter(aView, aModel);
		
		presenter.pushStart();
		
		equal(true, aView.isClear);
	});
	
	test('Given a started tetris when you rotate piece then the model is called', function() {
		var aView = new TetrisView();
		var aModel = new TetrisModel();
		var presenter = new TetrisPresenter(aView, aModel);
		
		TetrisModel.prototype.isRotatePieceCalled = false;
		aModel.rotatePiece = function() {
			this.isRotatePieceCalled = true;
		};
		
		presenter.onRotatePiece();
		
		equal(aModel.isRotatePieceCalled, true);
	});
	
});
