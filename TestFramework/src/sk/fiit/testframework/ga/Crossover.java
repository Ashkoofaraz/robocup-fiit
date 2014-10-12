package sk.fiit.testframework.ga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import sk.fiit.jim.code_review.UnderConstruction;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.agenttrainer.models.AgentMovePhase;

public class Crossover {

	//private int firstPointCrossover;

	public void onePointCrossover(AgentMove firstMove, AgentMove secondMove){
		int numberOfPhase = firstMove.getPhases().size();
		int firstPointCrossover = randomNumber(1,numberOfPhase);
		 
		ArrayList<AgentMovePhase> movePhase1 = new ArrayList<AgentMovePhase>();	
		ArrayList<AgentMovePhase> movePhase2 = new ArrayList<AgentMovePhase>();
		
		for(int i=0; i<firstPointCrossover; i++){
			movePhase1.add(secondMove.getPhases().get(i));
			movePhase2.add(firstMove.getPhases().get(i));
		}
		
		for(int i=firstPointCrossover;i<numberOfPhase;i++){
			movePhase1.add(secondMove.getPhases().get(i));
			movePhase2.add(firstMove.getPhases().get(i));
		}
		firstMove.setPhases(movePhase1);
		secondMove.setPhases(movePhase2);
		
	}
	
	public void twoPointCrossover(AgentMove firstMove, AgentMove secondMove){
		int numberOfPhase = firstMove.getPhases().size();
//		int point = randomNumber(1, numberOfPhase-1);
		 
		for(int n=0; n<2; n++){
			int crossoverPoint = randomNumber(1, numberOfPhase-1);
			
			ArrayList<AgentMovePhase> movePhase1 = new ArrayList<AgentMovePhase>();	
			ArrayList<AgentMovePhase> movePhase2 = new ArrayList<AgentMovePhase>();
			
			for(int i=0; i<crossoverPoint; i++){
				movePhase1.add(secondMove.getPhases().get(i));
				movePhase2.add(firstMove.getPhases().get(i));
			}
			
			for(int i=crossoverPoint;i<numberOfPhase;i++){
				movePhase1.add(secondMove.getPhases().get(i));
				movePhase2.add(firstMove.getPhases().get(i));
			}
			firstMove.setPhases(movePhase1);
			secondMove.setPhases(movePhase2);			
		}
			
	}
	
	public void morePointCrossover(AgentMove firstMove, AgentMove secondMove, int numberOfCrossover){
		int numberOfPhase = firstMove.getPhases().size();
		Set<Integer> crossPoints = new HashSet<Integer>();
		while(crossPoints.size()<numberOfCrossover){
			int point = randomNumber(1, numberOfPhase);
			crossPoints.add(point);
		}
		for (Iterator<Integer> iterator = crossPoints.iterator(); iterator.hasNext();) {
			Integer crossoverPoint = (Integer) iterator.next();
			
		
			 //crossoverPoint = randomNumber(1, numberOfPhase);
			ArrayList<AgentMovePhase> movePhase1 = new ArrayList<AgentMovePhase>();	
			ArrayList<AgentMovePhase> movePhase2 = new ArrayList<AgentMovePhase>();
			
			for(int i=0; i<crossoverPoint; i++){
				movePhase1.add(secondMove.getPhases().get(i));
				movePhase2.add(firstMove.getPhases().get(i));
			}
			
			for(int i=crossoverPoint;i<numberOfPhase;i++){
				movePhase1.add(secondMove.getPhases().get(i));
				movePhase2.add(firstMove.getPhases().get(i));
			}
			firstMove.setPhases(movePhase1);
			secondMove.setPhases(movePhase2);
			
		}
			
	}
	
	private int randomNumber(int from , int to){
		Random rndGen = new Random();		
		return rndGen.nextInt(to) + from;
	}
	
}

