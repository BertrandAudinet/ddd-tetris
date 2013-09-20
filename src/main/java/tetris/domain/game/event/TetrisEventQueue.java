package tetris.domain.game.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TetrisEventQueue implements TetrisListener {
    private Queue<TetrisEvent> queue;

    public TetrisEventQueue(List<TetrisEvent> events) {
        queue = new LinkedList<TetrisEvent>(events);
    }

    public TetrisEventQueue() {
        this(Collections.EMPTY_LIST);
    }

    public void push(TetrisEvent event) {
        queue.add(event);
    }

    public TetrisEvent peek() {
        return queue.peek();
    }

    public List<TetrisEvent> values() {
        final List<TetrisEvent> events = new ArrayList<TetrisEvent>();
        for (TetrisEvent tetrisEvent : queue) {
            events.add(tetrisEvent);
        }
        return events;
    }

    public TetrisEventQueue push(TetrisEventQueue eventQueue) {
        List<TetrisEvent> events = this.values();
        events.addAll(eventQueue.values());
        return new TetrisEventQueue(events);
    }

    @Override
    public void lineCleared(TetrisLineCleared event) {
        push(event);
    }

    @Override
    public void gameStarted(TetrisGameStarted event) {
        push(event);
    }

    @Override
    public void pieceDropped(TetrisPieceDropped event) {
        push(event);
    }

    @Override
    public void pieceMoved(TetrisPieceMoved event) {
        push(event);
    }

    @Override
    public void pieceRotated(TetrisPieceRotated event) {
        push(event);
    }

    @Override
    public void pieceLocked(TetrisPieceLocked event) {
        push(event);
    }

    @Override
    public void scoreChanged(TetrisScoreChanged event) {
        push(event);
    }

    @Override
    public void receiveLinePenalty(TetrisPenaltyLineReceived event) {
        push(event);
    }

    public TetrisEvent getLastEvent() {
        return queue.peek();
    }

}
