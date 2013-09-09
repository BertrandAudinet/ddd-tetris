package tetris.domain.battle;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.game.Game;
import tetris.domain.game.TetrisId;

public class Battle {
    private BattleId id;

    private List<TetrisId> opponents = new ArrayList<TetrisId>();

    private BattleStatus status = BattleStatus.AWAITED;

    Battle() {
    }

    public Battle(BattleId id) {
        this.id = id;
    }

    public BattleId getId() {
        return id;
    }

    public void addOpponent(TetrisId opponent) {
        opponents.add(opponent);
    }

    public void start() {
        this.status = BattleStatus.STARTED;
    }

    public List<TetrisId> getOpponents() {
        return opponents;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public void addOpponent(Game tetris) {
        opponents.add(tetris.getTetrisId());
    }

}
