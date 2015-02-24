package sk.fiit.jim.agent.highskill.move;

/**
 * 
 * WalkSlow.java
 * 
 * Specific implementation of slow speed walk.
 * 
 * @Title Jim
 * @author Nemecek, Markech
 * @author gitmen
 *
 */
public class WalkSlow2 extends Walk{
	public String name = "WalkSlow2";
	
	protected  WalkSlow2(){		
	}
	
	@Override
	public String getLowSkillName() {
		return "walk_slow2";
	}
	
	@Override
	public String getAnnotationName() {
		return "walk_slow2";
	}

}
