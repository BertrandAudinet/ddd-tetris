/**
 * @constructor
 * @param {String} battleId
 */
function RestBattleModel(battleId, tetrisId) {
	this.battleId = battleId;
	this.tetrisId = tetrisId;
	this.lastEventId = 0;
	this.handlers = new Array();
	
	var model = this;
	this.timer = $.timer(function() {
		model.getEvents();
	}, refreshTime, false);
};

// inherit BattleModel
RestBattleModel.prototype = new BattleModel();

// correct the constructor pointer because it points to RestBattleModel
RestBattleModel.prototype.constructor = RestBattleModel;

RestBattleModel.prototype.getBattle = function() {
	var model = this;
	var battle = undefined;
	jQuery(function($) {
		$.ajax({
			type : "GET",
			async : false,
			url : './ws/battles/' + model.battleId,
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				battle = data.battle;
			}			
		});
	});
	return battle;
};

RestBattleModel.prototype.addBattleEventHandler = function(handler) {
	this.handlers.push(handler);
	this.timer.once(refreshTime);

};

RestBattleModel.prototype.getEvents = function() {
	var model = this;
	jQuery(function($) {
		$.get('./ws/battles/' + model.battleId + "/events?lastEventId="+model.lastEventId).done(
				function(data, textStatus, jqXHR) {
					var events = data.battleEvent;
					//console.log("getEvents done : count=");
					for ( var i = 0; i < events.length; i++) {
						var event = events[i];
						for ( var j = 0; j < model.handlers.length; j++) {
							var handler = model.handlers[j];
							handler(event);
						}
						model.lastEventId = event.lastEventId;
					}
					model.timer.once(refreshTime);
					
				}).fail(function(jqXHR, textStatus, errorThrown) {
					model.timer.stop();
					alert('Error = '+errorThrown);
					
		});
	});
};

RestBattleModel.prototype.getTetrisId = function() {
	return this.tetrisId;
}