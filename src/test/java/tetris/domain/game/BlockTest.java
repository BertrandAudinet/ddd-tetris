package tetris.domain.game;

import junit.framework.Assert;

import org.junit.Test;

public class BlockTest {

    @Test
    public void testMoveDown_Block_ReturnsMovedBlock() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveDown();

        final Block expected = new Block(1, 2, Tetromino.I);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMoveLeft_Block_ReturnsMovedBlock() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveLeft();

        final Block expected = new Block(0, 1, Tetromino.I);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMoveRight_Block_ReturnsMovedBlock() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveRight();

        final Block expected = new Block(2, 1, Tetromino.I);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTranslate_Block_ReturnsTranslatedBlock() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.translate(10, 5);

        final Block expected = new Block(11, 6, Tetromino.I);
        Assert.assertEquals(expected, actual);
    }

}
