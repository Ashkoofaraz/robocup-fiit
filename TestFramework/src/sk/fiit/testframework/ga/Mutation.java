package sk.fiit.testframework.ga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.agenttrainer.models.AgentMoveEffector;
import sk.fiit.testframework.agenttrainer.models.AgentMovePhase;
import sk.fiit.testframework.ga.parameters.ParameterGA;

public class Mutation {
	private ArrayList<AgentMovePhase> phase;
	private ArrayList<AgentMoveEffector> effectors;
	
	
	//pocet klbov pre mutovanie
	private int numberOfMutationInPhase;
	//pocet faz pre mutovanie
	private int numberOfPhaseInMutation;	
	/**
	 * mutuje nahodne fazy a v nich nahodne klby
	 * vo faze zmutuje aj duration fazy
	 * @param agentMove
	 * @param paramGA
	 * @return
	 */
	public AgentMove mutate(AgentMove agentMove, ParameterGA paramGA){
		AgentMove pomMove = agentMove;
		phase = pomMove.getPhases();
		int numberPhases = phase.size();
		numberOfPhaseInMutation = randomGenerator(numberPhases,0);
		Set<Integer> recordP = new HashSet<Integer>(numberOfPhaseInMutation);
		int pom = numberOfPhaseInMutation;
		while(numberOfPhaseInMutation>=0){
			recordP.add(randomGenerator(numberPhases,0));
			numberOfPhaseInMutation--;
		}
		numberOfPhaseInMutation=pom;
		for (int cisla : recordP){
			//System.out.println("faza>>> "+cisla);
			//efektory vo faze cisla
			effectors = phase.get(cisla).getEffectors();
			phase.get(cisla).setDuration(calculate(phase.get(cisla).getDuration(),paramGA.getParameterMutation().getRangeMutation()));
			int numberEffector = effectors.size();
			//zoznam efektorov na mutovanie
			numberOfMutationInPhase = randomGenerator(numberEffector,0);
			Set<Integer> recordE = new HashSet<Integer>(numberOfMutationInPhase);
			pom = numberOfMutationInPhase;
			while(numberOfMutationInPhase>0){
				recordE.add(randomGenerator(numberEffector,0));
				numberOfMutationInPhase--;
			}
			numberOfMutationInPhase=pom;
			for(int zoznamEfektorov : recordE){
				//System.out.println("efektor>>> "+zoznamEfektorov);
				effectors.get(zoznamEfektorov).setEnd((int)calculate(effectors.get(zoznamEfektorov).getEnd(),effectors.get(zoznamEfektorov).getName(),paramGA.getParameterMutation().getRangeMutation()));
				//System.out.println(effectors.get(zoznamEfektorov).getName() + " " + effectors.get(zoznamEfektorov).getEnd());
								
			}
		}
		
		return pomMove;		
	}
	/**
	 * nepouzitelne zatial
	 * @param agentMove
	 * @return
	 */
	public AgentMove mutateB(AgentMove agentMove){
		
		
		return null;
	}
	/**
	 * 
	 * @param maxMutation - maximalny pocet mutovanych klbov vo faze
	 */
	public void setMaxMutation(int maxMutation){
		this.numberOfMutationInPhase = maxMutation;
	}
		
	private double calculate (int angle, String name, int range){
		double ang=angle;
		int change = randomGenerator(2*range, (range*(-1)));
		double percento = change/100.0;
		double newAngel = ang+(percento*angle);
		/*double pom = angle;
		pom = pom/100;
		double a = pom*80;*/
		 
		Joint j =Joint.getKey(name.toUpperCase());
				
		double low = Joint.valueOf(Joint.class, j.name()).getLow();
		double up = Joint.valueOf(Joint.class, j.name()).getUp();
		
		if(newAngel>low && newAngel<up){
		
			return Math.floor(newAngel);
		}
		return angle;
	}
	
	private int calculate(int time,int range){
		//range = range/2;
		//zmena - o kolko percent
		int change = randomGenerator(2*range,(range*(-1)));
		//hodnota jedneho percenta z casu(time) 
		double percento = (change/100.0);
		return (int) Math.floor(time + (percento*time));
		/*if (who == 0){
			return (int) Math.round(time*0.8);
		}
		else if(who == 1){
			return (int) Math.round(time*1.2);
		}		
		else{
			return time;
		}*/
	}
	
	public int randomGenerator(int max, int min){
		Random rndGen = new Random();
		
		return rndGen.nextInt(max)+min;//ak od jednotky +1;//
	}
	
	/**
	 * Mutuje v kazdej faze vybrane klby.
	 * Symetricku fazu mutuje o rovnako.
	 * @param move
	 * @param pamramGA
	 */
	public AgentMove mutateB(AgentMove move, ParameterGA paramGA){
		AgentMove pomMove= move;
		int numberOfPhase = pomMove.getPhases().size();
		int numberPhase=0;
		//phase for mutation
		Set<Integer>phasesForMutate = new HashSet<Integer>();
		//number phases for mutation
		int numberOfPhasesOnMutation = randomGenerator(numberOfPhase, 0);
		for(int i=0;i<numberOfPhasesOnMutation;i++){
			phasesForMutate.add(randomGenerator(numberOfPhase, 0));
		}
		//mutated phases
		ArrayList<AgentMovePhase> symetricPhases = new ArrayList<AgentMovePhase>();
		//for(AgentMovePhase phase : pomMove.getPhases()){
		for(Integer number:phasesForMutate){
			AgentMovePhase phase=pomMove.getPhases().get(number);
			//if(randdomGen(10, 0) >= 5){
				AgentMovePhase symPhase = findSymetricPhase(phase, pomMove, numberPhase);
				if(symPhase != null)
					symetricPhases.add(symPhase);
				if(!isMutated(phase,symetricPhases)){
					for(AgentMoveEffector effector : phase.getEffectors()){
						paramGA.getParameterMutation().getJoint();
						String efName=effector.getName().toUpperCase();
						if(paramGA.getParameterMutation().isSelected(efName)){
							int change = (int)calculate(effector.getEnd(),effector.getName(),paramGA.getParameterMutation().getRangeMutation());
							effector.setEnd(change);
							if(symPhase != null)
								getSymetricEffector(effector, symPhase).setEnd(change);
							
							
						}
					}
					int changeDuration=calculate(phase.getDuration(), paramGA.getParameterMutation().getRangeMutation());
					phase.setDuration(changeDuration);
					if(symPhase != null)
						symPhase.setDuration(changeDuration);
					
				}
				numberPhase++;
			//}
		}
		return pomMove;
	}
	/**
	 * 
	 * @param phase - phase for find symetric phase
	 * @param move - move where find symetric phase
	 */
	private AgentMovePhase findSymetricPhase(AgentMovePhase phaseS, AgentMove move, int numberPhase){
		int numberEffectorInPhase = phaseS.getEffectors().size();
		for(int ph=numberPhase+1; ph<move.getPhases().size(); ph++){
			AgentMovePhase phase = move.getPhases().get(ph);
			if(phase.getEffectors().size() == numberEffectorInPhase){
				//hlada opacny effector s rovnakym uhlom
				int equalsEffector = 0;
				for(int i=0; i<numberEffectorInPhase; i++){
					
					int j=0;
					boolean haveEqual = false;
					while(j<numberEffectorInPhase && haveEqual!=true){
						AgentMoveEffector efS = phaseS.getEffectors().get(i);
						AgentMoveEffector ef = phase.getEffectors().get(j);
						String efSName = efS.getName().substring(1, efS.getName().length());
						String efName = ef.getName().substring(1, ef.getName().length());
						String firstChar1 =efS.getName().substring(0, 1);
						String firstChar2 = ef.getName().substring(0, 1);
						if((efS.getEnd() == ef.getEnd()) && (efSName.compareTo(efName)==0) && (firstChar1.compareTo(firstChar2)!=0 || firstChar1.compareTo("h")==0)){
							equalsEffector++;
							haveEqual=true;
						}
						if(equalsEffector==numberEffectorInPhase){
							return phase;
						}
						j++;
					}//df
					
				}
			}
		}
		return null;
	}
	
	private boolean isMutated(AgentMovePhase phase, ArrayList<AgentMovePhase> symPhase){
		for(AgentMovePhase ph : symPhase){
			if(ph.getName().compareTo(phase.getName())==0){
				return true;
			}
		}
		return false;
	}
	
	private AgentMoveEffector getSymetricEffector(AgentMoveEffector effector, AgentMovePhase phase){
		for(AgentMoveEffector eff : phase.getEffectors()){
			String nameEffector=eff.getName().substring(1);
			String firsCharEff = eff.getName().substring(0, 1);
			if(nameEffector.compareTo(effector.getName().substring(1))==0 && (firsCharEff.compareTo(effector.getName().substring(0, 1))!=0 || firsCharEff.compareTo("h")==0)){
				return eff;
			}
		}
		return null;
	}
}
