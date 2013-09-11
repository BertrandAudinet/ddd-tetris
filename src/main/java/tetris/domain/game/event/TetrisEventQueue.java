package tetris.domain.game.event;

public interface TetrisEventQueue {

    void push(TetrisEvent event);

    TetrisEvent peek();

}
