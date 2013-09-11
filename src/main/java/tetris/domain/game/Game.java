package tetris.domain.game;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.game.event.TetrisLineCompleted;
import tetris.domain.game.event.TetrisListener;

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
        }
    }

    public void rotatePiece(Direction direction) {
        final Shape rotatedShape = piece.rotate(direction);
        if (!board.hasCollision(rotatedShape)) {
            this.piece = rotatedShape;
        }
    }

    public void dropPiece() {
        final Shape movedShape = piece.moveDown();
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
        }

    }

    public void fallPiece() {
        final Shape movePiece = piece.moveDown();
        if (board.hasCollision(movePiece)) {
            this.board = board.fillShape(piece);
            this.piece = null;
            // remove completed lines
            score = score.addLines(board.getCompletedLinesNumber());
            fireLineCompleted(board.getCompletedLinesNumber());
            this.board = board.removeCompletedLines();
        } else {
            this.piece = movePiece;
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
    }

    public void stop() {
        this.started = false;
    }

    public void dropNewPiece(Tetromino tetromino) {
        this.piece = new Shape(3, 0, tetromino);
        if (board.hasCollision(piece)) {
            this.lost = true;
            this.started = false;
        }
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

    protected void fireLineCompleted(int lineCount) {
        if (lineCount > 0) {
            for (TetrisListener listener : listeners) {
                TetrisLineCompleted event = new TetrisLineCompleted(tetrisId, lineCount);
                listener.lineCompleted(event);
            }
        }
    }

    public void addPenaltyLine(int lineCount) {
        this.board = board.insertPenaltyLine(lineCount);

    }

}
