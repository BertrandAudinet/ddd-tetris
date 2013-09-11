package tetris.infrastucture.persistence.inmemory;

import java.util.Queue;

import tetris.domain.game.event.TetrisEvent;
import tetris.domain.game.event.TetrisEventQueue;

public class InMemoryTetrisEventQueue implements TetrisEventQueue {
    private Queue<TetrisEvent> queue;

    @Override
    public void push(TetrisEvent event) {
        queue.add(event);
    }

    @Override
    public TetrisEvent peek() {
        return queue.peek();
    }

}
