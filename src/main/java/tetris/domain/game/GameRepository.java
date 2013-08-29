package tetris.domain.game;

public interface GameRepository {
    void store(Game game);

    Game find(TetrisId tetrisId);

    public TetrisId nextTetrisId();
}
