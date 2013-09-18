package tetris.domain.battle.event;

import java.util.List;

import tetris.domain.battle.BattleId;

public interface BattleEventRepository {
    void store(BattleEventQueue eventQueue);

    List<BattleEvent> findNextBattleEvents(BattleId battleId, long lastEventId);
}
