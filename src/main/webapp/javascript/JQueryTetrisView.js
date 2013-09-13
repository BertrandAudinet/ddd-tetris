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

JQueryTetrisView.prototype.clearGrid = function() {
	this.grid.find("td").removeClass(
			'IShape OShape TShape JShape lShape SShape ZShape PShape');
};

JQueryTetrisView.prototype.hideShape = function() {
	this.shapes.hide();
};

JQueryTetrisView.prototype.displayStart = function() {
	alert("Tetris started");
};

JQueryTetrisView.prototype.displayPause = function() {
	alert("Tetris paused");
};