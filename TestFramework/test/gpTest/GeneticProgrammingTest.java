package gpTest;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import sk.fiit.testframework.gp.Crossover;
import sk.fiit.testframework.gp.GeneticProgramming;
import sk.fiit.testframework.gp.InfixPostfixEvaluator;
import sk.fiit.testframework.gp.Representation;

public class GeneticProgrammingTest {
//	@Test
	public void run(){
		for (int i=0;i<20;i++){
			test();
		}
	}
	
	@Test
	public void test() {
		GeneticProgramming gp = GeneticProgramming.getInstance();
		InfixPostfixEvaluator ev = new InfixPostfixEvaluator();
		Crossover cr = new Crossover();
//		gp.addNewEquation(gp.generateEquation(3).getEquatioin());
//		gp.addNewEquation(gp.generateEquation(4).getEquatioin());
//		cr.onePointCrossover(eq, eq2);
//		System.out.println("############ Start ############");
//		System.out.println((gp.getInfixList().get(0)));
//		System.out.println((gp.getInfixList().get(1)));
//		System.out.println(gp.getPostfixList().get(0).getEquatioin());
//		System.out.println(gp.getPostfixList().get(1).getEquatioin());
//		System.out.println("after cross");
//		cr.onePointCrossover(gp.getPostfixList().get(0), gp.getPostfixList().get(1));
//		System.out.println(gp.getPostfixList().get(0).getEquatioin());
//		System.out.println(gp.getPostfixList().get(1).getEquatioin());
//		gp.setXValue(35.0);
//		gp.changeXtovalue(gp.getPostfixList().get(0));
//		gp.setXValue(70.0);
//		gp.changeXtovalue(gp.getPostfixList().get(1));
//		System.out.println(gp.getPostfixList().get(0).getEquatioin());
//		System.out.println(gp.getPostfixList().get(1).getEquatioin());
//		System.out.println(ev.evaluatePostfix(gp.getPostfixList().get(0).getEquatioin()));
//		System.out.println(ev.evaluatePostfix(gp.getPostfixList().get(1).getEquatioin()));
//		System.out.println(ev.evaluatePostfix(gp.getPostfixList().get(1).getEquatioin())%2);
//		eq = "(2*sin(3-1))";
//		System.out.println(ev.convert2Postfix(eq));
//		System.out.println(ev.evalInfix(eq));
		System.out.println("############  End  ############");
		Representation rep = new Representation();
		LinkedList<String> eq = new LinkedList<>();
		eq.add("(");
		eq.add("2");
		eq.add("*");
		eq.add("5");
		eq.add("+");
		eq.add("sin");
		eq.add("(");
		eq.add("9");
		eq.add("+");
		eq.add("3");
		eq.add("+");
		eq.add(")");
		eq.add(")");
		rep.setEquatioin(eq);
		ev.convert2Postfix(rep);
		System.out.println(rep.getEquatioin());
		System.out.println(ev.convert2Postfix(rep));
		System.out.println(ev.evalInfix(rep));
		
	}

}
