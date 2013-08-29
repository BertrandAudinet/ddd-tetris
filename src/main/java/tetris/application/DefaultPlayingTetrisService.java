package tetris.application;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.game.Direction;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;

@Service
public class DefaultPlayingTetrisService implements PlayingTetrisService {
    static Logger log = Logger.getLogger(DefaultPlayingTetrisService.class.getName());

    @Autowired
    private GameRepository gameRepository;

    @Override
    public TetrisId newTetris() {
        final TetrisId tetrisId = gameRepository.nextTetrisId();
        final Game game = new Game(tetrisId);

        gameRepository.store(game);
        log.info("Played new tetris with tetris id " + tetrisId);

        return tetrisId;
    }

    @Override
    public void movePiece(TetrisId tetrisId, Direction direction) {
        final Game game = gameRepository.find(tetrisId);

        game.movePiece(direction);

        gameRepository.store(game);
        log.info("Moved piece for tetris " + tetrisId + " to " + direction);
    }

    @Override
    public void rotatePiece(TetrisId tetrisId, Direction direction) {
        final Game game = gameRepository.find(tetrisId);

        game.rotatePiece(direction);

        gameRepository.store(game);
        log.info("Rotated piece for tetris " + tetrisId + " to " + direction);
    }

}
