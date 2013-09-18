package tetris.infrastucture.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.battle.BattleId;
import tetris.domain.battle.event.BattleEvent;
import tetris.domain.battle.event.BattleEventQueue;
import tetris.domain.battle.event.BattleEventRepository;

public class InMemoryBattleEventRepository implements BattleEventRepository {
    private BattleEventQueue eventQueueDb = new BattleEventQueue();

    @Override
    public void store(BattleEventQueue eventQueue) {
        eventQueueDb = eventQueueDb.push(eventQueue);
    }

    @Override
    public List<BattleEvent> findNextBattleEvents(BattleId battleId, long lastEventId) {
        final List<BattleEvent> nextEvents = new ArrayList<BattleEvent>();
        final List<BattleEvent> events = eventQueueDb.values();
        for (BattleEvent event : events) {
            if (event.getBattleId().equals(battleId)) {
                if (event.getTimestamp() > lastEventId) {
                    nextEvents.add(event);
                }
            }
        }
        return nextEvents;
    }
}
