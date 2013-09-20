package tetris.domain.game.event;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import tetris.domain.game.TetrisId;

public class TetrisEventQueueTest {

    private final static TetrisId A_TETRIS_ID = new TetrisId("T1");

    @Test
    public void testGetLastEvent_OneEvent_ReturnsEvent() {
        final TetrisEventQueue tetrisEventQueue = new TetrisEventQueue();

        final TetrisGameStarted event = new TetrisGameStarted(A_TETRIS_ID, false);
        tetrisEventQueue.gameStarted(event);

        final List<TetrisEvent> events = new ArrayList<TetrisEvent>();
        events.add(event);

        TetrisEvent actual = tetrisEventQueue.getLastEvent();
        TetrisGameStarted expected = event;
        Assert.assertEquals(expected, actual);
    }
}
