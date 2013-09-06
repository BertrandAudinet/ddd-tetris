package tetris.interfaces.playing;

public class PieceDto {
    private String tetromino;

    private int x;

    private int y;

    private int rotation;

    private String[][] grid;

    public String getTetromino() {
        return tetromino;
    }

    public void setTetromino(String tetromino) {
        this.tetromino = tetromino;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }
}
