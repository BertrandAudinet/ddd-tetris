package tetris.domain.game;

import java.util.ArrayList;
import java.util.List;

import tetris.domain.game.event.TetrisEvent;
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

    public Game(TetrisId tetrisId, Board board, Shape piece) {
        this.tetrisId = tetrisId;
        this.board = board;
        this.piece = piece;
    }

    public void movePiece(Direction direction) {
        final Shape movedShape = piece.move(direction);
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
            TetrisPieceMoved event = new TetrisPieceMoved(tetrisId, movedShape);
            fireTetrisEvent(event);
        }
    }

    public void rotatePiece(Direction direction) {
        final Shape rotatedShape = piece.rotate(direction);
        if (!board.hasCollision(rotatedShape)) {
            this.piece = rotatedShape;
            TetrisPieceRotated event = new TetrisPieceRotated(tetrisId, rotatedShape);
            fireTetrisEvent(event);
        }
    }

    public void dropPiece() {
        final Shape movedShape = piece.moveDown();
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
            TetrisPieceDropped event = new TetrisPieceDropped(tetrisId, piece, false);
            fireTetrisEvent(event);
        }

    }

    public void fallPiece() {
        final Shape movePiece = piece.moveDown();
        if (board.hasCollision(movePiece)) {
            TetrisPieceLocked event1 = new TetrisPieceLocked(tetrisId, piece);
            fireTetrisEvent(event1);
            this.board = board.fillShape(piece);
            this.piece = null;
            final List<Integer> completedLine = board.getCompletedLine();
            for (Integer line : completedLine) {
                board = board.removeLine(line);
                TetrisLineCleared event = new TetrisLineCleared(tetrisId, line, 1);
                fireTetrisEvent(event);
            }

            if (completedLine.size() > 0) {
                score = score.addLines(completedLine.size());
                TetrisScoreChanged event = new TetrisScoreChanged(tetrisId, score);
                fireTetrisEvent(event);
            }
        } else {
            this.piece = movePiece;
            TetrisPieceDropped event = new TetrisPieceDropped(tetrisId, piece, false);
            fireTetrisEvent(event);
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
        TetrisGameStarted event = new TetrisGameStarted(tetrisId, true);
        fireTetrisEvent(event);
    }

    public void stop() {
        this.started = false;
        TetrisGameStarted event = new TetrisGameStarted(tetrisId, false);
        fireTetrisEvent(event);
    }

    public void dropNewPiece(Tetromino tetromino) {
        final Shape newPiece = new Shape(3, 0, tetromino);
        if (board.hasCollision(newPiece)) {
            this.lost = true;
            stop();
        } else {
            this.piece = newPiece;
            TetrisPieceDropped event = new TetrisPieceDropped(tetrisId, newPiece, true);
            fireTetrisEvent(event);
        }
    }

    public void addPenaltyLine(int lineCount) {
        int lastLine = board.getHeight() - 1;
        for (int i = 0; i < lineCount; i++) {
            board = board.insertLine(lastLine);
        }
        fireTetrisEvent(new TetrisPenaltyLineReceived(tetrisId, lineCount));

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

    protected void fireTetrisEvent(TetrisEvent event) {
        for (TetrisListener listener : listeners) {
            event.notify(listener);
        }
    }
}
