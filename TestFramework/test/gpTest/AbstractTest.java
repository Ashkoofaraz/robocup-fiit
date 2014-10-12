package gpTest;

import java.util.Arrays;
import java.util.LinkedList;

import sk.fiit.testframework.gp.GeneticProgramming;

public class AbstractTest {

	GeneticProgramming gp = GeneticProgramming.getInstance();
	
	protected void start() {
		gp.getNewPostFixRepresentation().clear();
		gp.getPostfixList().clear();
		LinkedList<String> eq = new LinkedList<>();
		eq.addAll(Arrays.asList("(","X","*","5","+","sin","(","9","+","3",")",")"));				
		gp.addNewEquation(eq,1.0);
		gp.getPostfixList().get(0).setFitness(1.0);
		gp.getNewPostFixRepresentation().get(0).setFitness(1.0);
		
		
		LinkedList<String> eq2 = new LinkedList<>();
		eq2.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
		gp.addNewEquation(eq2,2.0);
		gp.getPostfixList().get(1).setFitness(2.0);
		gp.getNewPostFixRepresentation().get(1).setFitness(2.0);
		
		LinkedList<String> eq3 = new LinkedList<>();
		eq3.addAll(Arrays.asList("(","2","+","5","*","sin","(","9","*","3",")",")"));
		gp.addNewEquation(eq3,null);
		gp.getPostfixList().get(2).setFitness(null);
		gp.getNewPostFixRepresentation().get(2).setFitness(null);
		
//		LinkedList<String> eq3 = new LinkedList<>();
//		eq3.addAll(Arrays.asList("(","2","*","5","+","sin","(","9","+","3",")",")","+","4"));
//		gp.addNewEquation(eq3);
//		
//		LinkedList<String> eq4 = new LinkedList<>();
//		eq4.addAll(Arrays.asList("(","2","*","5","+","sin","(","9","+","3",")",")","+","4"));
//		gp.addNewEquation(eq4);
		
	}
	
}
