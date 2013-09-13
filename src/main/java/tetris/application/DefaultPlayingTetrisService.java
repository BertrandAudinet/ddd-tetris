package tetris.application;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.game.Direction;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;
import tetris.domain.game.Tetromino;
import tetris.domain.game.event.TetrisAdapter;
import tetris.domain.game.event.TetrisEvent;
import tetris.domain.game.event.TetrisEventQueue;
import tetris.domain.game.event.TetrisEventRepository;
import tetris.domain.game.event.TetrisLineCompleted;
import tetris.domain.game.event.TetrisListener;

@Service
public class DefaultPlayingTetrisService implements PlayingTetrisService {
    static Logger log = Logger.getLogger(DefaultPlayingTetrisService.class.getName());

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private TetrisEventRepository tetrisEventRepository;

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

        final TetrisEventQueue eventQueue = new TetrisEventQueue();
        game.addTetrisListener(eventQueue);

        game.movePiece(direction);

        game.removeTetrisListener(eventQueue);

        gameRepository.store(game);
        tetrisEventRepository.store(eventQueue);
        log.info("Moved piece for tetris " + tetrisId + " to " + direction);
    }

    @Override
    public void rotatePiece(TetrisId tetrisId, Direction direction) {
        final Game game = gameRepository.find(tetrisId);

        final TetrisEventQueue eventQueue = new TetrisEventQueue();
        game.addTetrisListener(eventQueue);

        game.rotatePiece(direction);

        game.removeTetrisListener(eventQueue);

        gameRepository.store(game);
        tetrisEventRepository.store(eventQueue);
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

        final TetrisListener listener = new TetrisAdapter() {

            @Override
            public void lineCompleted(TetrisLineCompleted event) {
                applicationEvents.dispatchTetrisEvent(event);
            }
        };
        game.addTetrisListener(listener);

        final TetrisEventQueue eventQueue = new TetrisEventQueue();
        game.addTetrisListener(eventQueue);

        game.start();

        game.removeTetrisListener(listener);
        game.removeTetrisListener(eventQueue);

        gameRepository.store(game);
        tetrisEventRepository.store(eventQueue);
        log.info("Started tetris " + tetrisId);
    }

    @Override
    public void runTetris(TetrisId tetrisId) {
        final Game game = gameRepository.find(tetrisId);

        final TetrisListener listener = new TetrisAdapter() {

            @Override
            public void lineCompleted(TetrisLineCompleted event) {
                applicationEvents.dispatchTetrisEvent(event);
            }
        };
        game.addTetrisListener(listener);

        final TetrisEventQueue eventQueue = new TetrisEventQueue();
        game.addTetrisListener(eventQueue);

        if (game.getPiece() == null) {
            game.dropNewPiece(nextTetromino());
        }
        game.fallPiece();
        if (game.getPiece() == null) {
            game.dropNewPiece(nextTetromino());
        }

        game.removeTetrisListener(listener);
        game.removeTetrisListener(eventQueue);

        gameRepository.store(game);
        tetrisEventRepository.store(eventQueue);
        log.info("Fallen piece for tetris " + game.getTetrisId());

    }

    Tetromino nextTetromino() {
        final int nextInt = new Random().nextInt(Tetromino.values().length - 1);
        return Tetromino.values()[nextInt];
    }

    @Override
    public List<TetrisEvent> getEvents(TetrisId tetrisId, long lastEventId) {
        return tetrisEventRepository.findNextTetrisEvents(tetrisId, lastEventId);
    }
}
