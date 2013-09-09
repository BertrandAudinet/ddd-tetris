package tetris.application;

import java.util.List;

import tetris.domain.battle.BattleId;
import tetris.domain.game.TetrisId;

public interface PlayingBattleService {
    BattleId joinBattle(TetrisId tetrisId);

    void startBattle(BattleId battleId);

    List<BattleId> listAwaitedBattles();

}
