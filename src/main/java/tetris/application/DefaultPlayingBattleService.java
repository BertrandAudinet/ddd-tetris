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
import tetris.domain.battle.event.BattleListener;
import tetris.domain.battle.event.BattlePenaltyLineAdded;
import tetris.domain.battle.event.BattleTetrisJoined;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.TetrisId;

@Service
public class DefaultPlayingBattleService implements PlayingBattleService {
    static Logger log = Logger.getLogger(DefaultPlayingBattleService.class.getName());

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BattleJoinService battleJoinService;

    @Override
    public BattleId joinBattle(TetrisId tetrisId) {
        final Game tetris = gameRepository.find(tetrisId);

        final Battle battle = battleJoinService.joinBattle(tetris);
        battle.addBattleListener(new BattleListener() {

            @Override
            public void tetrisJoined(BattleTetrisJoined event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void penaltyLineAdded(BattlePenaltyLineAdded event) {
                // TODO Auto-generated method stub

            }
        });

        battleRepository.store(battle);
        log.info("Tetris id " + tetrisId + " joins a battle with battle id " + battle.getBattleId());

        return battle.getBattleId();
    }

    @Override
    public void startBattle(BattleId battleId) {
        final Battle battle = battleRepository.find(battleId);

        battle.start();
        battleRepository.store(battle);

        final List<TetrisId> opponents = battle.getOpponents();
        for (TetrisId tetrisId : opponents) {
            final Game tetris = gameRepository.find(tetrisId);

            tetris.start();
            gameRepository.store(tetris);
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
        final BattleListener listener = new BattleListener() {

            @Override
            public void tetrisJoined(BattleTetrisJoined event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void penaltyLineAdded(BattlePenaltyLineAdded event) {
                final Game tetris = gameRepository.find(event.getTetrisId());
                tetris.addPenaltyLine(event.getLineCount());
                gameRepository.store(tetris);
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
}
