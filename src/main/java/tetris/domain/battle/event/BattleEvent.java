package tetris.domain.battle.event;

import java.util.EventObject;

import tetris.domain.battle.BattleId;

public class BattleEvent extends EventObject {

    private final long timestamp;

    public BattleEvent(BattleId battleId) {
        super(battleId);
        this.timestamp = System.currentTimeMillis();
    }

    public BattleId getBattleId() {
        return (BattleId ) getSource();
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

}
