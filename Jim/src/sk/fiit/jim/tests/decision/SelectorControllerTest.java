package sk.fiit.jim.tests.decision;

/**
 * Class for test a selectorController class. 
 * 
 * @author  Samuel Benkovic <sppred@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 */

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.models.EnvironmentModel.PlayMode;
import sk.fiit.jim.agent.parsing.ParsedData;
import sk.fiit.jim.decision.*;
import sk.fiit.jim.decision.situation.SituationManager;

public class SelectorControllerTest {
	private SelectorController selectorController = new SelectorController ();
	
	@BeforeClass
	public static void initModels(){
		ParsedData data = new ParsedData();
		data.playMode=PlayMode.BEFORE_KICK_OFF;
		EnvironmentModel.getInstance().processNewServerMessage(data);
	}
	
	@Before
	/** 
	 * mock for ControlTactics Test 
	 */
	public void mockforControlTacticsTest() throws Exception{
		
		/// Create Mock For situationManager and method getListOfCurrentSituations
		List<String> CurrentSituations =  new ArrayList<String>();
		CurrentSituations.add("FarFromEnemyGoal");
		CurrentSituations.add("FightForBall");
		SituationManager situationManager = mock(SituationManager.class);
		when(situationManager.getListOfCurrentSituations()).thenReturn(CurrentSituations);
		
		Selector selector = new Selector ();
		selector.setSituationManager(situationManager);		
				
		//set mock in Selector.
		selectorController.setSelector(selector);
	}
	
	
	/** Testing controlStrategy. */
	@Test
	public void controlStrategyTest() throws Exception {
		selectorController.controlStrategy();
		Assert.assertEquals(true,selectorController.getSelector().getSelectedStrategy()!=null);
	}
	
	/** Testing controlTactics. */
	@Test
	public void controlTacticsTest() throws Exception {
		selectorController.controlTactics();
		Assert.assertEquals(true,selectorController.getSelector().getSelectedTactic()!=null);
	}
	
}
