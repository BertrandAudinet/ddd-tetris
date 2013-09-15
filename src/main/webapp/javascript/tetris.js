/**
 * @constructor
 */
function TetrisView() {
};

TetrisView.prototype.addPushStartHandler = function(handlePushStart) {
};

TetrisView.prototype.addMoveLeftHandler = function(handleMoveLeft) {
};

TetrisView.prototype.addMoveRightHandler = function(handleMoveRight) {
};

TetrisView.prototype.addDropPieceHandler = function(handleDropPiece) {
};

TetrisView.prototype.addRotatePieceHandler = function(handleRotatePiece) {
};

TetrisView.prototype.clearGrid = function() {
};

TetrisView.prototype.hideShape = function() {
};

TetrisView.prototype.displayStart = function() {
};

TetrisView.prototype.displayPause = function() {
};

TetrisView.prototype.displayPiece = function() {
};

/**
 * @constructor
 */
function TetrisModel() {
};

/**
 * 
 * @returns {String}
 */
TetrisModel.prototype.playNewTetris = function() {
	return "";
};

TetrisModel.prototype.start = function() {
};

TetrisModel.prototype.addTetrisEventHandler = function() {
};

TetrisModel.prototype.movePiece = function(direction) {
};

TetrisModel.prototype.rotatePiece = function(direction) {
};

TetrisModel.prototype.dropPiece = function() {
};

TetrisModel.prototype.getEvent = function(tetrisId, lastEventId) {
};

/**
 * @constructor
 * @param {TetrisView}
 *            view
 * @Param {TetrisModel}
 *            model
 */
function TetrisPresenter(view, model) {
	this.view = view;
	this.model = model;
	this.tetrisId = null;
	this.lastEventId = 0;
};

TetrisPresenter.prototype.init = function() {
	var presenter = this;
	this.view.addPushStartHandler(function() {
		presenter.pushStart();
	});
	this.view.addMoveLeftHandler(function() {
		presenter.onMoveLeft();
	});
	this.view.addMoveRightHandler(function() {
		presenter.onMoveRight();
	});
	this.view.addDropPieceHandler(function() {
		presenter.onDropPiece();
	});
	this.view.addRotatePieceHandler(function() {
		presenter.onRotatePiece();
	});
	this.model.addTetrisEventHandler(function(event) {
		presenter.onTetrisEvent(event);
	});
};

TetrisPresenter.prototype.pushStart = function() {
	this.tetrisId = this.model.playNewTetris();
	this.view.clearGrid();
	this.view.hideShape();
	this.model.start();
	var presenter = this;
	var timer = $.timer(function() {
		presenter.model.getEvent(presenter.tetrisId, presenter.lastEventId);
		timer.once(500);
	}, 500, false);
	timer.once(500);

};

TetrisPresenter.prototype.onMoveLeft = function() {
	this.model.movePiece('LEFT');
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onMoveRight = function() {
	this.model.movePiece('RIGHT');
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onRotatePiece = function() {
	this.model.rotatePiece('RIGHT');
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onDropPiece = function() {
	this.model.dropPiece();
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onTetrisEvent = function(event) {
	var type = event.type;
	this.lastEventId = event.lastEventId;
	if (type == "TETRIS_STARTED") {
		this.onTetrisStarted(event);
	} else if (type == "TETRIS_PIECE_MOVED") {
		this.movePiece(event);
	} else if (type == "TETRIS_PIECE_ROTATED") {
		this.movePiece(event);
	} else if (type == "TETRIS_PIECE_DROPPED") {
		this.movePiece(event);
	} else {
		console.log("Event [type=" + event.type + "]");
	}
};

TetrisPresenter.prototype.onTetrisStarted = function(event) {
	if (event.started) {
		this.view.displayStart();
	} else {
		this.view.displayPause();
	}
};

TetrisPresenter.prototype.movePiece = function(event) {
	this.view.hideShape();
	this.view.displayPiece(event.piece);
};