package tetris.domain.game.event;

import tetris.domain.game.Score;
import tetris.domain.game.TetrisId;

public class TetrisScoreChanged extends TetrisEvent {

    private final Score score;

    public TetrisScoreChanged(TetrisId tetrisId, Score score) {
        super(tetrisId);
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}
