module('JQueryTetrisViewTest', {
	setup : function() {
	}
});

test('Construteur', function() {
	var view = new JQueryTetrisView(jQuery(".tetris").first());

	notEqual(view.pushStartButton, null);
	// strictEqual(convertEuro(1, this.currency), 1.3, 'succeed !');

});

test(
		'Given adding an push start handler when the push start button is clicked then the handler is called',
		function() {
			var view = new JQueryTetrisView(jQuery(".tetris").first());

			var isClicked = false;
			var pushStartHandler = function() {
				isClicked = true;
			};
			view.addPushStartHandler(pushStartHandler);

			view.pushStartButton.click();

			equal(isClicked, true);

		});

test('Given a filled tetris when is cleared then grid is empty', function() {
	var view = new JQueryTetrisView(jQuery(".tetris").first());

	view.clearGrid();

	equal($("#qunit-fixture td").hasClass("IShape"), false);
});
