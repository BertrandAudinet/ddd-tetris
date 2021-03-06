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

TetrisView.prototype.clearLine = function(line) {
};

TetrisView.prototype.hideShape = function() {
};

TetrisView.prototype.displayStart = function() {
};

TetrisView.prototype.displayPause = function() {
};

TetrisView.prototype.displayPiece = function() {
};

TetrisView.prototype.displayBlock = function(x, y, tetromino) {
};

TetrisView.prototype.displayScore = function(level, lines, points) {
};

TetrisView.prototype.scoreUp = function(level, lines, points) {
};

TetrisView.prototype.levelUp = function(level) {
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

TetrisModel.prototype.getEvent = function() {
};

TetrisModel.prototype.getBoard = function(tetrisId) {
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

	this.view.clearGrid();
	this.view.hideShape();

};

TetrisPresenter.prototype.pushStart = function() {
	this.model.playNewTetris();
	this.model.start();
};

TetrisPresenter.prototype.onMoveLeft = function() {
	this.model.movePiece('LEFT');
	this.model.getEvent();
};

TetrisPresenter.prototype.onMoveRight = function() {
	this.model.movePiece('RIGHT');
	this.model.getEvent();
};

TetrisPresenter.prototype.onRotatePiece = function() {
	this.model.rotatePiece('RIGHT');
	this.model.getEvent();
};

TetrisPresenter.prototype.onDropPiece = function() {
	this.model.dropPiece();
	this.model.getEvent();
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
		this.dropPiece(event);
	} else if (type == "TETRIS_PIECE_LOCKED") {
		this.loadGrid(event);
	} else if (type == "TETRIS_PENALTY_LINE_RECEIVED") {
		this.reLoadGrid(event);
	} else if (type == "TETRIS_SCORE_CHANGED") {
		this.changeScore(event);
	} else if (type == "TETRIS_LINE_CLEARED") {
		this.clearLine(event);
	} else if (type == "TETRIS_LEVELUP_PERFORMED") {
		this.levelUpPerformed(event);
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

TetrisPresenter.prototype.dropPiece = function(event) {
	if (event.newPiece) {
		var board = this.model.getBoard();
		this.view.clearGrid();
		this.view.hideShape();
		var blocks = board.blocks;
		if (blocks) {
			for ( var i = 0; i < blocks.length; i++) {
				var block = blocks[i];
				this.view.displayBlock(block.x, block.y, block.tetromino);
			}
		}
		var score = board.score;
		this.view.displayScore(score.level, score.lines, score.points);
	}
	this.movePiece(event);
};

TetrisPresenter.prototype.movePiece = function(event) {
	this.view.hideShape();
	this.view.displayPiece(event.piece);
};

TetrisPresenter.prototype.changeScore = function(event) {
	var score = event.score;
	this.view.scoreUp(score.level, score.lines, score.points);
};

TetrisPresenter.prototype.levelUpPerformed = function(event) {
	this.view.levelUp(event.level);
};

TetrisPresenter.prototype.clearLine = function(event) {
	this.view.clearLine(event.clearLine);
};

TetrisPresenter.prototype.loadGrid = function(event) {
	var piece = event.piece;
	var blocks = piece.blocks;
	for ( var i = 0; i < blocks.length; i++) {
		var block = blocks[i];
		this.view.displayBlock(block.x, block.y, block.tetromino);
	}
};

TetrisPresenter.prototype.reLoadGrid = function(event) {
	var board = this.model.getBoard();
	var blocks = board.blocks;
	this.view.clearGrid();
	if (blocks) {
		for ( var i = 0; i < blocks.length; i++) {
			var block = blocks[i];
			this.view.displayBlock(block.x, block.y, block.tetromino);
		}
	}
	var score = board.score;
	this.view.displayScore(score.level, score.lines, score.points);
};
