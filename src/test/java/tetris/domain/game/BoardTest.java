package tetris.domain.game;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {
    private int width = 10;

    private int height = 22;

    @Test
    public void testFillBlock_EmptyBoard_Filled() {
        final Board board = Board.emptyBoard(width, height);

        final Board actual = board.fillBlock(new Block(0, 0, Tetromino.T));

        Assert.assertEquals(new Block(0, 0, Tetromino.T), actual.getBlockAt(0, 0));
    }

    @Test
    public void testFillShape_EmptyBoard_Filled() {
        final Board board = Board.emptyBoard(width, height);

        final Board actual = board.fillShape(new Shape(5, 0, Tetromino.O));

        Assert.assertEquals(new Block(6, 1, Tetromino.O), actual.getBlockAt(6, 1));
    }

    @Test
    public void testHasCollision_EmptyBoard_None() {
        final Board board = Board.emptyBoard(width, height);

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertFalse(hasCollision);
    }

    @Test
    public void testHasCollision_FullBoard_Detected() {
        final Board board = Board.emptyBoard(width, height).fill();

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onFirstBlock_Detected() {
        final Board board = Board.emptyBoard(width, height).fillBlock(new Block(5, 2, Tetromino.I));

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onSecondBlock_Detected() {
        final Board board = Board.emptyBoard(width, height).fillBlock(new Block(6, 2, Tetromino.I));

        final boolean hasCollision = board.hasCollision(new Shape(5, 1, Tetromino.I));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onBottom_Detected() {
        final Board board = Board.emptyBoard(width, height);

        final boolean hasCollision = board.hasCollision(new Shape(5, 21, Tetromino.T));

        Assert.assertTrue(hasCollision);
    }

    @Test
    public void testHasCollision_onEndLine_Detected() {
        final Board board = Board.emptyBoard(width, height);

        final boolean hasCollision = board.hasCollision(new Shape(3, 20, Tetromino.I));

        Assert.assertFalse(hasCollision);
    }

    @Test
    public void testFillLine_EmptyBoard_ReturnNewBoardWithCompletedLine() {
        final Board board = Board.emptyBoard(width, height);

        final int lastLine = height - 1;
        final Board actual = board.fillLine(lastLine);

        Block[] expected = new Block[width];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = new Block(i, lastLine, Tetromino.T);
        }

        Assert.assertArrayEquals(expected, actual.getLineAt(lastLine));
    }

    @Test
    public void testRemoveCompletedLines_LastLineCompleted_ReturnsEmptyBoard() {
        final int lastLine = height - 1;
        final Board board = Board.emptyBoard(width, height).fillLine(lastLine);

        Board actual = board.removeCompletedLines();

        Board expected = Board.emptyBoard(width, height);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveCompletedLines_2LastLineCompleted_ReturnsEmptyBoard() {
        final int lastLine = height - 1;
        final Board board =
                        Board.emptyBoard(width, height).fillLine(lastLine).fillLine(lastLine - 1)
                                        .fillBlock(new Block(3, lastLine - 2, Tetromino.I));

        Board actual = board.removeCompletedLines();

        Board expected = Board.emptyBoard(width, height).fillBlock(new Block(3, lastLine, Tetromino.I));;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveCompletedLines_LastLineCompleted_ReturnsFallenBoard() {
        final int lastLine = height - 1;
        final Board board = Board.emptyBoard(width, height).fillLine(lastLine).fillBlock(new Block(3, 0, Tetromino.T));

        Board actual = board.removeCompletedLines();

        Board expected = Board.emptyBoard(width, height).fillBlock(new Block(3, 1, Tetromino.T));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveCompletedLines_LastLineUncompleted_ReturnsSameBoard() {
        final int lastLine = height - 1;
        final Board board = Board.emptyBoard(width, height).fillBlock(new Block(0, lastLine, Tetromino.T));

        Board actual = board.removeCompletedLines();

        Board expected = board;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertLine_EmptyBoard_Inserted() {
        final Board board = Board.emptyBoard(4, 5);

        final Board actual = board.insertLine(0);

        Block[] grid = new Block[20];
        grid[0] = new Block(0, 0, Tetromino.T);
        grid[1] = new Block(1, 0, Tetromino.T);
        grid[2] = new Block(2, 0, Tetromino.T);
        grid[3] = new Block(3, 0, Tetromino.T);
        Board expected = new Board(4, 5, grid);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertLine_BelowBlock_BlockIsLifted() {
        final Board board = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));

        final Board actual = board.insertLine(4);

        Board expected = Board.emptyBoard(4, 5).fillBlock(new Block(0, 3, Tetromino.T)).fillLine(4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertLine_OverBlock_BlockIsUnchanged() {
        final Board board = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));

        final Board actual = board.insertLine(3);

        Board expected = Board.emptyBoard(4, 5).fillLine(3).fillBlock(new Block(0, 4, Tetromino.T));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = BlockOutOfBoundsException.class)
    public void testInsertLine_FullBoard_RaiseException() {
        final Board board = Board.emptyBoard(4, 5).fill();

        try {
            board.insertLine(3);
        } catch (BlockOutOfBoundsException ex) {
            Assert.assertEquals("Block out of range : Block [x=0, y=0, tetromino=T]", ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void testRemoveLine_EmptyBoard_BoardIsUnchanged() {
        final Board board = Board.emptyBoard(4, 5);

        final Board actual = board.removeLine(0);

        Board expected = Board.emptyBoard(4, 5);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveLine_OnBlock_BlockIsCleared() {
        final Board board = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));

        final Board actual = board.removeLine(4);

        Board expected = Board.emptyBoard(4, 5);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertLine_BelowBlock_BlockIsLowered() {
        final Board board = Board.emptyBoard(4, 5).fillBlock(new Block(0, 3, Tetromino.T));

        final Board actual = board.removeLine(4);

        Board expected = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveLine_OverBlock_BlockIsUnchanged() {
        final Board board = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));

        final Board actual = board.removeLine(3);

        Board expected = Board.emptyBoard(4, 5).fillBlock(new Block(0, 4, Tetromino.T));
        Assert.assertEquals(expected, actual);
    }
}
