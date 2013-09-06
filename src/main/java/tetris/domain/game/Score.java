package tetris.domain.game;

import java.io.Serializable;

public class Score implements Serializable {
    private int level;

    private int lines;

    private int points;

    Score() {

    }

    public Score(int level) {
        this.level = level;
        this.lines = 0;
        this.points = 0;
    }

    public Score addLines(int number) {
        final Score score = new Score(this.level);
        score.lines = this.lines + number;
        score.points = this.points + (this.level + 1) * linePoints(number);
        return score;
    }

    private int linePoints(int number) {
        int points = 0;
        switch (number) {
            case 1:
                points = 100;
                break;

            case 2:
                points = 300;
                break;

            case 3:
                points = 500;
                break;

            case 4:
                points = 800;
                break;

        }
        return points;
    }

    public int getLevel() {
        return level;
    }

    public int getLines() {
        return lines;
    }

    public int getPoints() {
        return points;
    }
}
