package tetris.interfaces.playing;

import tetris.domain.game.Block;

public class BlockDto {

    private int x;

    private int y;

    private String tetromino;

    public BlockDto() {
    }

    public BlockDto(Block block) {
        x = block.getX();
        y = block.getY();
        tetromino = block.getTetromino().name();
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

    public String getTetromino() {
        return tetromino;
    }

    public void setTetromino(String tetromino) {
        this.tetromino = tetromino;
    }

}
