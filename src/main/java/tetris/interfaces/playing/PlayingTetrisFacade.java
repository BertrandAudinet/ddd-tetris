package tetris.interfaces.playing;


public interface PlayingTetrisFacade {
    String playNewTetris();

    BoardDto getBoard(String tetrisId);

    void movePiece(String tetrisId, MoveDto move);

    void rotatePiece(String tetrisId, RotateDto rotate);
}
