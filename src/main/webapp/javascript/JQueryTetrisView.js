/**
 * @constructor
 */
function JQueryTetrisView(html, isActive) {
	// Call the parent constructor
	TetrisView.call(this);
	this.component = jQuery(html);
	this.pushStartButton = this.component.find(".pushStart");
	this.grid = this.component.find(".board .grid");
	this.shapes = this.component.find(".board .shape");
	this.score = $(this.component.find(".scoreUp"));
	this.isActive = isActive;
	this.keyDowns = {};
	var cssPadding = this.grid.parent().css("padding-left");
	this.padding = 1 * cssPadding.substring(0, cssPadding.length-2);
};

// inherit TetrisView
JQueryTetrisView.prototype = new TetrisView();

// correct the constructor pointer because it points to JQueryTetrisView
JQueryTetrisView.prototype.constructor = JQueryTetrisView;

// replace all methods of TetrisView
JQueryTetrisView.prototype.addPushStartHandler = function(pushStartHandler) {
	this.pushStartButton.click(pushStartHandler);
};

JQueryTetrisView.prototype.onKey = function(event, handler) {
	var lastTimeStamp = 0;
	if (this.keyDowns[event.which] != undefined) {
		lastTimeStamp = this.keyDowns[event.which];
	}
	if (event.timeStamp - lastTimeStamp >= refreshTime*2) {
		this.keyDowns[event.which] = event.timeStamp;
		handler();
	}
};

JQueryTetrisView.prototype.addMoveLeftHandler = function(moveLeftHandler) {
	var view = this;
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 37) {
				view.onKey(event, moveLeftHandler);
				event.preventDefault();
			}
		});
		jQuery(document).keyup(function(event) {
			if (event.which == 37) {
				view.keyDowns[event.which] = 0;
			}
		});
	}
};

JQueryTetrisView.prototype.addMoveRightHandler = function(moveRightHandler) {
	var view = this;
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 39) {
				view.onKey(event, moveRightHandler);
				event.preventDefault();
			}
		});
		jQuery(document).keyup(function(event) {
			if (event.which == 39) {
				view.keyDowns[event.which] = 0;
			}
		});

	}
};

JQueryTetrisView.prototype.addDropPieceHandler = function(dropPieceHandler) {
	var view = this;
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 40) {
				view.onKey(event, dropPieceHandler);
				event.preventDefault();
			}
		});
		jQuery(document).keyup(function(event) {
			if (event.which == 40) {
				view.keyDowns[event.which] = 0;
			}
		});

	}
};

JQueryTetrisView.prototype.addRotatePieceHandler = function(rotatePieceHandler) {
	var view = this;
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 38) {
				view.onKey(event, rotatePieceHandler);
				event.preventDefault();
			}
		});
		jQuery(document).keyup(function(event) {
			if (event.which == 38) {
				view.keyDowns[event.which] = 0;
			}
		});

	}
};

JQueryTetrisView.prototype.clearGrid = function() {
	this.grid.find("td").removeClass(
			'IShape OShape TShape JShape lShape SShape ZShape PShape');
};

JQueryTetrisView.prototype.hideShape = function() {
	this.shapes.hide();
};

JQueryTetrisView.prototype.displayStart = function() {
	console.log("JQueryTetrisView.displayStart()");
};

JQueryTetrisView.prototype.displayPause = function() {
	console.log("JQueryTetrisView.displayPause()");
};

JQueryTetrisView.prototype.displayPiece = function(piece) {
	console.log("JQueryTetrisView.displayPiece()");

	var currentPiece = this.shapes.parent().find(
			'#' + piece.tetromino + 'Shape');

	var y = piece.y * this.padding/2 + this.padding;
	var x = piece.x * this.padding/2 + this.padding;
	currentPiece.css({
		left : x + 'px',
		top : y + 'px',
	});
	currentPiece.removeClass("rotate0 rotate1 rotate2 rotate3");
	currentPiece.addClass("rotate" + piece.rotation);
	currentPiece.show();
};

JQueryTetrisView.prototype.displayBlock = function(x, y, tetromino) {
	console.log("JQueryTetrisView.displayGrid");

	var block = $(this.grid.find('td')[y * 10 + x]);
	block.addClass(tetromino + 'Shape');
};

JQueryTetrisView.prototype.displayScore = function(level, lines, points) {
	$(".scoreChanged").remove();
	this.score.append('<span class="score">+' + points + '</span>');
	$(".score").delay(500).addClass("scoreChanged");
};