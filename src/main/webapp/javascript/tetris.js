/**
 * @constructor
 */
function TetrisView() {
};

TetrisView.prototype.addPushStartHandler = function(handlePushStart) {
};

TetrisView.prototype.addMoveLeftHandler = function(handleMoveLeft) {
};

TetrisView.prototype.clearGrid = function() {
};

TetrisView.prototype.hideShape = function() {
};

TetrisView.prototype.displayStart = function() {
};

TetrisView.prototype.displayPause = function() {
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

TetrisModel.prototype.getEvent = function(tetrisId, lastEventId) {
};

/**
 * @constructor
 * @param {TetrisView} view
 * @Param {TetrisModel} model
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
	this.model.addTetrisEventHandler(function(event) {
		presenter.onTetrisEvent(event);
	});
};

TetrisPresenter.prototype.pushStart = function() {
	this.tetrisId = this.model.playNewTetris();
	this.view.clearGrid();
	this.view.hideShape();
	this.model.start();
};

TetrisPresenter.prototype.onMoveLeft = function() {
	this.model.movePiece('LEFT');
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onMoveRight = function() {
	this.model.movePiece('RIGHT');
	this.model.getEvent(this.tetrisId, this.lastEventId);
};

TetrisPresenter.prototype.onTetrisEvent = function(event) {
	var type = event.type;
	this.lastEventId = event.lastEventId;
	if (type == "TETRIS_STARTED") {
		this.onTetrisStarted(event);
	} else {
		console.log("Event [type="+event.type+"]");
	}
};

TetrisPresenter.prototype.onTetrisStarted = function(event) {
	if (event.started) {
		this.view.displayStart();
	} else {
		this.view.displayPause();
	}
};

TetrisPresenter.prototype.onRotatePiece = function(direction) {
	this.model.rotatePiece(tetrisId, direction);
};
