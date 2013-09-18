function JQueryBattleView(component) {
	this.component = component;
	this.battleId = $(component).find("#battleId").first();
	this.opponents = $(component).find('.opponents').first();
};

// inherit BattleView
JQueryBattleView.prototype = new BattleView();

// correct the constructor pointer because it points to JQueryBattleView
JQueryBattleView.prototype.constructor = JQueryBattleView;

JQueryBattleView.prototype.displayBattle = function(battle) {
	this.battleId.html(battle.battleId);
};

JQueryBattleView.prototype.addTetris = function(tetrisId) {
	var tetrisView = $('<li class="tetrisView">'+tetrisId+'</li>');
	this.opponents.append(tetrisView);
	tetrisView.load("template/tetris-template.html .tetris", function() {
		var view = new JQueryTetrisView(this, false);
		var model = new RestTetrisModel(tetrisId);
		var presenter = new TetrisPresenter(view, model);
		presenter.init();
	});
};
