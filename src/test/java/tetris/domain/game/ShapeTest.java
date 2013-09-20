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
    public void testRotateLeft_JShape_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateLeft();

        Assert.assertEquals(1, actual.getRotation());
    }

    @Test
    public void testRotateRight_JShape_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight();

        Assert.assertEquals(1, actual.getRotation());
    }

    @Test
    public void testRotateRight_IShape_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight();

        Assert.assertEquals(1, actual.getRotation());
    }

    @Test
    public void testRotateRight_JShape2Times_Rotated() {
        final Shape shape = new Shape(1, 1, Tetromino.I);

        final Shape actual = shape.rotateRight().rotateRight();

        Assert.assertEquals(0, actual.getRotation());
    }

    @Test
    public void testContains_JShape_Contained() {
        final Shape shape = new Shape(0, 0, Tetromino.J);

        final boolean actual = shape.contains(0, 0);

        Assert.assertEquals(true, actual);
    }

    @Test
    public void testContains_JShape_NotContained() {
        final Shape shape = new Shape(0, 0, Tetromino.J);

        final boolean actual = shape.contains(1, 0);

        Assert.assertEquals(false, actual);
    }

    @Test
    public void testContains_TranslatedJShape_Contained() {
        final Shape shape = new Shape(5, 5, Tetromino.J);

        final boolean actual = shape.contains(5, 5);

        Assert.assertEquals(true, actual);
    }

    @Test
    public void testContains_OutsidePosition_NotContained() {
        final Shape shape = new Shape(5, 5, Tetromino.J);

        final boolean actual = shape.contains(15, 15);

        Assert.assertEquals(false, actual);
    }
}
