package tetris.domain.game.event;

import tetris.domain.game.TetrisId;

public class TetrisLineCompleted extends TetrisEvent {

    private final int lineCount;

    public TetrisLineCompleted(TetrisId tetrisId, int lineCount) {
        super(tetrisId);
        this.lineCount = lineCount;
    }

    public int getLineCount() {
        return lineCount;
    }
}
