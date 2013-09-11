package tetris.domain.battle.event;

import tetris.domain.battle.BattleId;
import tetris.domain.game.TetrisId;

public class BattlePenaltyLineAdded extends BattleEvent {

    private final TetrisId tetrisId;

    private final int lineCount;

    public BattlePenaltyLineAdded(BattleId battleId, TetrisId tetrisId, int lineCount) {
        super(battleId);
        this.tetrisId = tetrisId;
        this.lineCount = lineCount;
    }

    public TetrisId getTetrisId() {
        return tetrisId;
    }

    public int getLineCount() {
        return lineCount;
    }

}
