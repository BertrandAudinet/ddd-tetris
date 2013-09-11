package tetris.application;

import tetris.domain.game.Direction;
import tetris.domain.game.TetrisId;

public interface PlayingTetrisService {

    TetrisId newTetris();

    void movePiece(TetrisId tetrisId, Direction direction);

    void rotatePiece(TetrisId tetrisId, Direction direction);

    void dropPiece(TetrisId tetrisId);

    void startTetris(TetrisId tetrisId);

    void runTetris(TetrisId tetrisId);

}
