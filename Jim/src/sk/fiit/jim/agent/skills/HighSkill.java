package sk.fiit.jim.agent.skills;

import static sk.fiit.jim.log.LogType.HIGH_SKILL;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.communication.testframework.Message;
import sk.fiit.jim.agent.communication.testframework.TestFrameworkCommunication;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.BodyPart;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.moves.LowSkill;
//------------------------------------------------------------
// Nasledujuce importy tim 17 v tomto subore nema
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phases;
import sk.fiit.jim.log.Log;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3D;
//----------------------------------------------------------


/**
 *  
 *  <p>HighSkill represents an action - such as walking to a point, turning
 *  or standing up. This is achieved by selecting the appropriate low skills to perform
 *  smaller parts of the action.</p> 
 *  <p>High skills are implemented by subclassing this class.
 *  Most of them are currently written in Ruby and placed in the scripts/high_skills
 *  directory. When subclassing a high skill, the following methods need to be implemented:
 *  <ol>
 *  	<li>{@link #checkProgress}</li>
 *  	<li>{@link #pickLowSkill}</li>
 *  </ol>
 *  See their respective descriptions for details.</p>
 *  <p>For details on how to implement a new high skill, see (TODO: wiki page)</p>
 *
 *
 *@Title	Jim
 *@author	marosurbanec
 *@author	Androids
 */
public abstract class HighSkill implements IHighSkill {
	
    private static class StateLogger
    {
        private static StateLogger INSTANCE = new StateLogger();
        
        private static BufferedWriter bw ;
        static
        {
            try
            {
                bw = new BufferedWriter(new FileWriter("20140329_-040_9.csv"));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void log(Vector3D point)
        {
            String line = String.format(Locale.GERMAN, "%.3f;%.3f\n", point.getX(), point.getY());
            try
            {
                bw.write(line);
                bw.flush();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        private StateLogger()
        {
        }
        
        public static StateLogger getInstance()
        {
            return INSTANCE;
        }

        @Override
        protected void finalize() throws Throwable
        {
            bw.close();
            super.finalize();
        }
    }
    
	/**
	 * The low skill that's currently being performed
	 */
	protected LowSkill currentSkill;
	/**
	 * Low skill that was returned by {@link #pickLowSkill}, but is not yet active,
	 * because the current skill needs to be finalized first. Once that happens,
	 * this skill will become the current low skill (or the high skill will end if nextSkill is null).
	 */
	protected LowSkill nextSkill;
	/**
	 * Name of the HighSkill
	 */
	public String name = "unnamed high skill";
	
	/**
	 * Contains the possible states that a high skill can be in. These affect the behavior of the
	 * {@link HighSkill#execute} method.
	 * <ul>
	 * <li>INITIAL_STATE - the starting state of all high skills</li>
	 * <li>EXECUTING_STATE - the high skill is currently executing a chosen low skill</li>
	 * <li>FINALIZING_STATE - the high skill is currently bringing the chosen low skill
	 * into a safe, final state. Once that's done, a new low skill will be executed, or the
	 * high skill will end (depending on whether nextSkill is null)</li>
	 * <li>END_STATE - the high skill has ended and can no longer be used</li>
	 * </ul> 
	 */
	public enum HighSkillState { INITIAL_STATE, EXECUTING_STATE, FINALIZING_STATE, END_STATE };
	/**
	 * The state that the high skill is currently in. See {@link HighSkillState} for details.
	 */
	public HighSkillState state = HighSkillState.INITIAL_STATE;

	private boolean stopHighSkill = false;
	
	protected HighSkill(){
		
	}
	
	/*
	 * Gets name of current class - nam of the highskill
	 */
    public String getName(){
    	return this.getClass().getSimpleName();
    }
	
	/**
	 * <p>Performs a single step of the high skill. This means handling the selection of low skills,
	 * their execution and finalization. For details, see TODO: wiki link</p>
	 * <p>This method can also be subclassed to implement a more complex control structure
	 * (high skills that execute other high skills, etc.)</p> 
	 *
	 * @throws Exception
	 */
	public final void execute() throws Exception {
		Log.debug(HIGH_SKILL, "Currently executing: %s", currentSkill);
		
		switch (state) {
		case INITIAL_STATE:
		    logState(state);
	        
			//get a low skill from the specific high skill (usually a ruby script)
			currentSkill = pickLowSkill();
			//if none is returned, we're done
			if (currentSkill == null) {
				state = HighSkillState.END_STATE;
				return;
			}
			//reset the low skill to its initial state and start executing it
			currentSkill.reset();
			currentSkill.step();
			state = HighSkillState.EXECUTING_STATE;
			sayMoveStartedToFramwork(currentSkill);
			break;
			
		case EXECUTING_STATE:
		    logState(state);
			if (currentSkill.canFinalize() && !isStoppedHighSkill()) {
				// this happens when a phase with isFinal==true has ended
				// (so that the current low skill can now be finalized if we
				// choose to do so)
				// we need to decide whether to continue with the current low
				// skill
				// or pick a different one

				nextSkill = pickLowSkill();
				if (nextSkill == currentSkill) {
					// if it's the same skill, keep going as if nothing happened
					currentSkill.step();

					// End of current low skill and start of next are similar,
					// so it is possible to connect them directly
				} else if (nextSkill != null && 
						Phases.areSimilarPhases(
								Phases.get(nextSkill.initialPhase), 
								currentSkill.activePhase, 
								100)) {

					currentSkill = LowSkills.get(nextSkill.name);
					currentSkill.reset();
					currentSkill.step();
				} else {
					// if it's a different one, we need to finalize the current
					// one
					currentSkill.executeFinalisation();
					currentSkill.step();
					state = HighSkillState.FINALIZING_STATE;
				}
			} else if (currentSkill.canFinalize() && isStoppedHighSkill()) {
				//we need to finalize the current one
				currentSkill.executeFinalisation();
				currentSkill.step();
				state = HighSkillState.FINALIZING_STATE;
				
			} else {
				// if the current low skill can't be safely interrupted, we have
				// to keep going
				checkProgress();
				currentSkill.step();
				AgentInfo info = AgentInfo.getInstance();
				info.setState(currentSkill.name.toString());
			}
			break;
			
		case FINALIZING_STATE:
		    logState(state);
			//if the finalization phase has come to an end
			if (currentSkill.canFinalize()) {
				//if there's a next skill, start executing it
				if (nextSkill != null && !isStoppedHighSkill()) {
					currentSkill = nextSkill;
					currentSkill.reset();
					currentSkill.step();
					state = HighSkillState.EXECUTING_STATE;
				}else{
					//otherwise just finish the high skill altogether
					state = HighSkillState.END_STATE;
					sayMoveStoppedToFramwork(currentSkill);
				}
			}else{
				//otherwise we need to finish the finalization phase
				currentSkill.step();
			}
			break;
		}
	}
	
	/**
	 * addeded by Bimbo (High5), communication with test framework
	 * Blank try-catch because of ruby
	 * BUG - have to be in Lowskill
	 */
	private static void sayMoveStoppedToFramwork(LowSkill lowSkill) {
		try{
			//System.out.println("stopped:"+lowSkill.name);
			TestFrameworkCommunication.sendMessage(
				new Message().HighSkill().Stop(lowSkill.name, AgentModel.getInstance().getLastDataReceived().GAME_TIME));
		}catch(Exception e){
		}
	}
	private static void sayMoveStartedToFramwork(LowSkill lowSkill) {
		try{
			//System.out.println("started:"+lowSkill.name);
			TestFrameworkCommunication.sendMessage(
				new Message().HighSkill().Start(lowSkill.name, AgentModel.getInstance().getLastDataReceived().GAME_TIME));
		}catch(Exception e){
		}
	}

	/**
	 * <p>Called everytime the current low skill enters a phase that be can be finalized
	 * (so that it's possible to safely switch to a new low skill, see TODO: wiki link)</p>
	 * <p>Returns the next skill that needs to be executed
	 * in order to fulfill goals of this HighSkill.</p>
	 * <p>If the returned skill is the same as the current skill, it will continue normally.</p>
	 * <p>If a different low skill or null is returned, the current low skill will be finalized.
	 * Once that finalization has been succesfully completed, the returned low skill will be
	 * set as the new executed low skill, or, if null was returned, the high skill will end.
	 * 
	 * @return The next skill that needs to be executed. Null if the high skill should stop.
	 */
	public abstract LowSkill pickLowSkill();
	
	/**
	 * <p>Called on every single step of the high skill. That means every tick.</p>
	 * <p>Should throw an exception if the agent is found to be in
	 * an unexpected state, e.g. fallen on ground during walking.</p>
	 * <p>Causes this skill's execution to be immediately terminated.</p>
	 * TODO: does it work? should it work this way? currently, falling is handled by the
	 * pickLowSkill methods in our high skills
	 * 
	 * @throws Exception means that the high skill needs to be interrupted without regard
	 * to safe finalization of the current low skill
	 */
	public abstract void checkProgress() throws Exception;
	
	/**
	 * Returns true if the HighSkill has ended (it's in END_STATE). 
	 */
	public boolean isEnded(){
		return (state == HighSkillState.END_STATE);
	}
	
	/**
	 * Returns the currently executed low skill.
	 */
	public LowSkill getCurrentSkill(){
		return currentSkill;
	}
	
	//TODO: is this safe (no)? is it called from the test framework?
	public void setCurrentSkill(LowSkill skill) {
		this.currentSkill = skill;
	}

	@Override
	public String toString(){
		return name + ": current low skill: " + currentSkill.toString();
	}
	
	public boolean isballLongUnseen(double gap){
		return (EnvironmentModel.SIMULATION_TIME - WorldModel.getInstance().getBall().getLastTimeSeen()) > gap;
	}

	public boolean isStoppedHighSkill() {
		return stopHighSkill;
	}

	public void stopHighSkill() {
		stopHighSkill = true;
	}
	
	private static void logState(HighSkillState state) {
	    System.out.println(state);
	    AgentModel model = AgentModel.getInstance();
//	    System.out.println("Player position: " + model.getPosition());
//	    System.out.println("Player distance from ball: " + model.getDistanceFromBall());
//	    System.out.println("Center of Mass: " + model.getCenterOfMass());
//	    AgentInfo.logState(WorldModel.getInstance().getBall().getPosition().toString());
	    System.out.println("Ball pos: " + WorldModel.getInstance().getBall().getPosition());
	    StateLogger.getInstance().log(WorldModel.getInstance().getBall().getPosition());
//	    FileWrite.createFile("36_1", WorldModel.getInstance().getBall().getPosition());
//	    System.out.println("Ball relative: " + WorldModel.getInstance().getBall().getRelativePosition());
//        System.out.println("torso abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.TORSO));
//        System.out.println("torso rel pos: " +  model.getBodyPartRelPositions().get(BodyPart.TORSO));
//        System.out.println("left hip1 abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LHIP1) );
//        System.out.println("left hip1 rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LHIP1));
//        System.out.println("left hip2 abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LHIP2) );
//        System.out.println("left hip2 rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LHIP2));
//        System.out.println("left thigh abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LSHANK) );
//        System.out.println("left thigh rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LSHANK));
//        System.out.println("left shank abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LSHANK) );
//        System.out.println("left shank rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LSHANK));
//        System.out.println("left ankle abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LANKLE) );
//        System.out.println("left ankle rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LANKLE));
//        System.out.println("left foot abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LFOOT) );
//        System.out.println("left foot rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LFOOT));
//        
//        System.out.println("left hand abs pos: " + model.getBodyPartAbsPositions().get(BodyPart.LLOWERARM) );
//        System.out.println("left hand rel pos: " + model.getBodyPartRelPositions().get(BodyPart.LLOWERARM));
	}
}