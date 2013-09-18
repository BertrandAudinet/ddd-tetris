package tetris.domain.battle;

import org.junit.Assert;
import org.junit.Test;

import tetris.domain.battle.event.BattleAdapter;
import tetris.domain.battle.event.BattlePenaltyLineAdded;
import tetris.domain.battle.event.BattleTetrisJoined;
import tetris.domain.game.TetrisId;

public class BattleTest {

    @Test
    public void testConstructor_ReturnsAwaitedBattle() {
        final Battle battle = new Battle();

        Assert.assertEquals(BattleStatus.AWAITED, battle.getStatus());
    }

    @Test
    public void testAddPenaltyLine_SendEvent_BattlePenaltyLineAdded() {
        final Battle battle = new Battle(new BattleId("B1"));
        battle.addOpponent(new TetrisId("T1"));
        battle.addOpponent(new TetrisId("T2"));

        battle.addBattleListener(new BattleAdapter() {

            @Override
            public void penaltyLineAdded(BattlePenaltyLineAdded event) {
                Assert.assertEquals(new TetrisId("T2"), event.getTetrisId());

            }

            @Override
            public void tetrisJoined(BattleTetrisJoined event) {
                // TODO Auto-generated method stub

            }
        });
        battle.addPenaltyLine(new TetrisId("T1"), 1);

    }

    @Test
    public void testAddPenaltyLine_() {
        final Battle battle = new Battle(new BattleId("B1"));
        battle.addOpponent(new TetrisId("T1"));
        battle.addOpponent(new TetrisId("T2"));

        battle.addPenaltyLine(new TetrisId("T1"), 1);

        final int actual = battle.getOpponent(new TetrisId("T1")).getPenaltyLineCount();

        Assert.assertEquals(1, actual);
    }
}
