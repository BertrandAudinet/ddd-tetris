package tetris.domain.battle;

import junit.framework.Assert;

import org.junit.Test;

public class BattleTest {

    @Test
    public void testConstructor_ReturnsAwaitedBattle() {
        final Battle battle = new Battle();

        Assert.assertEquals(BattleStatus.AWAITED, battle.getStatus());
    }

}
