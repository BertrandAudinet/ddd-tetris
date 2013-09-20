TetrisPresenterTest = TestCase("TetrisPresenterTest");

TetrisPresenterTest.prototype.testInit = function() {
	var aView = new TetrisView();

	TetrisView.prototype.pushStartHandler = null;

	aView.addPushStartHandler = function(pushStartHandler) {
		this.pushStartHandler = pushStartHandler;
	};

	var aModel = new TetrisModel();
	var presenter = new TetrisPresenter(aView, aModel);

	presenter.init();

	assertNotNull(
			'Given a presenter when is initialized then a push start handler is added to the view',
			aView.pushStartHandler);
};

TetrisPresenterTest.prototype.testPushStart = function() {
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

	assertTrue(
			'Given a presenter when you push start then the grid is cleared',
			aView.isClear);
};

TetrisPresenterTest.prototype.testOnRotatePiece = function() {
	var aView = new TetrisView();
	var aModel = new TetrisModel();
	var presenter = new TetrisPresenter(aView, aModel);

	TetrisModel.prototype.isRotatePieceCalled = false;
	aModel.rotatePiece = function() {
		this.isRotatePieceCalled = true;
	};

	presenter.onRotatePiece();

	assertTrue(
			'Given a started tetris when you rotate piece then the model is called',
			aModel.isRotatePieceCalled);
};
