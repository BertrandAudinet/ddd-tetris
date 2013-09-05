package tetris.infrastucture.scheduler;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.Tetromino;

public class GameScheduler {
    static Logger log = Logger.getLogger(GameScheduler.class.getName());

    @Autowired
    private GameRepository gameRepository;

    public void run() {
        final List<Game> games = gameRepository.findStartedGames();
        for (Game game : games) {
            if (game.getPiece() == null) {
                game.dropNewPiece(nextTetromino());
            }
            game.fallPiece();
            if (game.getPiece() == null) {
                game.dropNewPiece(nextTetromino());
            }

            gameRepository.store(game);
            log.info("Fallen piece for tetris " + game.getTetrisId());
        }
    }

    Tetromino nextTetromino() {
        final int nextInt = new Random().nextInt(Tetromino.values().length - 1);
        return Tetromino.values()[nextInt];
    }
}
