package tetris.interfaces.playing;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import tetris.application.PlayingBattleService;
import tetris.domain.battle.Battle;
import tetris.domain.battle.BattleId;
import tetris.domain.battle.BattleRepository;
import tetris.domain.battle.Opponent;
import tetris.domain.battle.event.BattleEvent;
import tetris.domain.game.TetrisId;

@Path("/battles")
public class RestPlayingBattleFacade {

    @Autowired
    private PlayingBattleService playingBattleService;

    @Autowired
    private BattleRepository battleRepository;

    @POST
    @Produces({"application/json" })
    public String joinBattle(@QueryParam("tetrisId")
    String tetrisId) {
        BattleId battleId = playingBattleService.joinBattle(new TetrisId(tetrisId));
        return battleId.toString();
    }

    @GET
    @Path("/{battleId}")
    @Produces({"application/json" })
    public BattleDto getBattle(@PathParam("battleId")
    BattleId battleId) {
        final Battle battle = battleRepository.find(battleId);

        final BattleDto battleDto = new BattleDto();
        battleDto.setBattleId(battle.getBattleId().toString());

        battleDto.setStatus(battle.getStatus().name());

        final List<Opponent> opponents = battle.getOpponents();
        final List<String> listOpponentDto = new ArrayList<String>();
        for (Opponent opponent : opponents) {
            listOpponentDto.add(opponent.getTetrisId().toString());
        }
        battleDto.setOpponents(listOpponentDto);

        return battleDto;
    }

    @GET
    @Path("/{battleId}/events")
    @Produces({"application/json" })
    public List<BattleEventDto> getEvents(@PathParam("battleId")
    String battleId, @QueryParam("lastEventId")
    long lastEventId) {
        final List<BattleEventDto> battleEventsDto = new ArrayList<BattleEventDto>();
        final List<BattleEvent> events = playingBattleService.getEvents(new BattleId(battleId), lastEventId);
        for (BattleEvent event : events) {
            final BattleEventDto battleEventDto = BattleEventDto.map(event);
            battleEventsDto.add(battleEventDto);
        }
        return battleEventsDto;
    }
}
