package tetris.domain.battle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tetris.domain.game.Game;

@Service
public class BattleJoinService {

    @Autowired
    private BattleRepository battleRepository;

    public Battle joinBattle(Game tetris) {
        final List<Battle> awaitedBattles = battleRepository.lookupBattle(BattleStatus.AWAITED);
        Battle battle;
        if (awaitedBattles.isEmpty()) {
            battle = new Battle(battleRepository.nextBattleId());
        } else {
            battle = awaitedBattles.get(0);
        }

        battle.addOpponent(tetris.getTetrisId());

        return battle;

    }

}
