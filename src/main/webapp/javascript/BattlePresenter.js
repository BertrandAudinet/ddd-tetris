/**
 * 
 * @param {BattleView}
 *            view
 * @param {BattleModel}
 *            model
 * @returns
 */
function BattlePresenter(view, model) {
	this.view = view;
	this.model = model;
};

BattlePresenter.prototype.init = function() {
	var presenter = this;
	
	var battle = this.model.getBattle();
	this.view.displayBattle(battle);
	
	this.model.addBattleEventHandler(function(event) {
		presenter.onBattleEvent(event);
	});
	
	var timer = $.timer(function() {
		presenter.model.getEvents();
		timer.once(500);
	}, 500, false);
	timer.once(500);
};

BattlePresenter.prototype.onBattleEvent = function(event) {
	var type = event.type;
	this.lastEventId = event.lastEventId;
	if (type == "BATTLE_TETRIS_JOINED") {
		this.onTetrisJoined(event);
	} else {
		console.log("Event [type=" + event.type + "]");
	}
};

BattlePresenter.prototype.onTetrisJoined = function(event) {
	if (event.tetrisId != this.model.getTetrisId()) {
		this.view.addTetris(event.tetrisId);
	}
};

function BattleView() {
};

BattleView.prototype.displayBattle = function(battle) {
};

BattleView.prototype.addTetris = function(tetrisId) {
};

/**
 * 
 * @param {String} battleId
 * @returns
 */
function BattleModel(battleId) {
	this.battleId = battleId;
};

BattleModel.prototype.getBattle = function() {
};

BattleModel.prototype.addBattleEventHandler = function(handler) {
};

BattleModel.prototype.getEvents = function() {
};

BattleModel.prototype.getTetrisId = function() {
};
