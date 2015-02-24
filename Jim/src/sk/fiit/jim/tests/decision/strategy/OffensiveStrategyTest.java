package sk.fiit.jim.tests.decision.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import sk.fiit.jim.decision.situation.*;
import sk.fiit.jim.decision.situation.octan.*;
import sk.fiit.jim.decision.strategy.*;


/**
 * @author Michal Ceresnak
 * @team Team 4 2013/2014
 */
public class OffensiveStrategyTest {

	private OffensiveStrategy offensiveStrategy = new OffensiveStrategy();

	public boolean isSuitable(List<String> currentSituations) throws Exception {
		if (offensiveStrategy.getSuitability(currentSituations) == 0)
			return false;
		else
			return true;
	}

	@Test
	public void test() throws Exception {
		List<String> notSuitableCurrentSituations = new ArrayList<String>(
				Arrays.asList(FarFromEnemyGoal.class.getName(),
						BallOnGoal.class.getName(), MeIn1L.class.getName(),
						MeIn1R.class.getName(), MeIn2L.class.getName(),
						MeIn2R.class.getName(), MeIn3L.class.getName(),
						MeIn3R.class.getName(), MeIn4L.class.getName(),
						MeIn4R.class.getName()));

		List<String> suitableCurrentSituations = new ArrayList<String>(
				Arrays.asList(FightForBall.class.getName()));

		Assert.assertEquals(false, isSuitable(notSuitableCurrentSituations));
		Assert.assertEquals(true, isSuitable(suitableCurrentSituations));
	}
}
