package tetris.domain.game;

import junit.framework.Assert;

import org.junit.Test;

public class BoardTest {
    private int width = 10;

    private int height = 22;

    @Test
    public void testFillBlock_EmptyBoard_Filled() {
        final Board board = new Board(width, height);

        final Board actual = board.fillBlock(new Block(0, 0, Tetromino.T));

        Assert.assertEquals(new Block(0, 0, Tetromino.T), actual.getBlockAt(0, 0));
    }

    @Test
    public void testHasCollision_EmptyBoard_None() {
        final Board board = new Board(width, height);

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertFalse(hasCollision);
    }

    @Test
    public void testHasCollision_FullBoard_Detected() {
        final Board board = new Board(width, height).fill();

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onFirstBlock_Detected() {
        final Board board = new Board(width, height).fillBlock(new Block(5, 2, Tetromino.I));

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onSecondBlock_Detected() {
        final Board board = new Board(width, height).fillBlock(new Block(6, 2, Tetromino.I));

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

}
