package tetris.domain.game;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.game.event.TetrisGameStarted;
import tetris.domain.game.event.TetrisLineCleared;
import tetris.domain.game.event.TetrisListener;
import tetris.domain.game.event.TetrisPenaltyLineReceived;
import tetris.domain.game.event.TetrisPieceDropped;
import tetris.domain.game.event.TetrisPieceLocked;
import tetris.domain.game.event.TetrisPieceMoved;
import tetris.domain.game.event.TetrisPieceRotated;
import tetris.domain.game.event.TetrisScoreChanged;

public class Game {
    private transient List<TetrisListener> listeners = new ArrayList<TetrisListener>();

    private Board board;

    private Shape piece;

    private TetrisId tetrisId;

    private boolean started = false;

    private boolean lost = false;

    private Score score = new Score(0);

    public Game(Board board, Shape piece) {
        this.board = board;
        this.piece = piece;
    }

    public Game(TetrisId tetrisId) {
        this.tetrisId = tetrisId;
        this.board = Board.defaultBoard();
    }

    public void movePiece(Direction direction) {
        final Shape movedShape = piece.move(direction);
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
            firePieceMoved(movedShape);
        }
    }

    public void rotatePiece(Direction direction) {
        final Shape rotatedShape = piece.rotate(direction);
        if (!board.hasCollision(rotatedShape)) {
            this.piece = rotatedShape;
            firePieceRotated(rotatedShape);
        }
    }

    public void dropPiece() {
        final Shape movedShape = piece.moveDown();
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
            firePieceDropped(piece, false);
        }

    }

    public void fallPiece() {
        final Shape movePiece = piece.moveDown();
        if (board.hasCollision(movePiece)) {
            firePieceLocked(piece);
            this.board = board.fillShape(piece);
            this.piece = null;
            // remove completed lines
            final int completedLinesNumber = board.getCompletedLinesNumber();
            final List<Integer> lineClear = board.getLineClear();
            for (Integer line : lineClear) {
                fireLineCleared(line, 1);
            }

            if (completedLinesNumber > 0) {
                score = score.addLines(completedLinesNumber);
                fireScoreChanged(score);
            }

            this.board = board.removeCompletedLines();
        } else {
            this.piece = movePiece;
            firePieceDropped(piece, false);
        }
    }

    public Board getBoard() {
        return board;
    }

    public Shape getPiece() {
        return piece;
    }

    public Score getScore() {
        return score;
    }

    public TetrisId getTetrisId() {
        return tetrisId;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        this.started = true;
        fireGameStarted(true);
    }

    public void stop() {
        this.started = false;
    }

    public void dropNewPiece(Tetromino tetromino) {
        final Shape newPiece = new Shape(3, 0, tetromino);
        if (board.hasCollision(newPiece)) {
            this.lost = true;
            this.started = false;
        } else {
            this.piece = newPiece;
            firePieceDropped(newPiece, true);
        }
    }

    public void addPenaltyLine(int lineCount) {
        this.board = board.insertPenaltyLine(lineCount);
        fireLinePenaltyReceived(lineCount);

    }

    public void addTetrisListener(TetrisListener listener) {
        listeners.add(listener);
    }

    public void removeTetrisListener(TetrisListener listener) {
        listeners.remove(listener);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tetrisId == null) ? 0 : tetrisId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game ) obj;
        if (tetrisId == null) {
            if (other.tetrisId != null)
                return false;
        } else if (!tetrisId.equals(other.tetrisId))
            return false;
        return true;
    }

    protected void fireLineCleared(int line, int lineCount) {
        if (lineCount > 0) {
            for (TetrisListener listener : listeners) {
                TetrisLineCleared event = new TetrisLineCleared(tetrisId, line, lineCount);
                listener.lineCleared(event);
            }
        }
    }

    protected void fireGameStarted(boolean started) {
        for (TetrisListener listener : listeners) {
            TetrisGameStarted event = new TetrisGameStarted(tetrisId, started);
            listener.gameStarted(event);
        }
    }

    protected void firePieceDropped(Shape piece, boolean newPiece) {
        for (TetrisListener listener : listeners) {
            TetrisPieceDropped event = new TetrisPieceDropped(tetrisId, piece, newPiece);
            listener.pieceDropped(event);
        }
    }

    protected void firePieceMoved(Shape piece) {
        for (TetrisListener listener : listeners) {
            TetrisPieceMoved event = new TetrisPieceMoved(tetrisId, piece);
            listener.pieceMoved(event);
        }
    }

    protected void firePieceRotated(Shape piece) {
        for (TetrisListener listener : listeners) {
            TetrisPieceRotated event = new TetrisPieceRotated(tetrisId, piece);
            listener.pieceRotated(event);
        }
    }

    protected void firePieceLocked(Shape piece) {
        for (TetrisListener listener : listeners) {
            TetrisPieceLocked event = new TetrisPieceLocked(tetrisId, piece);
            listener.pieceLocked(event);
        }
    }

    protected void fireScoreChanged(Score score) {
        for (TetrisListener listener : listeners) {
            TetrisScoreChanged event = new TetrisScoreChanged(tetrisId, score);
            listener.scoreChanged(event);
        }
    }

    protected void fireLinePenaltyReceived(int lineCount) {
        for (TetrisListener listener : listeners) {
            TetrisPenaltyLineReceived event = new TetrisPenaltyLineReceived(tetrisId, lineCount);
            listener.receiveLinePenalty(event);
        }

    }
}
