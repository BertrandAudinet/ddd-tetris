package tetris.domain.game.event;

import tetris.domain.game.TetrisId;

public class TetrisGameStarted extends TetrisEvent {

    private boolean started;

    public TetrisGameStarted(TetrisId tetrisId, boolean started) {
        super(tetrisId);
        this.started = started;
    }

    public boolean isStarted() {
        return started;
    }

}
