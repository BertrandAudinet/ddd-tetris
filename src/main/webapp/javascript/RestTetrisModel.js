function RestTetrisModel() {
	// Call the parent constructor
	TetrisModel.call(this);
	this.tetrisId = null;
	this.handlers = new Array();
};

RestTetrisModel.prototype.playNewTetris = function() {
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
				model.getEvent(model.tetrisId, 0);
			},
			dataType : 'text'
		});
	});
	return this.tetrisId;
};

RestTetrisModel.prototype.start = function() {
	var model = this;
	$.get('./ws/playing/'+model.tetrisId+'/start')
	.done(function(data, textStatus, jqXHR) {
		console.log("start game for tetris id="+model.tetrisId);
	})
	.fail(function(jqXHR, textStatus, errorThrown) { 
		alert('error'); 
	});
};

RestTetrisModel.prototype.getEvent = function(tetrisId, lastEventId) {
	var model = this;
	jQuery(function($) {
		$.get('./ws/playing/' + tetrisId + "/events?lastEventId="+lastEventId).done(
				function(data, textStatus, jqXHR) {
					var events = data.tetrisEvent;
					//console.log("getEvents done : count=");
					for ( var i = 0; i < events.length; i++) {
						var event = events[i];
						for ( var j = 0; j < model.handlers.length; j++) {
							var handler = model.handlers[j];
							handler(event);
						}
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
			alert('error');
		});
	});
};

RestTetrisModel.prototype.addTetrisEventHandler = function(handler) {
	this.handlers.push(handler);
};

RestTetrisModel.prototype.rotatePiece = function(direction) {
	var model = this;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			url : './ws/playing/' + model.tetrisId + '/rotate',
			contentType : 'application/json',
			data : '{ "rotate" : { "direction": ' + direction + ' }}',
			success : function() {
				console.log("rotate piece to "+direction+" for tetris id="+model.tetrisId);
			}
		});
	});
	return this;
};

RestTetrisModel.prototype.movePiece = function(direction) {
	var model = this;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			url : './ws/playing/' + model.tetrisId + '/move',
			contentType : 'application/json',
			data : '{ "move" : { "direction": ' + direction + '}}',
			success : function() {
				console.log("move piece to "+direction+" for tetris id="+model.tetrisId);
			}
		});
	});
	return this;
};

RestTetrisModel.prototype.dropPiece = function() {
	var model = this;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			url : './ws/playing/' + model.tetrisId + '/drop',
			contentType : 'application/json',
			success : function() {
				console.log("drop piece for tetris id="+model.tetrisId);
			}
		});
	});
	return this;
};

RestTetrisModel.prototype.getBoard = function(tetrisId, lastEventId) {
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