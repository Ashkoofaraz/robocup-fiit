package gaTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import sk.fiit.testframework.agenttrainer.AgentMoveReader;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.ga.Selection;

public class selectionTest extends AbstractTest{
	
	
	@Test
	public void testTurnament() throws Exception {
			start();
			
			
			Selection sel = new Selection();
			ArrayList<AgentMove> m3 = sel.turnament(oldPop, newPop,2);
			if(newPop.size()%2==0){
				Assert.assertEquals(oldPop.size(), m3.size());
			} else{
				Assert.assertEquals(oldPop.size(), m3.size());
			}
			
	}
	@Test
	public void testRullete() throws Exception{
		start();
		Selection sel = new Selection();
		
		ArrayList<AgentMove> m3 = sel.rulete(oldPop, newPop,2);
		Assert.assertEquals(2, m3.size());
		
	}

}
