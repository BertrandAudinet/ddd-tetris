package tetris.domain.game;

public class Block {
    private int x;

    private int y;

    private Tetromino tetromino;

    public Block(int x, int y, Tetromino tetromino) {
        this.x = x;
        this.y = y;
        this.tetromino = tetromino;
    }

    public Block moveDown() {
        return new Block(x, y + 1, tetromino);
    }

    public Block moveUp() {
        return new Block(x, y - 1, tetromino);
    }

    public Block moveLeft() {
        return new Block(x - 1, y, tetromino);
    }

    public Block moveRight() {
        return new Block(x + 1, y, tetromino);
    }

    public Block translate(int dx, int dy) {
        return new Block(x + dx, y + dy, tetromino);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        Block other = (Block ) obj;
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
        return "Block [x=" + x + ", y=" + y + ", tetromino=" + tetromino + "]";
    }

}
