package sk.fiit.jim.agent.highskill.move;

/**
 * 
 * WalkMedium.java
 * 
 * Specific implementation of medium speed walk.
 * 
 * @Title Jim
 * @author Nemecek, Markech
 * @author gitmen
 *
 */
public class WalkMedium extends Walk {
	public String name = "WalkMedium";

	//TODO try to create solution with reusable Singleton Factory to do not need to write getInstance and private constructor for each new class
	protected WalkMedium(){		
	}
	
	@Override
	protected String getAnnotationName() {
		return "walk_forward";
	}
	
	@Override
	public String getLowSkillName() {
		return "walk_forward";
	}
	
	
}
