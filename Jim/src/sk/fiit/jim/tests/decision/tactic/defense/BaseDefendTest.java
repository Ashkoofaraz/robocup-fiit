package sk.fiit.jim.tests.decision.tactic.defense;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.EnemyInFrontOfMe;
import sk.fiit.jim.decision.tactic.defense.Defend;

/**
 * Test for abstract class we need acctuall instance of this abstract class<br/>
 * that's why we create instance of Defend
 * 
 * @author  Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public class BaseDefendTest {

	Defend defend = new Defend();

	@Test
	public void ProgressConditionGetter() {
		List<String> currentSituations = new ArrayList<String>();
		currentSituations.add(BallIsTheirs.class.getName());
		Assert.assertFalse(defend.getProgressCondition(currentSituations));
		currentSituations.clear();
		currentSituations.add(BallIsOurs.class.getName());
		currentSituations.add(EnemyInFrontOfMe.class.getName());
		Assert.assertFalse(defend.getProgressCondition(currentSituations));
		currentSituations.clear();
		currentSituations.add(BallIsOurs.class.getName());
		Assert.assertTrue(defend.getProgressCondition(currentSituations));
	}

}
