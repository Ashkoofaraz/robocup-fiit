package gpTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.ga.SelectionType;
import sk.fiit.testframework.gp.Representation;
import sk.fiit.testframework.gp.Selection;

public class SelectionTest extends AbstractTest{

	public void init(){
		if (gp.getPostfixList().size()==0)
			start();
		
	}
	@Test
	public void testTurnament() throws Exception {
			start();			
			
			LinkedList<String> eq2 = new LinkedList<>();
			eq2.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
			gp.addToNewPostFixRepresentation(eq2,1.2);
//			gp.getNewPostFixRepresentation().get(3).setFitness(1.2);
			
			LinkedList<String> eq3 = new LinkedList<>();
			eq3.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
			gp.addToNewPostFixRepresentation(eq3,null);
//			gp.getNewPostFixRepresentation().get(4).setFitness(null);

			gp.doSelectioin(SelectionType.Tournament,3);
			if(gp.getNewPostFixRepresentation().size()%2==0){
				Assert.assertEquals(3, gp.getPostfixList().size());
			} else{
				Assert.assertEquals(4, gp.getPostfixList().size());
			}
			
	}
	@Test
	public void testRullete() throws Exception{
		start();
		
		LinkedList<String> eq2 = new LinkedList<>();
		eq2.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
		gp.addToNewPostFixRepresentation(eq2,1.2);
		gp.getNewPostFixRepresentation().get(3).setFitness(1.2);
		
		LinkedList<String> eq3 = new LinkedList<>();
		eq3.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
		gp.addToNewPostFixRepresentation(eq3,null);
		gp.getNewPostFixRepresentation().get(4).setFitness(null);
		
		gp.doSelectioin(SelectionType.Rulette,3);
		Assert.assertEquals(3, gp.getPostfixList().size());		
		
	}
}
