package tetris.infrastucture.scheduler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.application.PlayingBattleService;
import tetris.domain.battle.BattleId;

public class BattleScheduler {
    static Logger log = Logger.getLogger(GameScheduler.class.getName());

    @Autowired
    private PlayingBattleService playingBattleService;

    public void run() {
        List<BattleId> battleIds = playingBattleService.listAwaitedBattles();
        for (BattleId battleId : battleIds) {
            playingBattleService.startBattle(battleId);
        }
    }
}
