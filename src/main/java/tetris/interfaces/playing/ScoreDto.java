package tetris.interfaces.playing;

import tetris.domain.game.Score;

public class ScoreDto {
    private int level;

    private int lines;

    private int points;

    public ScoreDto() {
    }

    public ScoreDto(Score score) {
        level = score.getLevel();
        lines = score.getLines();
        points = score.getPoints();
    }

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
