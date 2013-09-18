package tetris.application;

import java.util.List;

import tetris.domain.battle.BattleId;
import tetris.domain.battle.event.BattleEvent;
import tetris.domain.game.TetrisId;

public interface PlayingBattleService {
    BattleId joinBattle(TetrisId tetrisId);

    void startBattle(BattleId battleId);

    List<BattleId> listAwaitedBattles();

    void addPenaltyLine(BattleId battleId, TetrisId tetrisId, int lineCount);

    BattleId getBattle(TetrisId tetrisId);

    List<BattleEvent> getEvents(BattleId battleId, long lastEventId);
}
