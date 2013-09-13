package tetris.domain.game.event;

import tetris.domain.game.Shape;
import tetris.domain.game.TetrisId;

public class TetrisPieceDropped extends TetrisEvent {

    private final Shape piece;

    public TetrisPieceDropped(TetrisId tetrisId, Shape piece) {
        super(tetrisId);
        this.piece = piece;
    }

    public Shape getPiece() {
        return piece;
    }

}
