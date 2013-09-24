package tetris.domain.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (block == null) {
            throw new IllegalArgumentException("block can't be null");
        }
        return fillBlocks(block);
    }

    public Board fillShape(Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException("shape can't be null");
        }
        List<Block> blocks = new ArrayList<Block>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (shape.contains(x, y)) {
                    blocks.add(new Block(x, y, shape.getTetromino()));
                }
            }
        }
        return fillBlocks(blocks.toArray(new Block[blocks.size()]));
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

        final List<Block> blocks = shape.getBlocks();
        for (Block block : blocks) {
            if (contains(block.getX(), block.getY())) {
                final Block gridBlock = getBlockAt(block.getX(), block.getY());
                if (gridBlock != null) {
                    collision = true;
                }
            } else {
                collision = true;
            }
        }
        return collision;
    }

    private boolean contains(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
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

    public static Board emptyBoard(int width, int height) {
        return new Board(width, height);
    }

    public Board removeCompletedLines() {
        boolean[] completedLines = new boolean[height];

        // search completed lines
        for (int y = 0; y < height; y++) {
            completedLines[y] = true;
            for (int x = 0; x < width; x++) {
                final Block block = getBlockAt(x, y);
                if (block == null) {
                    completedLines[y] = false;
                }
            }
        }

        // remove completed lines
        final Block[] newGrid = Arrays.copyOf(grid, height * width);
        for (int y = 0; y < height; y++) {
            if (completedLines[y]) {
                for (int x = 0; x < width; x++) {
                    newGrid[y * width + x] = null;
                }
            }
        }

        // fall empty lines
        int offset = 0;
        for (int y = height - 1; y >= 0; y--) {
            while (completedLines[y] && y >= 0) {
                y--;
                offset++;
            }
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int newIndex = (y + offset) * width + x;
                if (newGrid[index] != null) {
                    newGrid[newIndex] = newGrid[index].translate(0, offset);
                    if (offset > 0) {
                        newGrid[index] = null;
                    }
                }
            }
        }
        return new Board(width, height, newGrid);
    }

    public Board fillLine(int line) {
        Block[] blocks = new Block[width];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(i, line, Tetromino.T);
        }
        return fillBlocks(blocks);
    }

    public Block[] getLineAt(int line) {
        Block[] lines = new Block[width];
        for (int x = 0; x < width; x++) {
            lines[x] = grid[line * width + x];
        }
        return lines;
    }

    public Block[] getGrid() {
        return grid;
    }

    public List<Integer> getCompletedLine() {
        boolean[] completedLines = new boolean[height];

        // search completed lines
        for (int y = 0; y < height; y++) {
            completedLines[y] = true;
            for (int x = 0; x < width; x++) {
                final Block block = getBlockAt(x, y);
                if (block == null) {
                    completedLines[y] = false;
                }
            }
        }

        List<Integer> linesClear = new ArrayList<Integer>();
        int count = 0;
        for (int i = 0; i < completedLines.length; i++) {
            if (completedLines[i]) {
                count++;
                linesClear.add(i);
            }
        }
        return linesClear;
    }

    public Board insertLine(int line) throws BlockOutOfBoundsException {
        final Block[] newGrid = new Block[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                Block block = grid[index];
                if (y == 0 && block != null) {
                    throw new BlockOutOfBoundsException(block);
                }
                if (y < line) {
                    int origin = (y + 1) * width + x;
                    block = grid[origin];
                    if (block != null) {
                        block = block.moveUp();
                    }
                } else if (y == line) {
                    block = new Block(x, y, Tetromino.T);
                }
                newGrid[index] = block;
            }
        }

        return new Board(width, height, newGrid);
    }

    public Board removeLine(int line) {
        final Block[] newGrid = new Block[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                Block block = grid[index];
                if (y == 0) {
                    block = null;
                } else if (y <= line) {
                    int origin = (y - 1) * width + x;
                    block = grid[origin];
                    if (block != null) {
                        block = block.moveDown();
                    }
                }
                newGrid[index] = block;
            }
        }
        return new Board(width, height, newGrid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(grid);
        result = prime * result + height;
        result = prime * result + width;
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
        Board other = (Board ) obj;
        if (!Arrays.equals(grid, other.grid))
            return false;
        if (height != other.height)
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Board [width=" + width + ", height=" + height + ", grid=" + Arrays.toString(grid) + "]";
    }
}
