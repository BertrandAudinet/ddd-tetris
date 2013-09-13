package tetris.interfaces.playing;

import tetris.domain.game.Block;
import tetris.domain.game.Shape;

public class PieceDto {
    private String tetromino;

    private int x;

    private int y;

    private int rotation;

    private String[][] grid;

    public PieceDto() {
    }

    public PieceDto(Shape piece) {
        this.setRotation(piece.getRotation());
        this.setTetromino(piece.getTetromino().name());
        this.setX(piece.getX());
        this.setY(piece.getY());

        String[][] pieceGrid = new String[Shape.NB_BLOCKS][Shape.NB_BLOCKS];
        for (Block block : piece.getBlocks()) {
            pieceGrid[block.getY() - piece.getY()][block.getX() - piece.getX()] = block.getTetromino().name();
        }
        this.setGrid(pieceGrid);
    }

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
