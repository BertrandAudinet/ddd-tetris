package tetris.domain.game;

import org.junit.Assert;
import org.junit.Test;

public class ShapeTest {

    @Test
    public void testMoveDown_IShape_Moved() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.moveDown();

        Assert.assertEquals(2, actual.getY());
    }

    @Test
    public void testMoveLeft_IShape_Moved() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.moveLeft();

        Assert.assertEquals(0, actual.getX());
    }

    @Test
    public void testMoveRight_IShape_Moved() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.moveRight();

        Assert.assertEquals(2, actual.getX());
    }

    @Test
    public void testRotateLeft_IShape_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateLeft();

        Block[] expected =
                        new Block[]{new Block(1, 3, Tetromino.I), new Block(1, 2, Tetromino.I),
                                        new Block(1, 1, Tetromino.I), new Block(1, 0, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateLeft_IShape_2Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateLeft().rotateLeft();

        Block[] expected =
                        new Block[]{new Block(3, 2, Tetromino.I), new Block(2, 2, Tetromino.I),
                                        new Block(1, 2, Tetromino.I), new Block(0, 2, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateLeft_IShape_3Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateLeft().rotateLeft().rotateLeft();

        Block[] expected =
                        new Block[]{new Block(2, 0, Tetromino.I), new Block(2, 1, Tetromino.I),
                                        new Block(2, 2, Tetromino.I), new Block(2, 3, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateLeft_IShape_4Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateLeft().rotateLeft().rotateLeft().rotateLeft();

        Assert.assertArrayEquals(shape.getBlocks(), actual.getBlocks());
    }

    @Test
    public void testRotateRight_IShape_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight();

        Block[] expected =
                        new Block[]{new Block(2, 0, Tetromino.I), new Block(2, 1, Tetromino.I),
                                        new Block(2, 2, Tetromino.I), new Block(2, 3, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateRight_IShape_2Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight().rotateRight();

        Block[] expected =
                        new Block[]{new Block(3, 2, Tetromino.I), new Block(2, 2, Tetromino.I),
                                        new Block(1, 2, Tetromino.I), new Block(0, 2, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateRight_IShape_3Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight().rotateRight().rotateRight();

        Block[] expected =
                        new Block[]{new Block(1, 3, Tetromino.I), new Block(1, 2, Tetromino.I),
                                        new Block(1, 1, Tetromino.I), new Block(1, 0, Tetromino.I), };
        Assert.assertArrayEquals(expected, actual.getBlocks());
    }

    @Test
    public void testRotateRight_IShape_4Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight().rotateRight().rotateRight().rotateRight();

        Assert.assertArrayEquals(shape.getBlocks(), actual.getBlocks());
    }
}
