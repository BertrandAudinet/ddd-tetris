package tetris.domain.game;

import junit.framework.Assert;

import org.junit.Test;

public class BlockTest {

    @Test
    public void testMoveDown() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveDown();

        Assert.assertEquals(2, actual.getY());
    }

    @Test
    public void testMoveLeft() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveLeft();

        Assert.assertEquals(0, actual.getX());
    }

    @Test
    public void testMoveRight() {
        final Block block = new Block(1, 1, Tetromino.I);

        final Block actual = block.moveRight();

        Assert.assertEquals(2, actual.getX());
    }

}
