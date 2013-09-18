package tetris.domain.battle.event;

import tetris.domain.battle.BattleId;

public class BattleStarted extends BattleEvent {

    private final boolean started;

    public BattleStarted(BattleId battleId, boolean started) {
        super(battleId);
        this.started = started;
    }

    public boolean isStarted() {
        return started;
    }
}
