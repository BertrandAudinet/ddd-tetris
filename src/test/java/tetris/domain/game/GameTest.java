package tetris.domain.game;

import org.junit.Assert;
import org.junit.Test;

import tetris.domain.game.event.TetrisEventQueue;
import tetris.domain.game.event.TetrisGameStarted;
import tetris.domain.game.event.TetrisPenaltyLineReceived;
import tetris.domain.game.event.TetrisPieceDropped;
import tetris.domain.game.event.TetrisPieceLocked;
import tetris.domain.game.event.TetrisPieceMoved;

public class GameTest {

    @Test
    public void testMovePiece_EmptyBoard_MovedOnLeft() {
        final Game game = new Game(new TetrisId("T1"), Board.defaultBoard(), new Shape(5, 0, Tetromino.I));

        game.movePiece(Direction.LEFT);

        Assert.assertEquals(4, game.getPiece().getX());
    }

    @Test
    public void testMovePiece_FullBoard_NotMoved() {
        final Game game = new Game(new TetrisId("T1"), Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.I));

        game.movePiece(Direction.LEFT);

        Assert.assertEquals(5, game.getPiece().getX());
    }

    @Test
    public void testFallPiece_EmptyBoard_PieceFallen() {
        final Game game = new Game(new TetrisId("T1"), Board.defaultBoard(), new Shape(5, 0, Tetromino.I));

        game.fallPiece();

        Assert.assertEquals(1, game.getPiece().getY());
    }

    @Test
    public void testFallPiece_OnCollision_PieceFilled() {
        final int lastLine = Board.DEFAULT_HEIGHT - 1;
        final Game game =
                        new Game(new TetrisId("T1"), Board.defaultBoard().fillShape(
                                        new Shape(3, lastLine - 1, Tetromino.O)), new Shape(3, lastLine - 3,
                                        Tetromino.O));

        game.fallPiece();

        Assert.assertEquals(Tetromino.O, game.getBoard().getBlockAt(5, lastLine - 3).getTetromino());
    }

    @Test
    public void testDropNewPiece_FullBoard_ReturnsIsLost() {
        final Game game = new Game(new TetrisId("T1"), Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.O));

        game.dropNewPiece(Tetromino.T);

        Assert.assertTrue(game.isLost());
    }

    @Test
    public void testNewGame_DefaultState_IsNotStarted() {
        final Game game = new Game(new TetrisId("T1"));

        game.start();

        Assert.assertTrue(game.isStarted());
    }

    @Test
    public void testNewGame_StartIt_IsStarted() {
        final Game game = new Game(new TetrisId("T1"));

        game.start();

        Assert.assertTrue(game.isStarted());
    }

    @Test
    public void testNewGame_StopIt_IsNotStarted() {
        final Game game = new Game(new TetrisId("T1"));

        game.stop();

        Assert.assertFalse(game.isStarted());
    }

    @Test
    public void testAddTetrisListener_StartGame_EventIsTetrisGameStarted() {
        final Game game = new Game(new TetrisId("T1"));
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.start();
        final TetrisGameStarted actual = (TetrisGameStarted ) queue.getLastEvent();

        Assert.assertTrue(actual.isStarted());
    }

    @Test
    public void testAddTetrisListener_StopGame_EventIsTetrisGameStarted() {
        final Game game = new Game(new TetrisId("T1"));
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.stop();
        final TetrisGameStarted actual = (TetrisGameStarted ) queue.getLastEvent();

        Assert.assertFalse(actual.isStarted());

    }

    @Test
    public void testAddTetrisListener_DropNewPiece_EventIsTetrisPieceDropped() {
        final Game game = new Game(new TetrisId("T1"));
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.dropNewPiece(Tetromino.T);
        final TetrisPieceDropped actual = (TetrisPieceDropped ) queue.getLastEvent();

        Assert.assertTrue(actual.isNewPiece());
    }

    @Test
    public void testAddTetrisListener_MovePiece_EventIsTetrisPieceMoved() {
        final Game game = new Game(new TetrisId("T1"));
        game.dropNewPiece(Tetromino.T);
        game.start();
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.movePiece(Direction.LEFT);
        final TetrisPieceMoved actual = (TetrisPieceMoved ) queue.getLastEvent();

        Shape expected = new Shape(2, 0, Tetromino.T);
        Assert.assertEquals(expected, actual.getPiece());
    }

    @Test
    public void testAddTetrisListener_DropPiece_EventIsTetrisPieceDropped() {
        final Game game = new Game(new TetrisId("T1"));
        game.dropNewPiece(Tetromino.T);
        game.start();
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.dropPiece();
        final TetrisPieceDropped actual = (TetrisPieceDropped ) queue.getLastEvent();

        Shape expected = new Shape(3, 1, Tetromino.T);
        Assert.assertEquals(expected, actual.getPiece());
    };

    @Test
    public void testAddTetrisListener_fallPiece_EventIsTetrisPieceDropped() {
        final Game game = new Game(new TetrisId("T1"));
        game.dropNewPiece(Tetromino.T);
        game.start();
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.fallPiece();
        final TetrisPieceDropped actual = (TetrisPieceDropped ) queue.getLastEvent();

        Shape expected = new Shape(3, 1, Tetromino.T);
        Assert.assertEquals(expected, actual.getPiece());
    }

    @Test
    public void testAddTetrisListener_fallPiece_EventIsTetrisPieceLocked() {
        final Game game = new Game(new TetrisId("T1"), Board.defaultBoard().fill(), new Shape(3, 0, Tetromino.T));
        game.start();
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.fallPiece();
        final TetrisPieceLocked actual = (TetrisPieceLocked ) queue.getLastEvent();

        Shape expected = new Shape(3, 0, Tetromino.T);
        Assert.assertEquals(expected, actual.getPiece());
    }

    @Test
    public void testAddPenaltyLine_addPenaltyLine_EventIsTetrisPenaltyLineReceived() {
        final Game game = new Game(new TetrisId("T1"));
        TetrisEventQueue queue = new TetrisEventQueue();
        game.addTetrisListener(queue);

        game.addPenaltyLine(2);
        final TetrisPenaltyLineReceived actual = (TetrisPenaltyLineReceived ) queue.getLastEvent();

        Assert.assertEquals(2, actual.getLineCount());
    }

    @Test
    public void testAddPenaltyLine_EmptyBoard_BoardIsFilled() {
        final Game game = new Game(new TetrisId("T1"));

        game.addPenaltyLine(2);

        Board expected = Board.defaultBoard().fillLine(20).fillLine(21);
        Assert.assertEquals(expected, game.getBoard());
    }
}
