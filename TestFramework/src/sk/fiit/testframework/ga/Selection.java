package sk.fiit.testframework.ga;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import sk.fiit.testframework.agenttrainer.models.AgentMove;

public class Selection {
	/**
	 * prva selekcia podla navrhu 
	 * @param moves
	 * @param newMoves
	 * @return
	 */
	public ArrayList<AgentMove> doSelection(ArrayList<AgentMove> moves, ArrayList<AgentMove> newMoves){
		double top = findTopFitness(newMoves);
		double worst = findWorstFitness(moves);
		int count=0;
		for (int i=0;i<newMoves.size();i++){
			//vstupuju do moves
			if(newMoves.get(i).getFitness()<worst){
				count++;
				moves.add(newMoves.get(i));
			}
		}
		worst=findWorstFitness(moves);
		for(int i=1;i<moves.size();i++){
			if(count>=0){
				if(moves.get(i).getFitness() == worst){
					moves.remove(i);
					count--;
					i=0;
					worst = findWorstFitness(moves);
				}
			}
			
			
		}
		
		return moves;
	}
	/**
	 * nefunguje - mala by byt ruleta
	 * @param moves
	 * @param newMoves
	 * @return
	 */
	public ArrayList<AgentMove>doSelectionC(ArrayList<AgentMove> moves, ArrayList<AgentMove> newMoves){
		double sumFitnes = 0;
		double percento = 0;
		int numberMove = 0;
		ArrayList<AgentMove> fitness = new ArrayList<AgentMove>();
		for(int i=1;i<moves.size();i++){
			sumFitnes+=moves.get(i).getFitness();
			fitness.add(moves.get(i)); 
		}
		for(int i=0;i<newMoves.size();i++){
			sumFitnes+=newMoves.get(i).getFitness();
			fitness.add(newMoves.get(i));
		}
		numberMove=moves.size()+newMoves.size()-1;
		percento = 100/sumFitnes;
		for(int i=0;i<fitness.size();i++){
			 
					 fitness.get(i).setFitness(fitness.get(i).getFitness()*percento);
			//fitness.set(i, e);
		}
		//do sort
		Collections.sort(fitness, new Compare());		
		//newMoves.clear();
		AgentMove firstMove=moves.get(0);
		moves.clear();
		
		moves.add(firstMove);
		while(moves.size()<(numberMove-newMoves.size()-1)){
			for (int i=fitness.size()-1;i>=0;i--){
				if(fitness.get(i).getFitness()>0.0){
					for(int j=0;j<newMoves.size();j++){
						if(fitness.get(i) == newMoves.get(j)){
							moves.add(moves.get(j));
						}
					}
				}
			}
		}
		for (AgentMove lteam : fitness) {
	        System.out.println(lteam.getFitness() + " points");
	    }
		return newMoves;
		
	}
	/**
	 * vyber pohybov na zaklade turnaja 
	 * @param moves
	 * @param newMoves
	 * @return moves
	 */
	//TODO: update for use elite(only one) move from parent, and make turnament with new population and elite move
	public ArrayList<AgentMove>doSelectionB(ArrayList<AgentMove> moves, ArrayList<AgentMove> newMoves){
		
		ArrayList<AgentMove> move = new ArrayList<AgentMove>();
		/*for (AgentMove agentMove : moves) {
			if(agentMove.getFitness()!= null)
				if(agentMove.getFitness()!=0)
					move.add(agentMove);
		}*/
		
		for(AgentMove agentMove : newMoves){
			if(agentMove.getFitness()!=0)
				move.add(agentMove);
		}
		
		while(move.size() >= moves.size()){
			for(int i=1;i<move.size();i=i+2){
				if(i+1<move.size()){
					if(move.get(i).getFitness()<move.get(i+1).getFitness()){
						move.get(i+1).setFitness(null);
					}
					else{
						move.get(i).setFitness(null);
					}
				}
			}
			int size=move.size();
			int i=1;
			int count=1;
			while(count<size){
				if(move.get(i).getFitness()==null){
					move.remove(i);
					
				}
				else{
					i++;
					}
				count++;
				
			}			
			/*for (Iterator iterator = move.iterator(); iterator.hasNext();) {
				AgentMove agentMove = (AgentMove) iterator.next();
				if(agentMove.getFitness()==null){
					move.remove(agentMove);
				}
			}*/	
			
		}
		AgentMove inMove=moves.get(0);
		moves.clear();
		moves.add(inMove);
		for(AgentMove m : move )
			moves.add(m);
			//moves = move;
		return moves;
	}
	public ArrayList<AgentMove> turnament(ArrayList<AgentMove> moves, ArrayList<AgentMove> newMoves, int sizePopulation){
		int sizeofPopulation = sizePopulation;
		clearNullFitness(newMoves);
		AgentMove bestMovInOld = bestMove(moves);
		ArrayList<AgentMove> outPopulation = new ArrayList<>();
		if(bestMovInOld.getFitness() != null && bestMovInOld.getFitness() > 0.0)
			outPopulation.add(bestMovInOld);
		for(AgentMove move: newMoves){ 
			outPopulation.add(move);
		}
		try{
			int act=0;			
			while(outPopulation.size()>sizeofPopulation){ 
				for(int i=act; i<outPopulation.size();i=i+2){
					if(i+1<outPopulation.size()){
						
						if(outPopulation.get(i).getFitness()<outPopulation.get(i+1).getFitness()){
							if(outPopulation.get(i).getName().contains("kick")){
								outPopulation.get(i).setFitness(null);
							}else{
								outPopulation.get(i+1).setFitness(null);
							}
							act=i+1;
							break;
						}else{
							if(outPopulation.get(i).getName().contains("kick")){
								outPopulation.get(i+1).setFitness(null);
							}else{
								outPopulation.get(i).setFitness(null);
							}
							act=i+1;
							break;
						} 
					}
//					act = 0;
					
				}
				if(act>=outPopulation.size()-1){
					act=0;
//					clearNullFitnes(outPopulation);
				}
				clearNullFitness(outPopulation);
			
		}
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
		
		return outPopulation;
	}
	
	public ArrayList<AgentMove> rulete(ArrayList<AgentMove> oldPopulation,
			ArrayList<AgentMove> newPopulation, int sizePopulation) {
		int sizeofPopulation = sizePopulation;
		AgentMove bestMovInOld = bestMove(oldPopulation);
		newPopulation.add(bestMovInOld);
		double totalFitness = 0.0;
		double lastEndPoint = 0.0;
		int movePosition = 0;
		clearNullFitness(newPopulation);
		for(AgentMove move : newPopulation){
			totalFitness += move.getFitness();
		}
		ArrayList<RulletePosition> moveRullete = new ArrayList<RulletePosition>();
		for(AgentMove move:newPopulation){
			RulletePosition rulete = new RulletePosition();
			rulete.move = move;
			rulete.startPosition = lastEndPoint;
			lastEndPoint =rulete.startPosition+(100*(move.getFitness()/totalFitness));
			rulete.endPosition = lastEndPoint;
			rulete.position = movePosition;
			moveRullete.add(movePosition, rulete);
			movePosition++;
			
		}
		ArrayList<AgentMove> outPopulation = new ArrayList<>();
		while(sizeofPopulation > outPopulation.size()){
			int who = randomGenerator(movePosition, 0);			
			outPopulation.add(moveRullete.get(who).move);
		}
		return outPopulation;
	}
	
	private int randomGenerator(int max, int min){
		Random rndGen = new Random();
		
		return rndGen.nextInt(max)+min;//ak od jednotky +1;//
	}
	
	private void clearNullFitness(ArrayList<AgentMove> newMoves) {
		for(int i=0; i<newMoves.size();i++){
			if(newMoves.get(i).getFitness()==null){
				newMoves.remove(i);
				i=i-1;
			}
		}
		
	}
	private double findTopFitness(ArrayList<AgentMove> moves){
		double top = 1000;
		for (int i = 0; i < moves.size(); i++) {
			if((moves.get(i).getFitness()<top) && (moves.get(i).getFitness() != 0)){
				top = moves.get(i).getFitness();
			}
		}
		return top;
		
	}
	
	private double findWorstFitness(ArrayList<AgentMove> moves){
		double worst = 0;
		for (int i = 1; i < moves.size(); i++) {
			if(moves.get(i).getFitness()>worst){
				worst = moves.get(i).getFitness();
			}
		}
		return worst;
	}
	private AgentMove bestMove(ArrayList<AgentMove> moves){
		double topFitnes = 100.0;
		AgentMove topMove = moves.get(0);
		for(AgentMove move : moves){
			if(move.getFitness() != null){
				if(move.getFitness()<topFitnes && move.getFitness()>0.0){
					topMove = move;
					topFitnes = move.getFitness();
				}
			}
		}
		return topMove;
	}
	
	static class RulletePosition{
		double startPosition;
		double endPosition;
		AgentMove move;
		int position;
	}
	
}
