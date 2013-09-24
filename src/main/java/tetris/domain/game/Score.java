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

    public Score(int level, int lines, int points) {
        this.level = level;
        this.lines = lines;
        this.points = points;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + level;
        result = prime * result + lines;
        result = prime * result + points;
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
        Score other = (Score ) obj;
        if (level != other.level)
            return false;
        if (lines != other.lines)
            return false;
        if (points != other.points)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Score [level=" + level + ", lines=" + lines + ", points=" + points + "]";
    }

}
