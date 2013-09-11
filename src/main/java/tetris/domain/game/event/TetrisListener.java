package tetris.domain.game.event;

import java.util.EventListener;

public interface TetrisListener extends EventListener {
    void lineCompleted(TetrisLineCompleted event);
}
