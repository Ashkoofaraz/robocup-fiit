package sk.fiit.jim.decision;

import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.decision.tactic.Tactic;

/**
 * Class for plan and replan all the strategy and tactic according to detected situations
 * Call methods from selector.
 * Decide when player change strategy or tactic. 
 * 
 * @author 	Matej Badal <matejbadal@gmail.com>, Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 */
public class SelectorController {
	 
	public static final double SUITABILITY_ACCURATION = 0.5;
	
	private Selector selector = new Selector();
	
	private int previousSituations;
	 
	/**
	 * Decides when player changes tactic.
	 *
	 * @throws Exception
	 */
	public void controlTactics() throws Exception {
		Tactic currentTactic = this.selector.getSelectedTactic();
		List<String> currentSituations = this.selector.getSituationManager()
				.getListOfCurrentSituations();
		// IF CURRENT TACTIC IS NOT SET
		if (currentTactic == null || (currentSituations.hashCode() != this.previousSituations
						&& !currentTactic.getProgressCondition(currentSituations))){
			AgentInfo.logState("REPLAN");
			replan();
			currentTactic = this.selector.getSelectedTactic();
			AgentInfo.logState(currentTactic.toString());
			this.previousSituations = currentSituations.hashCode();
		} else if(currentSituations.hashCode() != this.previousSituations && !this.selector.shouldSelectTacticFromSettings()) {
			currentTactic = checkBetterTactic(currentSituations, currentTactic);
			this.previousSituations = currentSituations.hashCode();
		}
		currentTactic.startTactic(currentSituations);
	}
	
	public Tactic checkBetterTactic(List<String> currentSituations, Tactic currentTactic) throws Exception {
		Tactic bestTactic = this.selector.getBestTacticForSituations(currentSituations);
		return ((currentTactic.getSuitability(currentSituations)
				<(bestTactic.getSuitability(currentSituations)-SUITABILITY_ACCURATION)) ?
				bestTactic : currentTactic );
	}
	
	public void replan() throws Exception {
		if ( this.selector.shouldSelectTacticFromSettings() ) {
			this.selector.selectTacticFromSettings();
		} else {
			this.selector.selectTactic();
		}
	}

	/**
	 * ControlStrategy is method which decide when player change stratagy.
	 * 
	 * @throws Exception
	 */
	public void controlStrategy() throws Exception {
		this.selector.selectStrategy();
	}

	/**
	 * Geter of selector.
	 * 
	 * @see SelectorControllerTest
	 * @return selector
	 */
	public Selector getSelector() {
		return this.selector;
	}

	/**
	 * Seter of selector.
	 * 
	 * @see SelectorControllerTest
	 */
	public void setSelector(Selector selector) {
		this.selector = selector;
	}
	 
}
