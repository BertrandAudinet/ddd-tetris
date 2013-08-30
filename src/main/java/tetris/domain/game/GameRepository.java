package tetris.domain.game;

import java.util.List;

public interface GameRepository {
    void store(Game game);

    Game find(TetrisId tetrisId);

    public TetrisId nextTetrisId();

    List<Game> findAll();
}
