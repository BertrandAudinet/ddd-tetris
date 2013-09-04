package tetris.domain.game;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    public static int NB_BLOCKS = 4;

    private int x;

    private int y;

    private Tetromino tetromino;

    private int rotation;

    private Tetrominoes tetrominoes;

    public Shape(int x, int y, Tetromino tetromino) {
        this(x, y, tetromino, 0);
    }

    public Shape(int x, int y, Tetromino tetromino, int rotation) {
        this.x = x;
        this.y = y;
        this.tetromino = tetromino;
        this.tetrominoes = Tetrominoes.getTetrominoes(tetromino);
        this.rotation = rotation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public int getRotation() {
        return rotation;
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<Block>();
        final int[][] picture = this.tetrominoes.getPicture(rotation);
        for (int y = 0; y < picture.length; y++) {
            for (int x = 0; x < picture.length; x++) {
                if (picture[y][x] == 1) {
                    blocks.add(new Block(x + this.x, y + this.y, tetromino));
                }
            }
        }
        return blocks;
    }

    public Shape moveDown() {
        return new Shape(x, y + 1, tetromino, rotation);
    }

    public Shape move(Direction direction) {
        Shape movedShape = this;
        switch (direction) {
            case LEFT:
                movedShape = moveLeft();
                break;
            case RIGHT:
                movedShape = moveRight();
                break;
        }
        return movedShape;
    }

    public Shape moveLeft() {
        return new Shape(x - 1, y, tetromino, rotation);
    }

    public Shape moveRight() {
        return new Shape(x + 1, y, tetromino, rotation);
    }

    public Shape rotate(Direction direction) {
        Shape rotatedShape = this;
        switch (direction) {
            case LEFT:
                rotatedShape = rotateLeft();
                break;

            case RIGHT:
                rotatedShape = rotateRight();
                break;
        }
        return rotatedShape;
    }

    public Shape rotateLeft() {
        return new Shape(x, y, tetromino, rotation - 1);
    }

    public Shape rotateRight() {
        return new Shape(x, y, tetromino, rotation + 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rotation;
        result = prime * result + ((tetromino == null) ? 0 : tetromino.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shape other = (Shape ) obj;
        if (rotation != other.rotation)
            return false;
        if (tetromino != other.tetromino)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Shape [x=" + x + ", y=" + y + ", tetromino=" + tetromino + ", rotation=" + rotation + "]";
    }

    public boolean contains(int x, int y) {
        boolean isOutside = false;
        // checks that x is outside shape
        if (x < this.x || this.x + NB_BLOCKS <= x) {
            isOutside = true;
        }
        // checks that y is inside shape
        if (y < this.y || this.y + NB_BLOCKS <= y) {
            isOutside = true;
        }

        if (isOutside) {
            return false;
        } else {
            final int[][] picture = tetrominoes.getPicture(rotation);
            final int xInsidePosition = x - this.x;
            final int yPosition = y - this.y;
            return picture[yPosition][xInsidePosition] == 1;
        }
    }

}
