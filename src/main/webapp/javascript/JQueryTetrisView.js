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
	var cssPadding = this.grid.parent().css("padding-left");
	this.padding = 1 * cssPadding.substring(0, cssPadding.length-2);
	var cssHeight = this.grid.find("tr").css("height");
	this.blockSize = 1 * cssHeight.substring(0, cssHeight.length-2);
	$(".score").remove();
	this.divLevelUp = this.component.find('#levelUp');
	this.divLevelUp.hide();
};

// inherit TetrisView
//JQueryTetrisView.prototype = new TetrisView();

// correct the constructor pointer because it points to JQueryTetrisView
//JQueryTetrisView.prototype.constructor = JQueryTetrisView;

JQueryTetrisView.prototype.levelUp = function(level) {
	this.divLevelUp.fadeIn(200).fadeOut(300);
};

// replace all methods of TetrisView
JQueryTetrisView.prototype.addPushStartHandler = function(pushStartHandler) {
	this.pushStartButton.click(pushStartHandler);
};


JQueryTetrisView.prototype.addMoveLeftHandler = function(moveLeftHandler) {
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 37) {
				moveLeftHandler();
				event.preventDefault();
			}
		});
	}
};
 
JQueryTetrisView.prototype.addMoveRightHandler = function(moveRightHandler) {
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 39) {
				moveRightHandler();
				event.preventDefault();
			}
		});
	}
};

JQueryTetrisView.prototype.addDropPieceHandler = function(dropPieceHandler) {
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 40) {
				dropPieceHandler();
				event.preventDefault();
			}
		});
	}
};

JQueryTetrisView.prototype.addRotatePieceHandler = function(rotatePieceHandler) {
	if (this.isActive) {
		jQuery(document).keydown(function(event) {
			if (event.which == 38) {
				rotatePieceHandler();
				event.preventDefault();
			}
		});
	}
};

JQueryTetrisView.prototype.clearGrid = function() {
	this.grid.find("td").removeClass(
			'IShape OShape TShape JShape LShape SShape ZShape PShape');
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

	var y = piece.y * this.blockSize + this.padding;
	var x = piece.x * this.blockSize + this.padding;
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

JQueryTetrisView.prototype.scoreUp = function(level, lines, points) {
	$(".score").remove();
	this.score.append('<span class="score">+' + points + '</span>');
	$(".score").animate({ top: "-200px", opacity: 0 }, 1500);
};

JQueryTetrisView.prototype.displayScore = function(level, lines, points) {
	$("#score_level").html(level);
	$("#score_lines").html(lines);
	$("#score_points").html(points);
};

JQueryTetrisView.prototype.clearLine = function(line) {
	var block = $(this.grid.find('tr')[line]);
	//block.fadeOut(100).fadeIn(10);
};


