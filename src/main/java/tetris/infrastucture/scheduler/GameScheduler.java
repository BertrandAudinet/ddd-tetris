package tetris.infrastucture.scheduler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.application.PlayingTetrisService;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;

public class GameScheduler {
    static Logger log = Logger.getLogger(GameScheduler.class.getName());

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayingTetrisService playingTetrisService;

    public void run() {
        final List<Game> games = gameRepository.findStartedGames();
        for (Game game : games) {
            playingTetrisService.runTetris(game.getTetrisId());
        }
    }
}
