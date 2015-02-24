package sk.fiit.jim.decision.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.decision.situation.FightForBall;
import sk.fiit.jim.decision.tactic.attack.AttackLeft;
import sk.fiit.jim.decision.tactic.attack.AttackMid;
import sk.fiit.jim.decision.tactic.attack.AttackRight;
import sk.fiit.jim.decision.tactic.defense.Defend;
import sk.fiit.jim.decision.tactic.defense.DefendAgressive;
import sk.fiit.jim.decision.tactic.defense.DefendPosition;
import sk.fiit.jim.decision.tactic.Tactic;

/**
 * Team 04 - RFC Megatroll Prototype of one strategy
 * 
 * @author michal petras + samo benkovic
 * 
 */
public class OffensiveStrategy implements Strategy {

	private ArrayList<String> prescribedSituations = new ArrayList<String>(
			Arrays.asList(FightForBall.class.getName()));
	private ArrayList<Tactic> prescribedTactics = new ArrayList<Tactic>(Arrays.asList(new AttackMid(),new AttackLeft(),new AttackRight(),new Defend(),new DefendAgressive(),new DefendPosition()));
	private Tactic defaultTactic = new AttackLeft();

	/*
	 * returnSuitability - check list of currentSituation and list of prescribed
	 * situations
	 */
	public float getSuitability(List<String> currentSituations)
			throws Exception {
		int NumberOfMatch = 0;

		//Todo #Task(better check two lists ? Maybe HASH ? NumberOfMatch - one.) #Solver() #Priority() | xmarkech 2013-12-10T20:38:13.4560000Z
		/*
		 * class for every strategy, not in all strategies
		 */
		for (String a : currentSituations) {
			for (String b : this.prescribedSituations) {
				if (a.equals(b)) {
					NumberOfMatch++;
					break;
				}
			}
		}

		/*
		 * ReturnFit returns number (NumberOfMatch) - 1,2,5 etc. NumberOfMatch
		 * in currentsituations and prescribedsituations
		 */
		return NumberOfMatch/currentSituations.size();
	}

	public ArrayList<Tactic> getPrescribedTactics() {
		return prescribedTactics;
	}

	public Tactic getDefaultTactic() {
		return defaultTactic;
	}
	
	

}
