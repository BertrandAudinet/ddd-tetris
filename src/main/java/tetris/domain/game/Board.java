package tetris.domain.game;

import java.util.Arrays;

public class Board {
    public static int DEFAULT_WIDTH = 10;

    public static int DEFAULT_HEIGHT = 22;

    private int width;

    private int height;

    private Block[] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Block[height * width];
    }

    public Board(int width, int height, Block[] grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    public Board fill() {
        final Board filledBoard = new Board(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Block block = new Block(x, y, Tetromino.T);
                int index = block.getY() * width + block.getX();
                filledBoard.grid[index] = block;
            }
        }
        return filledBoard;
    }

    public Board fillBlock(Block block) {
        return fillBlocks(block);
    }

    public Board fillShape(Shape shape) {
        final int nbBlock = shape.getBlocks().length;
        Block[] translatedBlocks = new Block[nbBlock];
        for (int i = 0; i < nbBlock; i++) {
            translatedBlocks[i] = shape.getBlocks()[i].translate(shape.getX(), shape.getY());
        }
        return fillBlocks(translatedBlocks);
    }

    public Board fillBlocks(Block... blocks) {
        final Block[] newGrid = Arrays.copyOf(grid, height * width);
        for (Block block : blocks) {
            int index = block.getY() * width + block.getX();
            newGrid[index] = block;
        }
        return new Board(width, height, newGrid);
    }

    public boolean hasCollision(Shape shape) {
        boolean collision = false;
        for (Block sBlock : shape.getBlocks()) {
            int xGrid = shape.getX() + sBlock.getX();
            int yGrid = shape.getY() + sBlock.getY();
            final Block block = getBlockAt(xGrid, yGrid);
            if (block != null) {
                collision = true;
            }
        }
        return collision;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Block getBlockAt(int x, int y) {
        int index = y * width + x;
        return grid[index];
    }

    public static Board defaultBoard() {
        return new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
