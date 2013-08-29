package tetris.application;

import java.util.logging.Logger;

import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;

public class DefaultRunningTetrisService implements RunningTetrisService {
    static Logger log = Logger.getLogger(DefaultPlayingTetrisService.class.getName());

    private GameRepository gameRepository;

    @Override
    public void runTetris(TetrisId tetrisId) {
        final Game game = gameRepository.find(tetrisId);

        game.fallPiece();

        gameRepository.store(game);
        log.info("Fallen piece for tetris " + tetrisId);
    }

}
