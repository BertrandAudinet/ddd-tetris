package tetris.domain.game;

public class Game {
    private Board board;

    private Shape piece;

    private TetrisId tetrisId;

    private boolean lost = false;

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
            final Board filledBoard = board.fillShape(piece);
            this.board = filledBoard;
            this.piece = null;
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

    public TetrisId getTetrisId() {
        return tetrisId;
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

    public void dropNewPiece(Tetromino tetromino) {
        this.piece = new Shape(3, 0, tetromino);
        if (board.hasCollision(piece)) {
            this.lost = true;
        }
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isStarted() {
        return !lost;
    }
}
