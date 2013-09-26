package tetris.domain.game.event;

import java.util.EventListener;

public interface TetrisListener extends EventListener {
    void lineCleared(TetrisLineCleared event);

    void gameStarted(TetrisGameStarted event);

    void pieceDropped(TetrisPieceDropped event);

    void pieceMoved(TetrisPieceMoved event);

    void pieceRotated(TetrisPieceRotated event);

    void pieceLocked(TetrisPieceLocked event);

    void scoreChanged(TetrisScoreChanged event);

    void receiveLinePenalty(TetrisPenaltyLineReceived event);

    void levelUpPerformed(TetrisLevelUpPerformed event);
}
