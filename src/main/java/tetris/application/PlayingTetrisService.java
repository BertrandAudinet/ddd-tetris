package tetris.application;

import java.util.List;

import tetris.domain.game.Direction;
import tetris.domain.game.TetrisId;
import tetris.domain.game.event.TetrisEvent;

public interface PlayingTetrisService {

    TetrisId newTetris();

    void movePiece(TetrisId tetrisId, Direction direction);

    void rotatePiece(TetrisId tetrisId, Direction direction);

    void dropPiece(TetrisId tetrisId);

    void startTetris(TetrisId tetrisId);

    void runTetris(TetrisId tetrisId);

    List<TetrisEvent> getEvents(TetrisId tetrisId, long lastEventId);

}
