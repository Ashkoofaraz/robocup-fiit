/**
 * 
 */
package sk.fiit.jim.agent.skills;

import sk.fiit.jim.agent.models.WorldModel;

/**
 * 
 *  WalkBehindBall.java
 *  
 *@Title        Jim
 *@author       $Author: Bimbo $
 */
public class JWalkBehindBall extends ComplexHighSkill{
	
	@Override
	public HighSkill pickHighSkill() {
		if(WorldModel.getInstance().getBall().notSeenLongTime()>5){
		}
		return null;
	}

}
