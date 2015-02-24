package sk.fiit.jim.agent.highskill.runner;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.GetUp;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.jim.garbage.plan.Plan;

/**
 * 
 * HighSkillPlanner.java
 * 
 * This class is used for planning the execution of chosen higher skills. These
 * skills are added to {@link BlockingDeque}. Skills from queue are peeked and
 * then executed. Only if the last skill was ended, new skill can be performed.
 * If the agents falls on ground there is implicit added get up skill in queue
 * to first position. It's copy of former ruby highskill's Planner class.
 * 
 * @Title Jim
 * @author Nemecek,Markech
 * @author Gitmen
 */
public class HighSkillPlanner extends Thread {

	private static HighSkillPlanner hsr = null;
	private BlockingDeque<HighSkill> highSkillQueue = new LinkedBlockingDeque<HighSkill>();
	private HighSkill currentHighSkill = null;
	private String lastLogMessage = "";
	private boolean abort = false;
	private HighSkill abortedHighSkill = null;

	public static HighSkillPlanner getInstance() {
		if (hsr == null) {
			hsr = new HighSkillPlanner();
		}
		return hsr;
	}

	private HighSkillPlanner() {

	}

	public void run() {
		control();
	}

	/**
	 * Method control is called EVERYTIME when the message from server arrives.
	 * In this method is checking for fallen agent.
	 */
	public void control() {
		try {			
			if (abort){
				abortedHighSkill.execute();
				if (abortedHighSkill.isEnded()) {			
						abort = false;
						abortedHighSkill = null;
				}
			}
			else if (! highSkillQueue.isEmpty()){
				currentHighSkill = highSkillQueue.peek();
				currentHighSkill.execute();
				if (currentHighSkill.isEnded()) {			
					highSkillQueue.poll();
				}	
			}		
			
			logHighSkillQueue();
			
		} catch (Exception ex) {
			Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Add selected higher skill to queue.
	 * 
	 * @param highSkill
	 */
	public void addHighskillToQueue(HighSkill highSkill) {
		highSkillQueue.addLast(highSkill);	
	}
	
	// Toto je nebezpecne, pretoze v dalsom volani funkcie control()
	// uz nebude currentHighSkill ten, ktory sa vykonaval, 
	// ale ten ktory by sa teraz pridal do queue
	// Treba toto pouzivat, iba ak nechceme aby sa skoncil prave vykonavany HighSkill
	public void addHighskillAsFirst(HighSkill highSkill) {
		highSkillQueue.addFirst(highSkill);
	}

	public HighSkill getcurrentHighSkill() {
		return currentHighSkill;
	}

	/**
	 * Peek the top of queue
	 * 
	 * @return {@link HighSkill}
	 */
	public HighSkill getNextHighSkill() {
		return highSkillQueue.peek();
	}

	public int getNumberOfPlannedHighSkills() {
		return highSkillQueue.size();
	}

	private void clearHighSkillQueue() {
		highSkillQueue.clear();
	}

	public boolean isStoppedCurrentExecutingHighSkill() {
		return currentHighSkill.isStoppedHighSkill();
	}

	public void stopCurrentExecutingHighSkill() {
		if (getNumberOfPlannedHighSkills() > 0)
			currentHighSkill.stopHighSkill();
	}

	/**
	 * Clears the queue and stops running higher skill
	 * {@link #stopCurrentExecutingHighSkill}
	 * {@link #clearHighSkillQueue}
	 */
	public void abortPlannedHighSkills() {
		if(abort == false){
			if(getNumberOfPlannedHighSkills() > 0){
				stopCurrentExecutingHighSkill();
				abort = true;
				abortedHighSkill = getcurrentHighSkill();
				clearHighSkillQueue();
			}
		}
		if (abort == true){
			clearHighSkillQueue();
		}
	}
	
	private void logHighSkillQueue(){
		String currentLogMessage = "";
		
		if(highSkillQueue.size() < 1){
			currentLogMessage = "HighSkillQueue: size = 0";
		}
		else {
			currentLogMessage = "HighSkillQueue: size = "
			+ highSkillQueue.size() + ",first = " + highSkillQueue.peekFirst().getClass().getSimpleName() 
			+ ",last = " + highSkillQueue.peekLast().getClass().getSimpleName();
		}
		
		if (!currentLogMessage.equals(lastLogMessage)){
			AgentInfo.logState(currentLogMessage);
			lastLogMessage = currentLogMessage;
		}
	}
}
