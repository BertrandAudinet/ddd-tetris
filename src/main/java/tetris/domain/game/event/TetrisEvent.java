package tetris.domain.game.event;

import java.util.EventObject;

import tetris.domain.game.TetrisId;

public class TetrisEvent extends EventObject {

    public TetrisEvent(TetrisId tetrisId) {
        super(tetrisId);
    }

    public TetrisId getTetrisId() {
        return (TetrisId ) getSource();
    }

}
