package sk.fiit.testframework.gp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

import sk.fiit.testframework.agenttrainer.models.AgentMovePhase;

public class Crossover {

	private GeneticProgramming gp = GeneticProgramming.getInstance();
	
	private static Logger logger = Logger.getLogger(Crossover.class.getName());
	
	/**
	 * Only with postfix representation!!!!!!!!!!!!!!
	 * 
	 * @param postFixFirst
	 * @param postFixSecond
	 */
	public void onePointCrossover(Representation postFixFirst, Representation postFixSecond){
		
		int crossStartPointFirst = 0;
		int crossEndPointFirst = 0;
		
		int crossStartPointSecond = 0;
		int crossEndPointSecond = 0;
		
		LinkedList<String> firstR = new LinkedList<String>();	
		LinkedList<String> secondR = new LinkedList<String>();
		
		
		if(!isPointOperator(postFixFirst.getEquatioin().get(crossEndPointFirst))){
			crossEndPointFirst = updateCrossPoint(postFixFirst);
			crossStartPointFirst = crossEndPointFirst - startPoint(postFixFirst.getEquatioin(),crossEndPointFirst);
			if(crossStartPointFirst<0) crossStartPointFirst=0;
			
		}else{
//			crossPointFirst++;
		}
		
		if(!isPointOperator(postFixSecond.getEquatioin().get(crossEndPointSecond))){
			crossEndPointSecond = updateCrossPoint(postFixSecond);
			crossStartPointSecond = crossEndPointSecond - startPoint(postFixSecond.getEquatioin(), crossEndPointSecond);
			if(crossStartPointSecond<0) crossStartPointSecond=0;
		}else{
//			crossPointSecond++;
			}
		
		logger.info(""+crossStartPointFirst);
		logger.info(""+crossEndPointFirst);
		logger.info(""+crossStartPointSecond);
		logger.info(""+crossEndPointSecond);
		try{
		for(int i=0; i<crossStartPointFirst; i++){
			firstR.add(postFixFirst.getEquatioin().get(i));
		}
		for(int i=crossStartPointSecond; i<=crossEndPointSecond; i++){
			firstR.add(postFixSecond.getEquatioin().get(i));
		}
		for(int i=crossEndPointFirst+1;i<postFixFirst.getEquatioin().size();i++){
			firstR.add(postFixFirst.getEquatioin().get(i));
		}
		
		for(int i=0; i<crossStartPointSecond; i++){
			secondR.add(postFixSecond.getEquatioin().get(i));
		}
		for(int i=crossStartPointFirst; i<=crossEndPointFirst; i++){
			secondR.add(postFixFirst.getEquatioin().get(i));
		}
		for(int i=crossEndPointSecond+1; i<postFixSecond.getEquatioin().size(); i++){
			secondR.add(postFixSecond.getEquatioin().get(i));
		}
		}catch(Exception ex){
			logger.info("cross err"+ex.getStackTrace());
		}
		changeVal(firstR);
		changeVal(secondR);
		
		postFixFirst.getEquatioin().clear();
		postFixFirst.getEquatioin().addAll(firstR);
		postFixSecond.getEquatioin().clear();
		postFixSecond.getEquatioin().addAll(secondR);

	}
	
	private void changeVal(LinkedList<String> eq) {
		
		if((Operators.isOperator(eq.get(0)))){
			String val = null;
			val=eq.get(0);
			eq.remove(0);
			eq.add(val);
			}
		if((Operators.isOperator(eq.get(1)))){
			String val = null;
			val=eq.get(1);
			eq.remove(1);
			eq.add(val);
//			for(int i=eq.size();i>=0;i--){
//				if(!Operators.isOperator(eq.get(i))){
//					val=eq.get(i);
//				}
//				break;
//			}
//			eq.add(1, val);
		}
		
	}

	private int startPoint(LinkedList<String> postFix, int crossEndPoint) {
		int countOperators=0;
		for(int i=crossEndPoint;i>=0;i--){
			if(isPointOperator(postFix.get(i))){
				if(postFix.get(i).equals("sin")){
					countOperators++;
				}else{
					countOperators +=2;
				}
			}
		}
		return countOperators;
	}

	private int updateCrossPoint(Representation postFixFirst) {
//		for(int i=0; i<postFixFirst.size();i++){
		int lenghtEquation = postFixFirst.getEquatioin().size()-1;
		if(lenghtEquation<1){
			lenghtEquation=1;
		}
		int i = gp.randomGenerator(1, lenghtEquation);
			while(!isPointOperator(postFixFirst.getEquatioin().get(i ))){				
				i = gp.randomGenerator(1, lenghtEquation);
				
			}
//		}
			return i;
		
	}

	private boolean isPointOperator(String value){
		for (Operators operator : Operators.getListOperators()){
			if(value.equalsIgnoreCase(operator.getOperator())){
				return true;
			}			
		}
		return false;
	}
	
}
