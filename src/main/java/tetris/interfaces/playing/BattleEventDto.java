package tetris.interfaces.playing;

import javax.xml.bind.annotation.XmlRootElement;

import tetris.domain.battle.event.BattleEvent;
import tetris.domain.battle.event.BattleListener;
import tetris.domain.battle.event.BattlePenaltyLineAdded;
import tetris.domain.battle.event.BattleStarted;
import tetris.domain.battle.event.BattleTetrisJoined;

@XmlRootElement(name = "battleEvent")
public class BattleEventDto {
    public static final String BATTLE_TETRIS_JOINED = "BATTLE_TETRIS_JOINED";

    public static final String BATTLE_STARTED = "BATTLE_STARTED";

    private String type;

    private long lastEventId;

    private String battleId;

    private String tetrisId;

    private boolean started;

    public static BattleEventDto map(BattleEvent event) {
        final BattleEventDto dto = new BattleEventDto();
        new BattleMapper(dto).map(event);
        return dto;
    }

    public static final class BattleMapper implements BattleListener {
        private final BattleEventDto dto;

        public BattleMapper(BattleEventDto dto) {
            this.dto = dto;
        }

        public void map(BattleEvent event) {
            dto.setBattleId(event.getBattleId().toString());
            dto.setLastEventId(event.getTimestamp());
            if (event instanceof BattleTetrisJoined) {
                tetrisJoined((BattleTetrisJoined ) event);
            } else if (event instanceof BattleStarted) {
                battleStarted((BattleStarted ) event);
            } else if (event instanceof BattlePenaltyLineAdded) {
                penaltyLineAdded((BattlePenaltyLineAdded ) event);
            } else {
                throw new IllegalArgumentException("Cannot map event " + event);
            }
        }

        @Override
        public void tetrisJoined(BattleTetrisJoined event) {
            dto.setType(BATTLE_TETRIS_JOINED);
            dto.setTetrisId(event.getTetrisId().toString());
        }

        @Override
        public void penaltyLineAdded(BattlePenaltyLineAdded event) {
        }

        @Override
        public void battleStarted(BattleStarted event) {
            dto.setType(BATTLE_STARTED);
            dto.setStarted(event.isStarted());
        }

    }

    public String getType() {
        return type;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(long lastEventId) {
        this.lastEventId = lastEventId;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public String getTetrisId() {
        return tetrisId;
    }

    public void setTetrisId(String tetrisId) {
        this.tetrisId = tetrisId;
    }

    public boolean isStarted() {
        return started;
    }
}
