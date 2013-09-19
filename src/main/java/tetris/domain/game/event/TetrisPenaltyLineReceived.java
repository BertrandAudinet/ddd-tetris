package tetris.domain.game.event;

import tetris.domain.game.TetrisId;

public class TetrisPenaltyLineReceived extends TetrisEvent {

    private final int lineCount;

    public TetrisPenaltyLineReceived(TetrisId tetrisId, int lineCount) {
        super(tetrisId);
        this.lineCount = lineCount;
    }

    public int getLineCount() {
        return lineCount;
    }
}
