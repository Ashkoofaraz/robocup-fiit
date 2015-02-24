package sk.fiit.jim.decision.tactic;

import java.util.List;

import sk.fiit.jim.agent.highskill.BeamHighSkill;
import sk.fiit.jim.agent.highskill.kick.KickHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.gui.ReplanWindow;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Interface for defining tactic instances
 *
 * @author Samuel Benkovic <sppred@gmail.com>, Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public abstract class Tactic {


    public static final int UNDEFINED_STATE = -1;
    public static final String UNDEFINED_STATE_NAME = "Undefined State";
    public static final Vector3D TheyGoal = Vector3D.cartesian(15, 0, 0);
    public static final Vector3D OurGoal = Vector3D.cartesian(-15, 0, 0);
    

    private MatchStarterTactic starterTactic	   = new MatchStarterTactic();
    protected KickHighSkill        kickExec        = new KickHighSkill();
    protected MovementHighSkill    moveExec        = new MovementHighSkill();
    protected BeamHighSkill        beamExec        = new BeamHighSkill();
    protected UndefinedStateTactic undefinedTactic = new UndefinedStateTactic();
    
    protected int currentState;

    /**
     * Getter for initialization condition
     *
     * @param currentSituations List of current situations
     * @return Status if tactic is usable
     */
    public abstract boolean getInitCondition(List<String> currentSituations);

    /**
     * Getter for progress condition
     *
     * @param currentSituations list of current situations
     * @return Status if tactic ended
     */
    public abstract boolean getProgressCondition(List<String> currentSituations);

    /**
     * Checks the current state of tactic
     *
     * @return int - state
     */
    protected abstract int checkState(List<String> currentSituations);

    /**
     * Execute tactic
     */
    public abstract void run();

    public abstract List<String> getPrescribedSituations();

    /**
     * Checks the state for execute run method
     *
     * @return boolean
     */
    private boolean isNewState(List<String> currentSituations) {
        int identifiedState = this.checkState(currentSituations);
        if (this.currentState != 0 && this.currentState == identifiedState) {
            return false;
        }
        this.currentState = identifiedState;
        return true;
    }

    /**
	 * Execute run if it is needed
	 */
	public void startTactic(List<String> currentSituations) {
		if (EnvironmentModel.beamablePlayMode() == true) {
			starterTactic.runBeam();
			ReplanWindow.getInstance().updateText(ReplanWindow.VALUE_TACTICS, "BEAM");
			return;
		}
		if (EnvironmentModel.isKickOffPlayMode() == true) {
			if(HighSkillRunner.getPlanner().getNumberOfPlannedHighSkills() == 0
					&& HighSkillRunner.getPlanner().getcurrentHighSkill().isEnded()) {
				starterTactic.runStart();
			}
			ReplanWindow.getInstance().updateText(ReplanWindow.VALUE_TACTICS, "KICK OFF");
			return;
		}
		if (this.isNewState(currentSituations)
				|| (HighSkillRunner.getPlanner().getNumberOfPlannedHighSkills() == 0
				&& HighSkillRunner.getPlanner().getcurrentHighSkill().isEnded())) {
			this.stopTacticHighSkills();
			if (this.currentState == UNDEFINED_STATE) {
				runUndefinedState(currentSituations);
                ReplanWindow.getInstance().updateText(ReplanWindow.VALUE_TACTICS, Tactic.UNDEFINED_STATE_NAME);
            } else {
                this.run();
                ReplanWindow.getInstance().updateText(ReplanWindow.VALUE_TACTICS, this.getClass().getSimpleName());
			}
		}
	}

	public void stopTacticHighSkills() {
        HighSkillRunner.getPlanner().abortPlannedHighSkills();
    }

    protected void runUndefinedState(List<String> currentSituations) {
    	undefinedTactic.run(currentSituations);
    }

    /**
     * Method is needed if more tactics initcondition evaluates to true
     * Calculated on the basis HighSkill and current situation.
     *
     * @return NumberOfMatch/currentSituations.size()
     */
    public float getSuitability(List<String> currentSituations) {
        int NumberOfMatch = 0;
        /*
         * TODO - better check two lists ? Maybe HASH ? NumberOfMatch - one
		 * class for every strategy, not in all strategies
		 */

        List<String> prescribedSituations = this.getPrescribedSituations();
        for (String a : currentSituations) {
            for (String b : prescribedSituations) {
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
        return ((float) NumberOfMatch / prescribedSituations.size());
    }

}
