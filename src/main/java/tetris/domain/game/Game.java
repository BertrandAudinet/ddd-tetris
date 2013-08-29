package tetris.domain.game;

public class Game {
    private Board board;

    private Shape piece;

    public Game(Board board, Shape piece) {
        this.board = board;
        this.piece = piece;
    }

    public void movePieceLeft() {
        final Shape movedShape = piece.moveLeft();
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
        }
    }

    public void movePieceRight() {
        final Shape movedShape = piece.moveRight();
        if (!board.hasCollision(movedShape)) {
            this.piece = movedShape;
        }
    }

    public void rotatePieceLeft() {
        final Shape rotatedShape = piece.rotateLeft();
        if (!board.hasCollision(rotatedShape)) {
            this.piece = rotatedShape;
        }
    }

    public void rotatePieceRight() {
        final Shape rotatedPiece = piece.rotateRight();
        if (!board.hasCollision(rotatedPiece)) {
            this.piece = rotatedPiece;
        }
    }

    public void fallPiece() {
        final Shape movePiece = piece.moveDown();
        if (board.hasCollision(movePiece)) {
            final Board filledBoard = board.fillShape(piece);
            this.board = filledBoard;
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
}
