package sk.fiit.testframework.gp;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;



public class Selection {
	
	private static Logger logger = Logger.getLogger(Selection.class.getName());
	
	
	public LinkedList<Representation> turnament(LinkedList<Representation> eqs, LinkedList<Representation> newEqs, int sizePopulation){
		int sizeofPopulation = sizePopulation;
		clearNullFitness(newEqs);
		Representation bestEqInOld = bestEquitation(eqs);
		LinkedList<Representation> outPopulation = new LinkedList<>();
		if(bestEqInOld.getFitness() != null && bestEqInOld.getFitness()>0.0){
			outPopulation.add(bestEqInOld);
		}
		for(Representation rep: newEqs){
			outPopulation.add(rep);
		}
		try{
			int act=0;	
			while(outPopulation.size()>sizeofPopulation){
				for(int i=act; i<outPopulation.size();i=i+2){
					if(i+1<outPopulation.size()){
						if(outPopulation.get(i).getFitness()>outPopulation.get(i+1).getFitness()){
							outPopulation.get(i).setFitness(null);
							act=i+1;
							break;
						}else{
							outPopulation.get(i+1).setFitness(null);
							act=i+1;
							break;
						}
					}
				}
				if(act>=outPopulation.size()-1){
					act=0;
				}
				clearNullFitness(outPopulation);
			}
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
		logger.info("out size of population " + outPopulation.size());
		return outPopulation;
	}
	
	public LinkedList<Representation> rulete(LinkedList<Representation> postFix,
			LinkedList<Representation> newPostFix, int sizePopulation) {
		int sizeofPopulation = sizePopulation;
		Representation bestEqInOld = bestEquitation(postFix);
		newPostFix.add(bestEqInOld);
		double totalFitness = 0.0;
		double lastEndPoint = 0.0;
		int eqPosition = 0;
		clearNullFitness(newPostFix);
		for(Representation eq : newPostFix){
			totalFitness += eq.getFitness();
		}
		LinkedList<RulletePosition> eqRullete = new LinkedList<RulletePosition>();
		for(Representation eq:newPostFix){
			RulletePosition rulete = new RulletePosition();
			rulete.eq = eq;
			rulete.startPosition = lastEndPoint;
			lastEndPoint =rulete.startPosition+(100*(eq.getFitness()/totalFitness));
			rulete.endPosition = lastEndPoint;
			rulete.position = eqPosition;
			eqRullete.add(eqPosition, rulete);
			eqPosition++;
			
		}
		LinkedList<Representation> outPopulation = new LinkedList<>();
		while(sizeofPopulation > outPopulation.size()){
			int who = randomGenerator(eqPosition, 0);			
			outPopulation.add(eqRullete.get(who).eq);
		}
		return outPopulation;
	}
	
	private int randomGenerator(int max, int min){
		Random rndGen = new Random();
		
		return rndGen.nextInt(max)+min;//ak od jednotky +1;//
	}
	
	private void clearNullFitness(LinkedList<Representation> newEqs) {
		for(int i=0; i<newEqs.size();i++){
			if(newEqs.get(i).getFitness()==null){
				newEqs.remove(i);
				i=i-1;
			}
		}
		
	}
	
	private Representation bestEquitation(LinkedList<Representation> eqs){
		double topFitnes = 100.0;
		Representation topEq = eqs.get(0);
		for(Representation eq : eqs){
			if(eq.getFitness() != null){
				if(eq.getFitness()<topFitnes && eq.getFitness()>0.0){
					topEq = eq;
					topFitnes = eq.getFitness();
				}
			}
		}
		return topEq;
	}
	
	static class RulletePosition{
		double startPosition;
		double endPosition;
		Representation eq;
		int position;
	}
}
