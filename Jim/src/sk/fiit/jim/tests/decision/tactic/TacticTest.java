package sk.fiit.jim.tests.decision.tactic;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.EnemyInFrontOfMe;
import sk.fiit.jim.decision.tactic.defense.DefendAgressive;

/**
 * Test for abstract class we need acctuall instance of this abstract class<br/>
 * that's why we create instance of DefendAgressive
 * 
 * @author  Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public class TacticTest {

	DefendAgressive defend = new DefendAgressive();

	@Test
	public void intiConditionGetter() {
		List<String> currentSituations = new ArrayList<String>();
		currentSituations.add(BallIsOurs.class.getName());
		float x = defend.getSuitability(currentSituations);
		Assert.assertTrue(x==0);
		currentSituations.add(BallIsTheirs.class.getName());
		x = defend.getSuitability(currentSituations);
		Assert.assertTrue(x==((float)1/defend.getPrescribedSituations().size()));
		currentSituations.add(EnemyInFrontOfMe.class.getName());
		x = defend.getSuitability(currentSituations);
		Assert.assertTrue(x==((float)2/defend.getPrescribedSituations().size()));
	}

}
