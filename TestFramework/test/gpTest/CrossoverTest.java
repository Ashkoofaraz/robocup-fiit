package gpTest;

import java.util.LinkedList;

import org.junit.Test;

import sk.fiit.testframework.gp.Crossover;
import sk.fiit.testframework.gp.GeneticProgramming;
import sk.fiit.testframework.gp.Representation;

public class CrossoverTest extends AbstractTest{
	Crossover cross = new Crossover();
	GeneticProgramming gp = GeneticProgramming.getInstance();
	@Test
	public void test()  {
		start();
		
		LinkedList<String> eq3 = new LinkedList<>();
		eq3.addAll(gp.getPostfixList().get(1).getEquatioin());
		gp.addNewEquation(eq3,0.0);
		
		LinkedList<String> eq2 = new LinkedList<>();
		eq2.addAll(gp.getPostfixList().get(0).getEquatioin());
		gp.addNewEquation(eq2,0.0);
		
		System.out.println("+++++++++++");
		for(int i=0;i<gp.getPostfixList().size();i++){
			System.out.println(gp.getPostfixList().get(i).getEquatioin());
		}
		System.out.println("+++++++++++");
		for(int i=0;i<gp.getNewPostFixRepresentation().size();i++){
			System.out.println(gp.getNewPostFixRepresentation().get(i).getEquatioin());
		}
		System.out.println("+++++++++++");
		gp.crossover(gp.getNewPostFixRepresentation().get(0), gp.getNewPostFixRepresentation().get(1));
		System.out.println("+++++++++++");
		for(int i=0;i<gp.getPostfixList().size();i++){
			System.out.println(gp.getPostfixList().get(i).getEquatioin());
			
		}
		System.out.println("+++++++++++");
		for(int i=0;i<gp.getNewPostFixRepresentation().size();i++){
			System.out.println(gp.getNewPostFixRepresentation().get(i).getEquatioin());
		}
		System.out.println("+++++++++++");
		for(int i=0;i<gp.getNewPostFixRepresentation().size();i++){
			gp.setXValue(0.0+i);
			
			System.out.println(gp.evaluate(gp.changeXtovalue(gp.getNewPostFixRepresentation().get(i))));
		}
	}

}
