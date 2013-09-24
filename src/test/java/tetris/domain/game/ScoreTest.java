package tetris.domain.game;

import org.junit.Assert;
import org.junit.Test;

public class ScoreTest {

    @Test
    public void testConstrutor_AnyLevel_ScoreIsZero() {
        final Score score = new Score(0);

        Score expected = new Score(0, 0, 0);
        Assert.assertEquals(expected, score);
    }

    @Test
    public void testAddLine_SomeLine_LineIsIncreased() {
        Score score = new Score(0, 1, 0);

        Score actual = score.addLines(2);

        int expected = 3;
        Assert.assertEquals(expected, actual.getLines());
    }

    @Test
    public void testAddLine_OneLine_PointsAreIncreased100() {
        Score score = new Score(0, 0, 0);

        Score actual = score.addLines(1);

        int expected = 100;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_TwoLine_PointsAreIncreased300() {
        Score score = new Score(0, 0, 0);

        Score actual = score.addLines(2);

        int expected = 300;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_ThreeLine_PointsAreIncreased500() {
        Score score = new Score(0, 0, 0);

        Score actual = score.addLines(3);

        int expected = 500;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_ThreeLine_PointsAreIncreased800() {
        Score score = new Score(0, 0, 0);

        Score actual = score.addLines(4);

        int expected = 800;
        Assert.assertEquals(expected, actual.getPoints());
    }
}
