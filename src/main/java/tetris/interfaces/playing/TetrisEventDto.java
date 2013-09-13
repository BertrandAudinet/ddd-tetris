package tetris.interfaces.playing;

import javax.xml.bind.annotation.XmlRootElement;

import tetris.domain.game.event.TetrisEvent;
import tetris.domain.game.event.TetrisGameStarted;
import tetris.domain.game.event.TetrisLineCompleted;
import tetris.domain.game.event.TetrisListener;
import tetris.domain.game.event.TetrisPieceDropped;
import tetris.domain.game.event.TetrisPieceMoved;
import tetris.domain.game.event.TetrisPieceRotated;

@XmlRootElement(name = "tetrisEvent")
public class TetrisEventDto {
    public static final String TETRIS_STARTED = "TETRIS_STARTED";

    public static final String TETRIS_PIECE_ROTATED = "TETRIS_PIECE_ROTATED";

    public static final String TETRIS_PIECE_MOVED = "TETRIS_PIECE_MOVED";

    public static final String TETRIS_PIECE_DROPPED = "TETRIS_PIECE_DROPPED";

    public static String TETRIS_LINE_COMPLETED = "TETRIS_LINE_COMPLETED";

    private String type;

    private int lineCompleted;

    private boolean started;

    private long lastEventId;

    private String tetrisId;

    private PieceDto piece;

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
            } else if (event instanceof TetrisLineCompleted) {
                lineCompleted((TetrisLineCompleted ) event);
            } else if (event instanceof TetrisGameStarted) {
                gameStarted((TetrisGameStarted ) event);
            } else if (event instanceof TetrisPieceDropped) {
                pieceDropped((TetrisPieceDropped ) event);
            } else if (event instanceof TetrisPieceMoved) {
                pieceMoved((TetrisPieceMoved ) event);
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
        }

        @Override
        public void lineCompleted(TetrisLineCompleted event) {
            dto.setType(TetrisEventDto.TETRIS_LINE_COMPLETED);
            dto.setLineCompleted(event.getLineCount());
        }

        @Override
        public void gameStarted(TetrisGameStarted event) {
            dto.setType(TetrisEventDto.TETRIS_STARTED);
            dto.setStarted(event.isStarted());
        }
    }

    public void setPiece(PieceDto piece) {
        this.piece = piece;
    };

    public PieceDto getPiece() {
        return piece;
    }

}
