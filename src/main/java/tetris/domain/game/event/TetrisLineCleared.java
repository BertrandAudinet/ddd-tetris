package tetris.domain.game.event;

import tetris.domain.game.TetrisId;

public class TetrisLineCleared extends TetrisEvent {

    private final int lineCount;

    private final int line;

    public TetrisLineCleared(TetrisId tetrisId, int line, int lineCount) {
        super(tetrisId);
        this.line = line;
        this.lineCount = lineCount;
    }

    public int getLine() {
        return line;
    }

    public int getLineCount() {
        return lineCount;
    }
}
