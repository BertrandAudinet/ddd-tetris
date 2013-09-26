package tetris.domain.game.event;

import tetris.domain.game.TetrisId;

public class TetrisLevelUpPerformed extends TetrisEvent {

    private final int level;

    public TetrisLevelUpPerformed(TetrisId tetrisId, int level) {
        super(tetrisId);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void notify(TetrisListener listener) {
        listener.levelUpPerformed(this);
    }

}
