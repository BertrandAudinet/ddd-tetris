package tetris.domain.game.event;

import java.util.EventObject;

import tetris.domain.game.TetrisId;

public abstract class TetrisEvent extends EventObject {

    private final long timestamp;

    public TetrisEvent(TetrisId tetrisId) {
        super(tetrisId);
        this.timestamp = System.currentTimeMillis();
    }

    public TetrisId getTetrisId() {
        return (TetrisId ) getSource();
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public abstract void notify(TetrisListener listener);
}
