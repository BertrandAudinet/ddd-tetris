var refreshTime =  500;	

/**
 * @constructor
 */
function ApplicationView(component) {
	this.pushStartButton = component.find(".pushStart");
	this.joinBattleButton = component.find(".joinBattle");
	this.tetrisView = component.find(".tetrisView");
	this.battleView = component.find(".battleView");
}

ApplicationView.prototype.addPushStartHandler = function(handler) {
	this.pushStartButton.click(handler);
};

ApplicationView.prototype.addJoinBattleHandler = function(handler) {
	this.joinBattleButton.click(handler);
};

ApplicationView.prototype.displayTetris = function(tetrisId) {
	this.tetrisView.load("template/tetris-template.html .tetris", function() {
		var view = new JQueryTetrisView(this, true);
		var model = new RestTetrisModel(tetrisId);
		var presenter = new TetrisPresenter(view, model);
		presenter.init();
	});
};

ApplicationView.prototype.displayBattle = function(battleId, tetrisId) {
	this.battleView.load("template/battle-template.html .battle", function() {
		var view = new JQueryBattleView(this);
		var model = new RestBattleModel(battleId, tetrisId);
		var presenter = new BattlePresenter(view, model);
		presenter.init();
	});
};

/**
 * @constructor
 */
function ApplicationModel() {
};

/**
 * @return {String}
 */
ApplicationModel.prototype.playNewTetris = function() {
	var tetrisId = undefined;
	jQuery(function($) {
		$.ajax({
			type : "POST",
			async : false,
			url : './ws/playing',
			contentType : 'application/json',
			data : '',
			success : function(data, textStatus, jqXHR) {
				tetrisId = data;
			},
			dataType : 'text'
		});
	});
	return tetrisId;
};

/**
 * @param {String} tetrisId
 * @return {String}
 */
ApplicationModel.prototype.joinBattle = function(tetrisId) {
	var battleId = undefined;
	jQuery(function($) {
		$.ajax({
	        type: "POST",
	        async : false,
	        url: './ws/battles?tetrisId='+tetrisId,
	        contentType : 'application/json',
	        data: '',
	        success: function (data, textStatus, jqXHR) {
	        	battleId = data;
	        },
	        dataType : 'text'
		});
	});
	return battleId;
};

ApplicationModel.prototype.startTetris = function(tetrisId) {
	$.get('./ws/playing/'+tetrisId+'/start')
	.done(function(data, textStatus, jqXHR) {
		console.log("start game for tetris id="+tetrisId);
	})
	.fail(function(jqXHR, textStatus, errorThrown) { 
		alert('error'); 
	});
};

/**
 * @constructor
 * @param {ApplicationView}
 *            view
 * @param {ApplicationModel}
 *            model
 */
function ApplicationPresenter(view, model) {
	this.view = view;
	this.model = model;
};

ApplicationPresenter.prototype.init = function() {
	var presenter = this;

	this.view.addPushStartHandler(function() {
		presenter.onPushStart();
	});
	this.view.addJoinBattleHandler(function() {
		presenter.onJoinBattle();
	});
};

ApplicationPresenter.prototype.onPushStart = function() {
	var tetrisId = this.model.playNewTetris();
	this.view.displayTetris(tetrisId);
	this.model.startTetris(tetrisId);
};

ApplicationPresenter.prototype.onJoinBattle = function() {
	var tetrisId = this.model.playNewTetris();
	var battleId = this.model.joinBattle(tetrisId);
	this.view.displayTetris(tetrisId);
	this.view.displayBattle(battleId, tetrisId);
};


