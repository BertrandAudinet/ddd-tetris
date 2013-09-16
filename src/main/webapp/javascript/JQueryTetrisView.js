/**
 * @constructor
 */
function JQueryTetrisView(html) {
	// Call the parent constructor
	TetrisView.call(this);
	this.component = jQuery(html);
	this.pushStartButton = this.component.find(".pushStart");
	this.grid = this.component.find(".board .grid");
	this.shapes = this.component.find(".board .shape");
	this.scoreChanged = $(this.component.find(".scoreChanged"));
};

// inherit TetrisView
JQueryTetrisView.prototype = new TetrisView();

// correct the constructor pointer because it points to JQueryTetrisView
JQueryTetrisView.prototype.constructor = JQueryTetrisView;

// replace all methods of TetrisView
JQueryTetrisView.prototype.addPushStartHandler = function(pushStartHandler) {
	this.pushStartButton.click(pushStartHandler);
};

JQueryTetrisView.prototype.addMoveLeftHandler = function(moveLeftHandler) {
	jQuery(document).on("keydown", function(event) {
		if (event.which == 37) {
			moveLeftHandler();
			event.stopPropagation();
		}
	});
};

JQueryTetrisView.prototype.addMoveRightHandler = function(moveRightHandler) {
	jQuery(document).on("keydown", function(event) {
		if (event.which == 39) {
			moveRightHandler();
			event.stopPropagation();
		}
	});
};


JQueryTetrisView.prototype.addDropPieceHandler = function(dropPieceHandler) {
	jQuery(document).on("keydown", function(event) {
		if (event.which == 40) {
			dropPieceHandler();
			event.stopPropagation();
		}
	});
};

JQueryTetrisView.prototype.addRotatePieceHandler = function(rotatePieceHandler) {
	jQuery(document).on("keydown", function(event) {
		if (event.which == 38) {
			rotatePieceHandler();
			event.stopPropagation();
		}
	});
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
	
	var currentPiece = this.shapes.parent().find('#' + piece.tetromino + 'Shape');
	// descente classique

	var y = piece.y * 23 + 46;
	var x = piece.x * 23 + 46;
	//var rotation = piece.rotation * (90);
	currentPiece.css({
		left : x + 'px',
		top : y + 'px',
		//transform : 'rotate(' + rotation + 'deg)'
	});
	currentPiece.removeClass("rotate0 rotate1 rotate2 rotate3")
	currentPiece.addClass("rotate"+piece.rotation);
	currentPiece.show();
};

JQueryTetrisView.prototype.displayBlock = function(x, y, tetromino) {
	console.log("JQueryTetrisView.displayGrid");
	
	var block = $(this.grid.find('td')[y*10+x]);
	block.addClass(tetromino+'Shape');
};

JQueryTetrisView.prototype.displayScore = function(level, lines, points) {
	this.scoreChanged.html('+'+points);
	this.scoreChanged.show();
	this.scoreChanged.slideUp();
};