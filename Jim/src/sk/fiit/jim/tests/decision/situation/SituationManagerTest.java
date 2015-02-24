package sk.fiit.jim.tests.decision.situation;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sk.fiit.jim.decision.situation.*;
import static org.mockito.Mockito.*;

/* Samuel Benkovic
 * RFC megatroll
 * 
 * Test for :
 * getListOfCurrent Situation - if return actual situation
 * 
 */

public class SituationManagerTest {
	private SituationManager situationManager = new SituationManager();

	@Before
	public void setup() {
		ArrayList<Situation> SituationsMock = new ArrayList<Situation>();

		// / Create Mock For FightForBall
		FightForBall fightForBall = mock(FightForBall.class);
		when(fightForBall.checkSituation()).thenReturn(true);
		SituationsMock.add(fightForBall);

		// / Create Mock For FarFromEnemyGoal
		FarFromEnemyGoal farFromEnemyGoal = mock(FarFromEnemyGoal.class);
		when(farFromEnemyGoal.checkSituation()).thenReturn(true);
		SituationsMock.add(farFromEnemyGoal);
		
		// / Create Mock For ballOnGoal
		BallOnGoal ballOnGoal = mock(BallOnGoal.class);
		when(ballOnGoal.checkSituation()).thenReturn(false);
		SituationsMock.add(ballOnGoal);
		
		// set mock in situation manager
		situationManager.setSituations(SituationsMock);

	}

	@Test
	public void TestSituationManager() throws Exception {

		// check if FightForBall and FarFromEnemyGoal is in current list situation and ballOnGoal is not.
		Assert.assertEquals(true, situationManager.getListOfCurrentSituations()
				.get(0).contains("FightForBall"));
		Assert.assertEquals(true, situationManager.getListOfCurrentSituations()
				.get(1).contains("FarFromEnemyGoal"));
		Assert.assertEquals(2, situationManager.getListOfCurrentSituations().size());
	}
				

}
