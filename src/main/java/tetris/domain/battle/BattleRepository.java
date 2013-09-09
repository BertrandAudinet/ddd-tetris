package tetris.domain.battle;

import java.util.List;

public interface BattleRepository {
    void store(Battle battle);

    Battle find(BattleId battleId);

    public BattleId nextBattleId();

    List<Battle> lookupBattle(BattleStatus status);
}
