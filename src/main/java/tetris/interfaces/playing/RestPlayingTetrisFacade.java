package tetris.interfaces.playing;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.application.PlayingTetrisService;
import tetris.domain.game.Block;
import tetris.domain.game.Board;
import tetris.domain.game.Direction;
import tetris.domain.game.Game;
import tetris.domain.game.GameRepository;
import tetris.domain.game.Score;
import tetris.domain.game.Shape;
import tetris.domain.game.TetrisId;

@Path("/playing")
public class RestPlayingTetrisFacade implements PlayingTetrisFacade {
    @Autowired
    private PlayingTetrisService playingTetrisService;

    @Autowired
    GameRepository gameRepository;

    @POST
    @Produces({"application/json" })
    @Override
    public String playNewTetris() {
        final TetrisId tetrisId = playingTetrisService.newTetris();
        return tetrisId.toString();
    }

    @GET
    @Path("/{tetrisId}/board")
    @Produces({"application/json" })
    public BoardDto getBoard(@PathParam("tetrisId")
    String tetrisId) {
        final Game game = gameRepository.find(new TetrisId(tetrisId));

        // Create grid
        final Board board = game.getBoard();
        String[][] grid = new String[board.getHeight()][board.getWidth()];

        // Copy board
        for (Block block : board.getGrid()) {
            if (block != null) {
                grid[block.getY()][block.getX()] = block.getTetromino().name();
            }
        }

        // Copy piece
        final Shape piece = game.getPiece();
        PieceDto pieceDto = null;
        if (piece != null) {
            pieceDto = new PieceDto();
            pieceDto.setRotation(piece.getRotation());
            pieceDto.setTetromino(piece.getTetromino().name());
            pieceDto.setX(piece.getX());
            pieceDto.setY(piece.getY());

            String[][] pieceGrid = new String[Shape.NB_BLOCKS][Shape.NB_BLOCKS];
            for (Block block : piece.getBlocks()) {
                pieceGrid[block.getY() - piece.getY()][block.getX() - piece.getX()] = block.getTetromino().name();
            }
            pieceDto.setGrid(pieceGrid);
        }

        ScoreDto scoreDto = new ScoreDto();
        Score score = game.getScore();
        scoreDto.setLevel(score.getLevel());
        scoreDto.setLines(score.getLines());
        scoreDto.setPoints(score.getPoints());

        BoardDto boardDto = new BoardDto();
        boardDto.setScore(scoreDto);
        boardDto.setGrid(grid);
        boardDto.setPiece(pieceDto);
        boardDto.setGameOver(game.isLost());
        return boardDto;
    }

    @POST
    @Path("/{tetrisId}/move")
    @Consumes({"application/json" })
    @Override
    public void movePiece(@PathParam("tetrisId")
    String tetrisId, MoveDto move) {
        playingTetrisService.movePiece(new TetrisId(tetrisId), Direction.valueOf(move.getDirection()));
    }

    @POST
    @Path("/{tetrisId}/rotate")
    @Consumes({"application/json" })
    @Override
    public void rotatePiece(@PathParam("tetrisId")
    String tetrisId, RotateDto rotate) {
        playingTetrisService.rotatePiece(new TetrisId(tetrisId), Direction.valueOf(rotate.getDirection()));
    }

    @POST
    @Path("/{tetrisId}/drop")
    @Consumes({"application/json" })
    public void drop(@PathParam("tetrisId")
    String tetrisId) {
        playingTetrisService.dropPiece(new TetrisId(tetrisId));
    }

    @GET
    @Path("/{tetrisId}/start")
    public void start(@PathParam("tetrisId")
    String tetrisId) {
        playingTetrisService.startTetris(new TetrisId(tetrisId));
    }
}
