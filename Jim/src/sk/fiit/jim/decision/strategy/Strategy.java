package sk.fiit.jim.decision.strategy;

import java.util.ArrayList;
import java.util.List;

import sk.fiit.jim.decision.tactic.Tactic;

public interface Strategy {

	public float getSuitability(List<String> currentSituations) throws Exception;
	public ArrayList<Tactic> getPrescribedTactics();
	public Tactic getDefaultTactic();
	
}
