package sk.fiit.jim.agent.highskill.runner;


/**
 * 
 *  HighSkillRunner.java
 *  
 *  This class manage running of HighSkillPlanner thread.
 *  {@link HighSkillPlanner}
 *  
 *@Title        Jim
 *@author       Nemecek,Markech
 *@author  		Gitmen
 */
public class HighSkillRunner {

	private static HighSkillPlanner planner = HighSkillPlanner.getInstance();
	
	private static boolean isRunning = false;
	
	/**
	 * At agents start up start new planner thread. In each new cycle runs the execution method of planner.
	 */
	public static void proceed(){
		
		if (!isRunning){
			planner.start();
			 isRunning = true;
		}
		else{
			planner.control();			
		}
	}

	public static HighSkillPlanner getPlanner() {
		return planner;
	}
}
