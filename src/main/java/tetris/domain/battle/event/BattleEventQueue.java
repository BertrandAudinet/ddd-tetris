package tetris.domain.battle.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BattleEventQueue implements BattleListener {
    private Queue<BattleEvent> queue;

    public BattleEventQueue(List<BattleEvent> events) {
        queue = new LinkedList<BattleEvent>(events);
    }

    public BattleEventQueue() {
        this(Collections.EMPTY_LIST);
    }

    public void push(BattleEvent event) {
        queue.add(event);
    }

    public BattleEvent peek() {
        return queue.peek();
    }

    public List<BattleEvent> values() {
        final List<BattleEvent> events = new ArrayList<BattleEvent>();
        for (BattleEvent event : queue) {
            events.add(event);
        }
        return events;
    }

    public BattleEventQueue push(BattleEventQueue eventQueue) {
        List<BattleEvent> events = this.values();
        events.addAll(eventQueue.values());
        return new BattleEventQueue(events);
    }

    @Override
    public void penaltyLineAdded(BattlePenaltyLineAdded event) {
        push(event);
    }

    @Override
    public void tetrisJoined(BattleTetrisJoined event) {
        push(event);
    }

    @Override
    public void battleStarted(BattleStarted event) {
        push(event);
    }
}
