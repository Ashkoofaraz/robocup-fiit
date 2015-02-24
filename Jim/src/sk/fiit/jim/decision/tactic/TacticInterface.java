package sk.fiit.jim.decision.tactic;

import java.util.List;

public interface TacticInterface {
	
	/**
     * Getter for initialization condition
     *
     * @param currentSituations List of current situations
     * @return Status if tactic is usable
     */
    public boolean getInitCondition(List<String> currentSituations);

    /**
     * Getter for progress condition
     *
     * @param currentSituations list of current situations
     * @return Status if tactic ended
     */
    public boolean getProgressCondition(List<String> currentSituations);

    /**
     * Execute tactic
     */
    public void run();

    public List<String> getPrescribedSituations();
    
    public void startTactic(List<String> currentSituations);
    
    public float getSuitability(List<String> currentSituations);
    
}
