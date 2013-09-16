package tetris.domain.game.event;

import tetris.domain.game.Shape;
import tetris.domain.game.TetrisId;

public class TetrisPieceDropped extends TetrisEvent {

    private final Shape piece;

    private final boolean newPiece;

    public TetrisPieceDropped(TetrisId tetrisId, Shape piece, boolean newPiece) {
        super(tetrisId);
        this.piece = piece;
        this.newPiece = newPiece;
    }

    public Shape getPiece() {
        return piece;
    }

    public boolean isNewPiece() {
        return newPiece;
    }
}
