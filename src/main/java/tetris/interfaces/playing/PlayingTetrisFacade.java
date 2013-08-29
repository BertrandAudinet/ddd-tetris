package tetris.interfaces.playing;

public interface PlayingTetrisFacade {
    String playNewTetris();

    void movePiece(String tetrisId, String direction);

    void rotatePiece(String tetrisId, String direction);
}
