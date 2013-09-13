package tetris.domain.game.event;

import java.util.List;

import tetris.domain.game.TetrisId;

public interface TetrisEventRepository {

    void store(TetrisEventQueue eventQueue);

    List<TetrisEvent> findNextTetrisEvents(TetrisId tetrisId, long lastEventId);

}
