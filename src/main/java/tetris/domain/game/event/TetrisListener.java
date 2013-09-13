package tetris.domain.game.event;

import java.util.EventListener;

public interface TetrisListener extends EventListener {
    void lineCompleted(TetrisLineCompleted event);

    void gameStarted(TetrisGameStarted event);

    void pieceDropped(TetrisPieceDropped event);

    void pieceMoved(TetrisPieceMoved event);

    void pieceRotated(TetrisPieceRotated event);
}
