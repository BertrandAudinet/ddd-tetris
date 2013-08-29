package tetris.domain.game;

public class Shape {
    public static int NB_BLOCKS = 4;

    private int x;

    private int y;

    private Tetromino tetromino;

    private Block[] blocks;

    public Shape(int x, int y, Tetromino tetromino) {
        this.x = x;
        this.y = y;
        this.tetromino = tetromino;
        this.blocks = calculateBlocks(tetromino);
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

    public Block[] getBlocks() {
        return blocks;
    }

    public Shape moveDown() {
        return new Shape(x, y + 1, tetromino);
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
        return new Shape(x - 1, y, tetromino);
    }

    public Shape moveRight() {
        return new Shape(x + 1, y, tetromino);
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
        final Shape rotatedShape = new Shape(x, y, tetromino);
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            rotatedShape.blocks[i] = new Block(block.getY(), NB_BLOCKS - 1 - block.getX(), block.getTetromino());
        }
        return rotatedShape;
    }

    public Shape rotateRight() {
        final Shape rotatedShape = new Shape(x, y, tetromino);
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            rotatedShape.blocks[i] = new Block(NB_BLOCKS - 1 - block.getY(), block.getX(), block.getTetromino());
        }
        return rotatedShape;
    }

    private static Block[] calculateBlocks(Tetromino tetromino) {
        Block[] blocks = new Block[4];
        switch (tetromino) {
            case I:
                blocks[0] = new Block(0, 1, tetromino);
                blocks[1] = new Block(1, 1, tetromino);
                blocks[2] = new Block(2, 1, tetromino);
                blocks[3] = new Block(3, 1, tetromino);
                break;
            case J:
                blocks[0] = new Block(0, 1, tetromino);
                blocks[1] = new Block(1, 1, tetromino);
                blocks[2] = new Block(2, 1, tetromino);
                blocks[3] = new Block(0, 2, tetromino);
                break;
            case L:
                blocks[0] = new Block(1, 1, tetromino);
                blocks[1] = new Block(2, 1, tetromino);
                blocks[2] = new Block(3, 1, tetromino);
                blocks[3] = new Block(3, 2, tetromino);
                break;
            case O:
                blocks[0] = new Block(1, 1, tetromino);
                blocks[1] = new Block(2, 1, tetromino);
                blocks[2] = new Block(2, 2, tetromino);
                blocks[3] = new Block(1, 2, tetromino);
                break;
            case S:
                blocks[0] = new Block(1, 2, tetromino);
                blocks[1] = new Block(2, 2, tetromino);
                blocks[2] = new Block(2, 1, tetromino);
                blocks[3] = new Block(3, 1, tetromino);
                break;
            case Z:
                blocks[0] = new Block(0, 1, tetromino);
                blocks[1] = new Block(1, 1, tetromino);
                blocks[2] = new Block(1, 2, tetromino);
                blocks[3] = new Block(2, 2, tetromino);
                break;
            case T:
                blocks[0] = new Block(0, 1, tetromino);
                blocks[1] = new Block(1, 1, tetromino);
                blocks[2] = new Block(2, 1, tetromino);
                blocks[3] = new Block(1, 2, tetromino);
                break;
            default:
                break;
        }
        return blocks;
    }

}
