package tetris.interfaces.playing;

import javax.xml.bind.annotation.XmlRootElement;

import tetris.domain.game.event.TetrisEvent;
import tetris.domain.game.event.TetrisGameStarted;
import tetris.domain.game.event.TetrisLevelUpPerformed;
import tetris.domain.game.event.TetrisLineCleared;
import tetris.domain.game.event.TetrisListener;
import tetris.domain.game.event.TetrisPenaltyLineReceived;
import tetris.domain.game.event.TetrisPieceDropped;
import tetris.domain.game.event.TetrisPieceLocked;
import tetris.domain.game.event.TetrisPieceMoved;
import tetris.domain.game.event.TetrisPieceRotated;
import tetris.domain.game.event.TetrisScoreChanged;

@XmlRootElement(name = "tetrisEvent")
public class TetrisEventDto {
    public static final String TETRIS_STARTED = "TETRIS_STARTED";

    public static final String TETRIS_PIECE_ROTATED = "TETRIS_PIECE_ROTATED";

    public static final String TETRIS_PIECE_MOVED = "TETRIS_PIECE_MOVED";

    public static final String TETRIS_PIECE_DROPPED = "TETRIS_PIECE_DROPPED";

    public static final String TETRIS_PIECE_LOCKED = "TETRIS_PIECE_LOCKED";

    public static final String TETRIS_SCORE_CHANGED = "TETRIS_SCORE_CHANGED";

    public static final String TETRIS_LEVELUP_PERFORMED = "TETRIS_LEVELUP_PERFORMED";

    public static String TETRIS_LINE_CLEARED = "TETRIS_LINE_CLEARED";

    public static String TETRIS_PENALTY_LINE_RECEIVED = "TETRIS_PENALTY_LINE_RECEIVED";

    private String type;

    private int lineCompleted;

    private boolean started;

    private long lastEventId;

    private String tetrisId;

    private PieceDto piece;

    private boolean newPiece;

    private ScoreDto score;

    private int clearLine;

    private int level;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineCompleted() {
        return lineCompleted;
    }

    public void setLineCompleted(int lineCompleted) {
        this.lineCompleted = lineCompleted;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public long getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(long lastEventId) {
        this.lastEventId = lastEventId;
    }

    public static TetrisEventDto map(TetrisEvent event) {
        final TetrisEventDto dto = new TetrisEventDto();
        new TetrisMapper(dto).map(event);
        return dto;
    }

    public String getTetrisId() {
        return tetrisId;
    }

    public void setTetrisId(String tetrisId) {
        this.tetrisId = tetrisId;
    }

    public static final class TetrisMapper implements TetrisListener {
        private final TetrisEventDto dto;

        public TetrisMapper(TetrisEventDto dto) {
            this.dto = dto;
        }

        public void map(TetrisEvent event) {
            dto.setTetrisId(event.getTetrisId().toString());
            dto.setLastEventId(event.getTimestamp());
            if (event instanceof TetrisPieceRotated) {
                pieceRotated((TetrisPieceRotated ) event);
            } else if (event instanceof TetrisLineCleared) {
                lineCleared((TetrisLineCleared ) event);
            } else if (event instanceof TetrisGameStarted) {
                gameStarted((TetrisGameStarted ) event);
            } else if (event instanceof TetrisPieceDropped) {
                pieceDropped((TetrisPieceDropped ) event);
            } else if (event instanceof TetrisPieceMoved) {
                pieceMoved((TetrisPieceMoved ) event);
            } else if (event instanceof TetrisPieceLocked) {
                pieceLocked((TetrisPieceLocked ) event);
            } else if (event instanceof TetrisScoreChanged) {
                scoreChanged((TetrisScoreChanged ) event);
            } else if (event instanceof TetrisPenaltyLineReceived) {
                receiveLinePenalty((TetrisPenaltyLineReceived ) event);
            } else if (event instanceof TetrisLevelUpPerformed) {
                levelUpPerformed((TetrisLevelUpPerformed ) event);
            } else {
                throw new IllegalArgumentException("Cannot map event " + event);
            }
        }

        @Override
        public void pieceRotated(TetrisPieceRotated event) {
            dto.setType(TetrisEventDto.TETRIS_PIECE_ROTATED);
            dto.setPiece(new PieceDto(event.getPiece()));
        }

        @Override
        public void pieceMoved(TetrisPieceMoved event) {
            dto.setType(TetrisEventDto.TETRIS_PIECE_MOVED);
            dto.setPiece(new PieceDto(event.getPiece()));
        }

        @Override
        public void pieceDropped(TetrisPieceDropped event) {
            dto.setType(TetrisEventDto.TETRIS_PIECE_DROPPED);
            dto.setPiece(new PieceDto(event.getPiece()));
            dto.setNewPiece(event.isNewPiece());
        }

        @Override
        public void lineCleared(TetrisLineCleared event) {
            dto.setType(TetrisEventDto.TETRIS_LINE_CLEARED);
            dto.setClearLine(event.getLine());
            dto.setLineCompleted(event.getLineCount());
        }

        @Override
        public void gameStarted(TetrisGameStarted event) {
            dto.setType(TetrisEventDto.TETRIS_STARTED);
            dto.setStarted(event.isStarted());
        }

        @Override
        public void pieceLocked(TetrisPieceLocked event) {
            dto.setType(TetrisEventDto.TETRIS_PIECE_LOCKED);
            dto.setPiece(new PieceDto(event.getPiece()));
        }

        @Override
        public void scoreChanged(TetrisScoreChanged event) {
            dto.setType(TetrisEventDto.TETRIS_SCORE_CHANGED);
            dto.setScore(new ScoreDto(event.getScore()));
        }

        @Override
        public void receiveLinePenalty(TetrisPenaltyLineReceived event) {
            dto.setType(TetrisEventDto.TETRIS_PENALTY_LINE_RECEIVED);
        }

        @Override
        public void levelUpPerformed(TetrisLevelUpPerformed event) {
            dto.setType(TetrisEventDto.TETRIS_LEVELUP_PERFORMED);
            dto.setLevel(event.getLevel());
        }
    }

    public void setPiece(PieceDto piece) {
        this.piece = piece;
    };

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setClearLine(int ClearLine) {
        clearLine = ClearLine;
    }

    public int getClearLine() {
        return clearLine;
    }

    public void setScore(ScoreDto score) {
        this.score = score;
    }

    public ScoreDto getScore() {
        return score;
    }

    public void setNewPiece(boolean newPiece) {
        this.newPiece = newPiece;
    }

    public PieceDto getPiece() {
        return piece;
    }

    public boolean isNewPiece() {
        return newPiece;
    }

}
