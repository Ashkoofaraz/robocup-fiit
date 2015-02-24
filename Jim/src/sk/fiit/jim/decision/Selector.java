package sk.fiit.jim.decision;

import java.util.ArrayList;
import java.util.List;
import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.decision.situation.SituationManager;
import sk.fiit.jim.decision.strategy.Strategy;
import sk.fiit.jim.decision.strategy.OffensiveStrategy;
import sk.fiit.jim.decision.strategy.StrategyList;
import sk.fiit.jim.decision.tactic.Tactic;
import sk.fiit.jim.decision.tactic.TacticList;
import sk.fiit.jim.gui.ReplanWindow;

/**
 * Class for selecting strategy and tactic according to detected situations
 * 
 * @author 	Vlado Bosiak <vladimir.bosiak@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 */
public class Selector {

	/** Constant for initial fitness */
	private final static float INITIAL_FITTNESS = 0;
	/** Constant for default strategy */
    private final static Strategy DEFAULT_STRATEGY = new OffensiveStrategy();
	
    /** Selected strategy */
    private Strategy selectedStrategy;
    /** Selected tactic */
    private Tactic selectedTactic;
    /** List of tactics from selected strategy */
    private ArrayList<Strategy> strategyList = StrategyList.getStrategies();
    /** Situation manager */
    private SituationManager situationManager = new SituationManager();

    /**
     * Select best strategy
     * 
     * @return true if strategy was successfully selected
     * @throws Exception
     */
    public boolean selectStrategy() throws Exception {
        Strategy bestStrategy = this.getBestStrategyForSituation(this.situationManager.getListOfCurrentSituations());
        this.setSelectedStrategy(bestStrategy);
        if (bestStrategy == null) {
            return false;
        }
        ReplanWindow.getInstance().getActualStrategyValue().setText(bestStrategy.getClass().getSimpleName());
        return true;
    }
    
    /**
     * Method for getting best strategy according to situations
     * 
     * @param currentSituations
     * @return Best strategy or default strategy
     */
    private Strategy getBestStrategyForSituation(List<String> currentSituations) {
        float bestSuitability = Selector.INITIAL_FITTNESS;
        float actualSuitability = Selector.INITIAL_FITTNESS;
        Strategy bestStrategy = Selector.DEFAULT_STRATEGY;

        for (Strategy strategy : this.strategyList) {
        	actualSuitability = Selector.INITIAL_FITTNESS;
            try {
                actualSuitability = strategy.getSuitability(currentSituations);
            } catch (Exception e) {
            	System.err.println("GETTING BEST STRATEGY:"+e.getMessage());
            }
            if (actualSuitability > bestSuitability) {
                bestSuitability = actualSuitability;
                bestStrategy = strategy;
            }
        }
        return bestStrategy;
    }

    /**
     * Select best tactic
     * 
     * @return true if tactic was successfully selected
     * @throws Exception
     */
    public boolean selectTactic() throws Exception {    	
    	Tactic bestTactic = this.getBestTacticForSituations(this.situationManager.getListOfCurrentSituations());
    	
    	this.setSelectedTactic(bestTactic);
    	if (bestTactic==null) return false;
		return true;
    }
    
    public boolean selectTacticFromSettings() throws Exception {
    	ArrayList<Tactic> tacticList = TacticList.getTactics();    	
    	
    	for(Tactic tactic : tacticList) {
    		if (tactic.getClass().getSimpleName().equals(Settings.getString("debuggingTacticName"))){    			
    			this.setSelectedTactic(tactic);    			
    			return true;
    		}    				
    	}    	
    	return false;
    }
    
    /**
     * 
     * @return
     */
    public boolean shouldSelectTacticFromSettings(){
    	return  Settings.getBoolean("debugTactic");    	
    }

    /**
     * Method for getting best tactic according to situations 
     * 
     * @param currentSituations
     * @return Best tactic or default tactic
     * @throws Exception 
     */
    protected Tactic getBestTacticForSituations(List<String> currentSituations) throws Exception {
    	
    	//if we dont have selected strategy yet we have to select it.
    	if (this.selectedStrategy == null) selectStrategy();
    	
    	Tactic bestTactic = (this.selectedTactic != null ? this.selectedTactic : this.selectedStrategy.getDefaultTactic());
    	List<Tactic> tacticList = this.selectedStrategy.getPrescribedTactics();
    	float bestSuitability = Selector.INITIAL_FITTNESS;
    	float actualSuitability = Selector.INITIAL_FITTNESS;
    	for(Tactic tactic : tacticList) {
			AgentInfo.logState(tactic.getClass().getSimpleName()
					+ "\n Init Condition is : "
					+ tactic.getInitCondition(currentSituations)
					+ "\n Suitabilita"
					+ tactic.getSuitability(currentSituations));

			if (!tactic.getInitCondition(currentSituations)) {
				continue;
			}
			actualSuitability = Selector.INITIAL_FITTNESS;
			try {
				actualSuitability = tactic.getSuitability(currentSituations);
            } catch (Exception e) {
                System.err.println("GETTING BEST TACTIC:"+e.getMessage());
            }
            if (actualSuitability > bestSuitability) {
                bestSuitability = actualSuitability;
                bestTactic = tactic;
            }
    	}
    	AgentInfo.logState("Decide to do Tactic: " + bestTactic.getClass().getSimpleName());
    	return bestTactic;
    }

    /**
     * Setter for selected strategy
     * 
     * @param strategy Strartegy which have to be selected
     * @return Selector instance for chaining
     */
    public Selector setSelectedStrategy(Strategy strategy) {
        this.selectedStrategy = strategy;
        return this;
    }

    /**
     * Setter for selected tactic
     * 
     * @param tactic Tactic which have to be selected
     * @return Selector instance for chaining
     */
    public Selector setSelectedTactic(Tactic tactic) {
        this.selectedTactic = tactic;
        return this;
    }

    /**
     * Getter for selected strategy
     * 
     * @return Selected strategy.
     */
    public Strategy getSelectedStrategy() {
        return this.selectedStrategy;
    }

    /**
     * Getter for selected tactic
     * 
     * @return Selected tactic
     */
    public Tactic getSelectedTactic() {
        return this.selectedTactic;
    }
    
    /**
     * Getter for situation manager
     * 
     * @return Instance of SituationManager
     */
    public SituationManager getSituationManager() {
    	return this.situationManager;
    }
    
    /**
     * Set situation manager
     * 
     * use in selectorTest to set a mock
     * 
     * @param SituationManager to set in selector.
     */
    public void setSituationManager(SituationManager situationManager) {
    	this.situationManager = situationManager;
    }
    
    

}
