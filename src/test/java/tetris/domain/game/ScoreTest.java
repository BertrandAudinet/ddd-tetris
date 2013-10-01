package tetris.domain.game;

import org.junit.Assert;
import org.junit.Test;

public class ScoreTest {

    @Test
    public void testConstrutor_AnyLevel_ScoreIsZero() {
        final Score score = new Score(1);

        Score expected = new Score(1, 0, 0);
        Assert.assertEquals(expected, score);
    }

    @Test
    public void testConstrutorLevelZero_0Lines_PointsAreEqual0() {
        final Score score = new Score(1, 0);

        Score expected = new Score(1, 0, 0);
        Assert.assertEquals(expected, score);
    }

    @Test
    public void testConstrutorLevelZero_1Lines_PointsAreEqual100() {
        final Score score = new Score(1, 1);

        Score expected = new Score(1, 1, 100);
        Assert.assertEquals(expected, score);
    }

    @Test
    public void testConstrutorLevelZero_2Lines_PointsAreEqual300() {
        final Score actual = new Score(1, 2);

        Score expected = new Score(1, 2, 300);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConstrutorLevelZero_3Lines_PointsAreEqual500() {
        final Score actual = new Score(1, 3);

        Score expected = new Score(1, 3, 500);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConstrutorLevelZero_4Lines_PointsAreEqual800() {
        final Score actual = new Score(1, 4);

        Score expected = new Score(1, 4, 800);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddScore_7Lines_PointsAreAdded() {
        final Score score = new Score(1, 3);

        final Score actual = score.add(new Score(1, 4));

        Score expected = new Score(1, 7, 1300);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddScore_10Lines_levelUpIsDone() {
        final Score score = new Score(1, 6, 600);

        final Score actual = score.add(new Score(1, 4));

        Score expected = new Score(2, 10, 1400);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddLine_SomeLine_LineIsIncreased() {
        Score score = new Score(1, 1, 0);

        Score actual = score.addLines(2);

        int expected = 3;
        Assert.assertEquals(expected, actual.getLines());
    }

    @Test
    public void testAddLine_OneLine_PointsAreIncreased100() {
        Score score = new Score(1, 0, 0);

        Score actual = score.addLines(1);

        int expected = 100;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_TwoLine_PointsAreIncreased300() {
        Score score = new Score(1, 0, 0);

        Score actual = score.addLines(2);

        int expected = 300;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_ThreeLine_PointsAreIncreased500() {
        Score score = new Score(1, 0, 0);

        Score actual = score.addLines(3);

        int expected = 500;
        Assert.assertEquals(expected, actual.getPoints());
    }

    @Test
    public void testAddLine_ThreeLine_PointsAreIncreased800() {
        Score score = new Score(1, 0, 0);

        Score actual = score.addLines(4);

        int expected = 800;
        Assert.assertEquals(expected, actual.getPoints());
    }
}
