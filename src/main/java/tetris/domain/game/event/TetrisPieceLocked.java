package tetris.domain.game.event;

import tetris.domain.game.Shape;
import tetris.domain.game.TetrisId;

public class TetrisPieceLocked extends TetrisEvent {

    private final Shape piece;

    public TetrisPieceLocked(TetrisId tetrisId, Shape piece) {
        super(tetrisId);
        this.piece = piece;
    }

    public Shape getPiece() {
        return piece;
    }

    @Override
    public void notify(TetrisListener listener) {
        listener.pieceLocked(this);
    }

}
