package tetris.domain.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tetris.domain.battle.event.BattleEvent;
import tetris.domain.battle.event.BattleListener;
import tetris.domain.battle.event.BattlePenaltyLineAdded;
import tetris.domain.battle.event.BattleTetrisJoined;
import tetris.domain.game.TetrisId;

public class Battle {
    private transient List<BattleListener> listeners = new ArrayList<BattleListener>();

    public void addBattleListener(BattleListener listener) {
        listeners.add(listener);
    }

    public void removeBattleListener(BattleListener listener) {
        listeners.remove(listener);
    }

    private BattleId battleId;

    private List<Opponent> opponents = new ArrayList<Opponent>();

    private BattleStatus status = BattleStatus.AWAITED;

    Battle() {
    }

    public Battle(BattleId battleId) {
        this.battleId = battleId;
    }

    public BattleId getBattleId() {
        return battleId;
    }

    public void addOpponent(TetrisId tetrisId) {
        opponents.add(new Opponent(tetrisId));
        fireBattleEvent(new BattleTetrisJoined(battleId, tetrisId));
    }

    public void start() {
        this.status = BattleStatus.STARTED;
    }

    public List<TetrisId> getOpponents() {
        return Collections.EMPTY_LIST;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public List<Opponent> getOpponentsOf(TetrisId tetrisId) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean contains(TetrisId tetrisId) {
        for (Opponent opponent : opponents) {
            if (opponent.getTetrisId().equals(tetrisId)) {
                return true;
            }
        }
        return false;
    }

    public void addPenaltyLine(TetrisId tetrisId, int lineCount) {
        for (Opponent opponent : opponents) {
            if (opponent.isOpponent(tetrisId)) {
                opponent = opponent.addPenaltyLine(lineCount);
                fireBattleEvent(new BattlePenaltyLineAdded(battleId, opponent.getTetrisId(), lineCount));
            }
        }
    }

    protected void fireBattleEvent(BattleEvent event) {
        for (BattleListener listener : listeners) {
            if (event instanceof BattleTetrisJoined) {
                listener.tetrisJoined((BattleTetrisJoined ) event);
            } else if (event instanceof BattlePenaltyLineAdded) {
                listener.penaltyLineAdded((BattlePenaltyLineAdded ) event);
            }
        }
    }

    public Opponent getOpponent(TetrisId tetrisId) {
        for (Opponent opponent : opponents) {
            if (opponent.getTetrisId().equals(tetrisId)) {
                return opponent;
            }
        }
        return null;
    }

}
