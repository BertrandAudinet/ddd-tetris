package tetris.application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.battle.Battle;
import tetris.domain.battle.BattleId;
import tetris.domain.battle.BattleRepository;
import tetris.domain.battle.BattleStatus;
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

    @Override
    public BattleId joinBattle(TetrisId tetrisId) {
        final List<Battle> awaitedBattles = battleRepository.lookupBattle(BattleStatus.AWAITED);
        Battle battle;
        if (awaitedBattles.isEmpty()) {
            battle = new Battle(battleRepository.nextBattleId());
        } else {
            battle = awaitedBattles.get(0);
        }

        battle.addOpponent(tetrisId);

        battleRepository.store(battle);
        log.info("Tetris id " + tetrisId + " joins a battle with battle id " + battle.getId());

        return battle.getId();
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
            awaitedBattles.add(battle.getId());
        }
        return awaitedBattles;
    }
}
