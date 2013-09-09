package tetris.infrastucture.persistence.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import tetris.domain.battle.Battle;
import tetris.domain.battle.BattleId;
import tetris.domain.battle.BattleRepository;
import tetris.domain.battle.BattleStatus;

public class InMemoryBattleRepository implements BattleRepository {
    private Map<BattleId, Battle> battleDb = new HashMap<BattleId, Battle>();

    @Override
    public void store(Battle battle) {
        battleDb.put(battle.getId(), battle);
    }

    @Override
    public Battle find(BattleId battleId) {
        return battleDb.get(battleId);
    }

    @Override
    public BattleId nextBattleId() {
        final String random = UUID.randomUUID().toString().toUpperCase();
        return new BattleId(random.substring(0, random.indexOf("-")));
    }

    @Override
    public List<Battle> lookupBattle(BattleStatus status) {
        final List<Battle> lookupBattle = new ArrayList<Battle>();
        final Collection<Battle> values = battleDb.values();
        for (Battle battle : values) {
            if (battle.getStatus().equals(status)) {
                lookupBattle.add(battle);
            }
        }
        return lookupBattle;
    }
}
