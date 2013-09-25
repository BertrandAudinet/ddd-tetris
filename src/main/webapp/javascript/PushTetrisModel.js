function TetrisCommand(tetrisId, action, data) {
	this.tetrisId = tetrisId;
	this.action = action;
	this.data = data;
};

TetrisCommand.prototype.send = function() {
	var command = this;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			url : './ws/playing/' + command.tetrisId + '/' + command.action,
			contentType : 'application/json',
			data : command.data,
			success : function() {
				console.log(command.action +" piece to " + command.data + " for tetris id="
						+ command.tetrisId);
			}
		});
	});
};

/**
 * @constructor
 * @param [String}
 *            tetrisId
 */
function PushTetrisModel(tetrisId) {
	// Call the parent constructor
	TetrisModel.call(this);
	this.tetrisId = tetrisId;
	this.lastEventId = 0;
	this.handlers = new Array();
	this.commands = new Array();
	var model = this;
	this.timer = $.timer(function() {
		while (model.commands.length > 0) {
			var command = model.commands.shift();
			if (command != null) {
				command.send();
			}
		}
		model.getEvent();
		model.timer.once(refreshTime);
	}, refreshTime, false);
};

PushTetrisModel.prototype.playNewTetris = function() {
	var model = this;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			async : false,
			url : './ws/playing',
			contentType : 'application/json',
			data : '',
			success : function(data, textStatus, jqXHR) {
				model.tetrisId = data;
				model.getEvent();
			},
			dataType : 'text'
		});
	});
	return this.tetrisId;
};

PushTetrisModel.prototype.start = function() {
	var model = this;
	$.get('./ws/playing/' + model.tetrisId + '/start').done(
			function(data, textStatus, jqXHR) {
				console.log("start game for tetris id=" + model.tetrisId);
			}).fail(function(jqXHR, textStatus, errorThrown) {
		alert('error');
	});
};

PushTetrisModel.prototype.getEvent = function() {
	var model = this;
	jQuery(function($) {
		$.get(
				'./ws/playing/' + model.tetrisId + "/events?lastEventId="
						+ model.lastEventId).done(
				function(data, textStatus, jqXHR) {
					var events = data.tetrisEvent;
					// console.log("getEvents done : count=");
					for ( var i = 0; i < events.length; i++) {
						var event = events[i];
						for ( var j = 0; j < model.handlers.length; j++) {
							var handler = model.handlers[j];
							handler(event);
						}
						model.lastEventId = event.lastEventId;
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					model.timer.stop();
					alert('Error = ' + errorThrown);
		});
	});
};

PushTetrisModel.prototype.addTetrisEventHandler = function(handler) {
	this.handlers.push(handler);
	this.timer.once(refreshTime);
};

PushTetrisModel.prototype.rotatePiece = function(direction) {
	if (this.commands.length < 5) {
		var command = new TetrisCommand(this.tetrisId, 'rotate', '{ "rotate" : { "direction": ' + direction + ' }}');
		this.commands.push(command);
	}
};

PushTetrisModel.prototype.movePiece = function(direction) {
	if (this.commands.length < 5) {
		var command = new TetrisCommand(this.tetrisId, 'move', '{ "move" : { "direction": ' + direction + '}}');
		this.commands.push(command);
	}
};

PushTetrisModel.prototype.dropPiece = function() {
	if (this.commands.length < 5) {
		var command = new TetrisCommand(this.tetrisId, 'drop', null);
		this.commands.push(command);
	}
};

PushTetrisModel.prototype.getBoard = function() {
	var model = this;
	var board = null;
	jQuery(function($) {
		$.ajax({
			type : "GET",
			async : false,
			url : './ws/playing/' + model.tetrisId + "/board",
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				board = data.Result;
			}
		});
	});
	return board;
};