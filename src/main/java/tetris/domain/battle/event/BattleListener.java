package tetris.domain.battle.event;

import java.util.EventListener;

public interface BattleListener extends EventListener {
    void penaltyLineAdded(BattlePenaltyLineAdded event);

    void tetrisJoined(BattleTetrisJoined event);

    void battleStarted(BattleStarted event);
}
