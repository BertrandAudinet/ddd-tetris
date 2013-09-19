package tetris.application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.battle.Battle;
import tetris.domain.battle.BattleId;
import tetris.domain.battle.BattleJoinService;
import tetris.domain.battle.BattleRepository;
import tetris.domain.battle.BattleStatus;
import tetris.domain.battle.Opponent;
import tetris.domain.battle.event.BattleAdapter;
import tetris.domain.battle.event.BattleEvent;
import tetris.domain.battle.event.BattleEventQueue;
import tetris.domain.battle.event.BattleEventRepository;
import tetris.domain.battle.event.BattleListener;
import tetris.domain.battle.event.BattlePenaltyLineAdded;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;
import tetris.domain.game.event.TetrisEventQueue;
import tetris.domain.game.event.TetrisEventRepository;

@Service
public class DefaultPlayingBattleService implements PlayingBattleService {
    static Logger log = Logger.getLogger(DefaultPlayingBattleService.class.getName());

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BattleJoinService battleJoinService;

    @Autowired
    private BattleEventRepository battleEventRepository;

    @Autowired
    private TetrisEventRepository tetrisEventRepository;

    @Autowired
    private PlayingTetrisService playingTetrisService;

    @Override
    public BattleId joinBattle(TetrisId tetrisId) {
        final Game tetris = gameRepository.find(tetrisId);

        final Battle battle = battleJoinService.joinBattle(tetris);

        final BattleEventQueue eventQueue = new BattleEventQueue();
        battle.addBattleListener(eventQueue);

        battle.addOpponent(tetris.getTetrisId());

        battle.removeBattleListener(eventQueue);

        battleRepository.store(battle);
        battleEventRepository.store(eventQueue);
        log.info("Tetris id " + tetrisId + " joins a battle with battle id " + battle.getBattleId());

        return battle.getBattleId();
    }

    @Override
    public void startBattle(BattleId battleId) {
        final Battle battle = battleRepository.find(battleId);

        final BattleEventQueue eventQueue = new BattleEventQueue();
        battle.addBattleListener(eventQueue);

        battle.start();

        battle.removeBattleListener(eventQueue);

        battleRepository.store(battle);
        battleEventRepository.store(eventQueue);

        final List<Opponent> opponents = battle.getOpponents();
        for (Opponent opponent : opponents) {
            playingTetrisService.startTetris(opponent.getTetrisId());
            // final Game tetris = gameRepository.find(tetrisId);
            //
            // tetris.start();
            // gameRepository.store(tetris);
        }

        log.info("Started new battle with battle id " + battleId);
    }

    @Override
    public List<BattleId> listAwaitedBattles() {
        final List<Battle> lookupBattle = battleRepository.lookupBattle(BattleStatus.AWAITED);
        List<BattleId> awaitedBattles = new ArrayList<BattleId>();
        for (Battle battle : lookupBattle) {
            awaitedBattles.add(battle.getBattleId());
        }
        return awaitedBattles;
    }

    @Override
    public void addPenaltyLine(BattleId battleId, TetrisId tetrisId, int lineCount) {
        final Battle battle = battleRepository.find(battleId);

        final BattleListener listener = new BattleAdapter() {

            @Override
            public void penaltyLineAdded(BattlePenaltyLineAdded event) {
                final Game tetris = gameRepository.find(event.getTetrisId());

                final TetrisEventQueue eventQueue = new TetrisEventQueue();
                tetris.addTetrisListener(eventQueue);

                tetris.addPenaltyLine(event.getLineCount());

                gameRepository.store(tetris);
                tetrisEventRepository.store(eventQueue);
            }
        };
        battle.addBattleListener(listener);

        battle.addPenaltyLine(tetrisId, lineCount);

        battle.removeBattleListener(listener);

        battleRepository.store(battle);
    }

    @Override
    public BattleId getBattle(TetrisId tetrisId) {
        final Battle battle = battleRepository.lookupBattleOfTetrisId(tetrisId);
        if (battle == null) {
            return null;
        }
        return battle.getBattleId();
    }

    @Override
    public List<BattleEvent> getEvents(BattleId battleId, long lastEventId) {
        return battleEventRepository.findNextBattleEvents(battleId, lastEventId);
    }
}
