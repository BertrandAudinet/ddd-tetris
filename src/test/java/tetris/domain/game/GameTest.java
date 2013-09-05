package tetris.domain.game;

import junit.framework.Assert;

import org.junit.Test;

public class GameTest {

    @Test
    public void testMovePiece_EmptyBoard_MovedOnLeft() {
        final Game game = new Game(Board.defaultBoard(), new Shape(5, 0, Tetromino.I));

        game.movePiece(Direction.LEFT);

        Assert.assertEquals(4, game.getPiece().getX());
    }

    @Test
    public void testMovePiece_FullBoard_NotMoved() {
        final Game game = new Game(Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.I));

        game.movePiece(Direction.LEFT);

        Assert.assertEquals(5, game.getPiece().getX());
    }

    @Test
    public void testFallPiece_EmptyBoard_PieceFallen() {
        final Game game = new Game(Board.defaultBoard(), new Shape(5, 0, Tetromino.I));

        game.fallPiece();

        Assert.assertEquals(1, game.getPiece().getY());
    }

    @Test
    public void testFallPiece_FullBoard_PieceNotFallen() {
        final Game game = new Game(Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.I));

        game.fallPiece();

        Assert.assertNull(game.getPiece());
    }

    @Test
    public void testFallPiece_FullBoard_PieceFilled() {
        final Game game = new Game(Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.O));

        game.fallPiece();

        Assert.assertEquals(Tetromino.O, game.getBoard().getBlockAt(6, 1).getTetromino());
    }

    @Test
    public void testDropNewPiece_FullBoard_ReturnsIsLost() {
        final Game game = new Game(Board.defaultBoard().fill(), new Shape(5, 0, Tetromino.O));

        game.dropNewPiece(Tetromino.T);

        Assert.assertTrue(game.isLost());
    }
}
