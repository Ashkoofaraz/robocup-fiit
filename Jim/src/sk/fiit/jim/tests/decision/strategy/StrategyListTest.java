package sk.fiit.jim.tests.decision.strategy;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.decision.strategy.Strategy;
import sk.fiit.jim.decision.strategy.StrategyList;

/* michal petras 
 * RFC megatroll
 * 
 * Test for load Strategy List - in this time only one - offensivestrategy
 * Strategyfactory.createstrategy 
 */

public class StrategyListTest {
	
	//metod to search string in list of IStrategy
	public boolean searchInList(String stringToSearch,ArrayList<Strategy> ListWhereSearch){
		  for (Strategy strategy : ListWhereSearch) {
			  if (strategy.getClass().getName().contains(stringToSearch)){
				  return true;
			  }
		  }
		  return false;
		
		
	}
	
	@Test
	public void test() {
		
		//Testing Method
        StrategyList list = new StrategyList();
        list.initializeAllStrategies();
		ArrayList<Strategy> strategy =  list.getStrategies();
		
		//test if list contain whot we need.
		Assert.assertEquals(true,searchInList("OffensiveStrategy",strategy));
		Assert.assertEquals(false,searchInList("nejakaIna",strategy));
	}

}
