package tetris.interfaces.playing;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.application.PlayingTetrisService;
import tetris.domain.game.Direction;
import tetris.domain.game.TetrisId;

@Path("/playing")
public class RestPlayingTetrisFacade implements PlayingTetrisFacade {
    @Autowired
    private PlayingTetrisService playingTetrisService;

    @POST
    @Produces({"application/json" })
    @Override
    public String playNewTetris() {
        final TetrisId tetrisId = playingTetrisService.newTetris();
        return tetrisId.toString();
    }

    @Override
    public void movePiece(String tetrisId, String direction) {
        playingTetrisService.movePiece(new TetrisId(tetrisId), Direction.valueOf(direction));
    }

    @Override
    public void rotatePiece(String tetrisId, String direction) {
        playingTetrisService.rotatePiece(new TetrisId(tetrisId), Direction.valueOf(direction));
    }

}
