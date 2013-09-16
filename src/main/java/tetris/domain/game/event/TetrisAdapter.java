package tetris.domain.game.event;

public class TetrisAdapter implements TetrisListener {

    @Override
    public void lineCleared(TetrisLineCleared event) {
    }

    @Override
    public void gameStarted(TetrisGameStarted event) {
    }

    @Override
    public void pieceDropped(TetrisPieceDropped event) {
    }

    @Override
    public void pieceMoved(TetrisPieceMoved event) {
    }

    @Override
    public void pieceRotated(TetrisPieceRotated event) {
    }

    @Override
    public void pieceLocked(TetrisPieceLocked event) {
    }

    @Override
    public void scoreChanged(TetrisScoreChanged event) {
    }

}
