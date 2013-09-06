package tetris.interfaces.playing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
public class BoardDto {
    private String[][] grid;

    private int level;

    private int lines;

    private int score;

    private PieceDto piece;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public PieceDto getPiece() {
        return piece;
    }

    public void setPiece(PieceDto piece) {
        this.piece = piece;
    }
}
