package sk.fiit.jim.tests.decision.tactic.defense;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.tactic.defense.DefendPosition;

/**
 * Test for Defend Position Class
 * 
 * @author Michal Ceresnak <micoken@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class DefendPositionTest {

	DefendPosition defend = new DefendPosition();
	
	@Test
	public void intiConditionGetter() {
		List<String> currentSituations = new ArrayList<String>();
		currentSituations.add(BallIsOurs.class.getName());
		Assert.assertFalse(defend.getInitCondition(currentSituations));
		currentSituations.clear();
		currentSituations.add(BallIsTheirs.class.getName());
		Assert.assertTrue(defend.getInitCondition(currentSituations));
	}
}
