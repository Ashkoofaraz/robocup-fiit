package sk.fiit.jim.tests.decision.situation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.fiit.jim.decision.situation.*;

/**
 * @author 	Vladimir Bosiak
 * @team 	Team 4 2013/2014
 */
public class SituationListTest {

	@BeforeClass
	public static void initTest(){
		SituationList.initializeAllSituations();
	}
	
	/**
	 * Test for list & test of exceptions in checkSituation methods
	 */
	@Test
	public void test() {
		// Testing Method
		ArrayList<Situation> situations = SituationList.getSituations();
		for(Situation situation : situations){
		    assertTrue(situation instanceof Situation);
		    boolean x = situation.checkSituation();
		    assertTrue((x == true) || (x==false));
		}
		boolean f = true;
		
	}
	@Test
	public void test2() {
		ArrayList<Situation> situations = SituationList.getSituations();
		boolean f = true;
		for(Situation situation : situations){
		    
		    if (situation.getClass().getName().contains("EnemyIn1L")){
		    	f = false;
		    }
		   
		}
		assertTrue(f==false);
		
	}

}
