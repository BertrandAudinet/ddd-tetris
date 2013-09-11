package tetris.domain.battle;

import tetris.domain.game.TetrisId;

public class Opponent {
    private TetrisId tetrisId;

    private int penaltyLineCount;

    public Opponent(TetrisId tetrisId) {
        this(tetrisId, 0);
    }

    public Opponent(TetrisId tetrisId, int penaltyLineCount) {
        this.tetrisId = tetrisId;
        this.penaltyLineCount = penaltyLineCount;
    }

    public Opponent addPenaltyLine(int lineCount) {
        return new Opponent(tetrisId, penaltyLineCount + lineCount);
    }

    public TetrisId getTetrisId() {
        return tetrisId;
    }

    public boolean isOpponent(TetrisId opponent) {
        return !tetrisId.equals(opponent);
    }

    public int getPenaltyLineCount() {
        return penaltyLineCount;
    }
}
