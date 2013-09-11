package tetris.domain.battle.event;

import java.util.EventObject;

import tetris.domain.battle.BattleId;

public class BattleEvent extends EventObject {

    public BattleEvent(BattleId battleId) {
        super(battleId);
    }

    public BattleId getBattleId() {
        return (BattleId ) getSource();
    }

}
