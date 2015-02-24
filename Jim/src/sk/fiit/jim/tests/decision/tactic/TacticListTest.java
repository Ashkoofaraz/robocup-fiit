package sk.fiit.jim.tests.decision.tactic;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.fiit.jim.decision.tactic.*;

public class TacticListTest {

	public boolean searchInList(String stringToSearch, ArrayList<Tactic> ListWhereSearch) {
		for (Tactic tactic : ListWhereSearch) {
			if (tactic.getClass().getName().contains(stringToSearch)) {
				return true;
			}
		}
		return false;
	}
	
	@BeforeClass
	public static void initTest(){
		TacticList.initializeAllTactics();
	}

	@Test
	public void test() {
		// Testing Method
		ArrayList<Tactic> tactic = TacticList.getTactics();

		Assert.assertEquals(true, searchInList("Goalie", tactic));
		Assert.assertEquals(false, searchInList("Nieco", tactic));
	}

}
