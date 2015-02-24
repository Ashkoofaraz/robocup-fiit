package sk.fiit.jim.tests.decision;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import sk.fiit.jim.decision.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sk.fiit.jim.decision.situation.SituationManager;

/**
 * Class for test a selectorTest class. 
 * 
 * @author 	Samuel Benkovic <sppred@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */

public class SelectorTest {

	private Selector selector = new Selector();
	
	
	@Before
	/** 
	 * mock for best Strategy and Tactic Test 
	 */
	public void mockforBestStrategy() throws Exception{
		
		/// Create Mock For situationManager and method getListOfCurrentSituations
		List<String> CurrentSituations =  new ArrayList<String>();
		CurrentSituations.add("FarFromEnemyGoal");
		CurrentSituations.add("FightForBall");
		SituationManager situationManager = mock(SituationManager.class);
		when(situationManager.getListOfCurrentSituations()).thenReturn(CurrentSituations);
		
		
				
		//set mock in Selector.
		selector.setSituationManager(situationManager);
	}
	
	/////////////////
	// TESTING SELECTORS
	///////////////// 
	
	/** Testing selectStrategy. */
	@Test
	public void getBestStrategyForSituationTest() throws Exception {
		Assert.assertEquals(true,selector.selectStrategy());
	}
	
	/** Testing selectTactic.*/
	@Test
	public void getBestTacticForSituationsTest() throws Exception {
		
		Assert.assertEquals(true,selector.selectTactic());
	}
	
	/////////////////
	// TESTING GETERS
	/////////////////
	
	/** Testing getSelectedStrategy.*/
	@Test
	public void getSelectedStrategyTest() throws Exception {
		Assert.assertEquals(false,selector.getSelectedStrategy()!=null);
		selector.selectStrategy();
		Assert.assertEquals(true,selector.getSelectedStrategy()!=null);
	}
	
	/** Testing getSelectedTactic.*/
	@Test
	public void getSelectedTacticTest() throws Exception {
		Assert.assertEquals(false,selector.getSelectedTactic()!=null);
		selector.selectTactic();
		Assert.assertEquals(true,selector.getSelectedTactic()!=null);
	}
	
	/** Testing getSituationManager.*/
	@Test
	public void getSituationManagerTest() throws Exception {
		Assert.assertEquals(true,selector.getSituationManager()!=null);
	}
	
	/////////////////
	// TESTING SETERS
	/////////////////
	
	/** Testing setSelectedStrategy.*/
			@Test
			public void setSelectedStrategy() throws Exception {
				selector.selectStrategy();
				selector.setSelectedStrategy(null);
				Assert.assertEquals(true,selector.getSelectedStrategy()== null);
			}
			
	/** Testing setSelectedTactic.*/
		@Test
		public void setSelectedTactic() throws Exception {
			selector.selectTactic();
			selector.setSelectedTactic(null);
			Assert.assertEquals(true,selector.getSelectedTactic()== null);
		}
	
}
