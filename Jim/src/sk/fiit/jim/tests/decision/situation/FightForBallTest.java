package sk.fiit.jim.tests.decision.situation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.*;

public class FightForBallTest {
	
	private FightForBall fightForBall = new FightForBall();
	private double i = 1;
	@Before
	public void setup() {
		
		
		// / Create Mock For agentModel
		
		AgentModel agentModel = mock(AgentModel.getInstance().getClass());
		WorldModel worldModel = mock(WorldModel.getInstance().getClass());
		when(agentModel.getDistanceNereastToBall(worldModel.getTeamPlayers())).thenReturn(i);
		when(agentModel.getDistanceNereastToBall(worldModel.getOpponentPlayers())).thenReturn(i);

		// set mock in situation manager
		// TODO use the fucking singleton
		fightForBall.setAgentModel(agentModel);
		System.out.println(agentModel.getDistanceNereastToBall(null));

	}

	@Test
	public void checkSituationFalse() throws Exception {
		
		// check if fightforBall is enable
		i=1;
		Assert.assertEquals(true, fightForBall.checkSituation());
		i=2;
		setup();
		Assert.assertEquals(false, fightForBall.checkSituation());
		}
}
