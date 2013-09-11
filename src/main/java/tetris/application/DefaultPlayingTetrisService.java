package tetris.application;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.game.Direction;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;
import tetris.domain.game.Tetromino;
import tetris.domain.game.event.TetrisLineCompleted;
import tetris.domain.game.event.TetrisListener;

@Service
public class DefaultPlayingTetrisService implements PlayingTetrisService {
    static Logger log = Logger.getLogger(DefaultPlayingTetrisService.class.getName());

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ApplicationEvents applicationEvents;

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

    @Override
    public void dropPiece(TetrisId tetrisId) {
        final Game game = gameRepository.find(tetrisId);

        game.dropPiece();

        gameRepository.store(game);
        log.info("Dropped piece for tetris " + tetrisId);
    }

    @Override
    public void startTetris(TetrisId tetrisId) {
        final Game game = gameRepository.find(tetrisId);

        game.start();

        gameRepository.store(game);
        log.info("Started tetris " + tetrisId);
    }

    @Override
    public void runTetris(TetrisId tetrisId) {
        final Game game = gameRepository.find(tetrisId);
        final TetrisListener listener = new TetrisListener() {

            @Override
            public void lineCompleted(TetrisLineCompleted event) {
                applicationEvents.dispatchTetrisEvent(event);
            }
        };
        game.addTetrisListener(listener);

        if (game.getPiece() == null) {
            game.dropNewPiece(nextTetromino());
        }
        game.fallPiece();
        if (game.getPiece() == null) {
            game.dropNewPiece(nextTetromino());
        }

        game.removeTetrisListener(listener);
        gameRepository.store(game);
        log.info("Fallen piece for tetris " + game.getTetrisId());

    }

    Tetromino nextTetromino() {
        final int nextInt = new Random().nextInt(Tetromino.values().length - 1);
        return Tetromino.values()[nextInt];
    }
}
