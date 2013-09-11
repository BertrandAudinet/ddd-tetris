package tetris.domain.battle.event;

import tetris.domain.battle.BattleId;
import tetris.domain.game.TetrisId;

public class BattleTetrisJoined extends BattleEvent {

    private final TetrisId tetrisId;

    public BattleTetrisJoined(BattleId battleId, TetrisId tetrisId) {
        super(battleId);
        this.tetrisId = tetrisId;
    }

    public TetrisId getTetrisId() {
        return tetrisId;
    }

}
