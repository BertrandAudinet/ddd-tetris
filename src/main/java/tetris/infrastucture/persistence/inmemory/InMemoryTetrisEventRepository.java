package tetris.infrastucture.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.game.TetrisId;
import tetris.domain.game.event.TetrisEvent;
import tetris.domain.game.event.TetrisEventQueue;
import tetris.domain.game.event.TetrisEventRepository;

public class InMemoryTetrisEventRepository implements TetrisEventRepository {
    private TetrisEventQueue eventQueueDb = new TetrisEventQueue();

    @Override
    public void store(TetrisEventQueue eventQueue) {
        eventQueueDb = eventQueueDb.push(eventQueue);
    }

    @Override
    public List<TetrisEvent> findNextTetrisEvents(TetrisId tetrisId, long lastEventId) {
        final List<TetrisEvent> nextEvents = new ArrayList<TetrisEvent>();
        final List<TetrisEvent> events = eventQueueDb.values();
        for (TetrisEvent tetrisEvent : events) {
            if (tetrisEvent.getTetrisId().equals(tetrisId)) {
                if (tetrisEvent.getTimestamp() > lastEventId) {
                    nextEvents.add(tetrisEvent);
                }
            }
        }
        return nextEvents;
    }
}
