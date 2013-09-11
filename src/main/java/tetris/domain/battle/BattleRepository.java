package tetris.domain.battle;

import java.util.List;

import tetris.domain.game.TetrisId;

public interface BattleRepository {
    void store(Battle battle);

    Battle find(BattleId battleId);

    public BattleId nextBattleId();

    List<Battle> lookupBattle(BattleStatus status);

    Battle lookupBattleOfTetrisId(TetrisId tetrisId);
}
