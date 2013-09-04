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
        if (piece != null) {
            for (Block block : piece.getBlocks()) {
                grid[block.getY()][block.getX()] = block.getTetromino().name();
            }
        }

        BoardDto boardDto = new BoardDto();
        boardDto.setLevel(0);
        boardDto.setScore(0);
        boardDto.setGrid(grid);
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

}
