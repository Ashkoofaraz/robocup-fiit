package sk.fiit.jim.agent.highskill.move;

import sk.fiit.jim.agent.highskill.AbstractHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementSkills.MovementEnum;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.robocup.library.geometry.Vector3D;
/**
 * 
 * MovementHighSkill.java
 * 
 * This class is parent highskill for walk moves. In this class is the best  walk move chosen.
 * 
 * @Title Jim
 * @author Nemecek, Markech, Linner
 * @author gitmen
 *
 */
public class MovementHighSkill extends AbstractHighSkill {

	/**
	 * Speed enum for tactics. With this enum tactics choose speed(prioritized walk speed ) with which agent should move.
	 * @author Tomas Nemecek
	 */
	public static enum MovementSpeedEnum {
		WALK_SLOW(1), WALK_MEDIUM(2), WALK_FAST(3);

		private int speed;

		public int getSpeed() {
			return speed;
		}

		MovementSpeedEnum(int speed) {
			this.speed = speed;
		}
	};
	
	
	
    /**
     * Method for player when he want to go in specific position. for example 4,5.. have to be static
     * x,y - static
     * 
     * @param position
     * @param prefferedSpeed
     * @return
     */
    public String move(Vector3D position, MovementSpeedEnum prefferedSpeed) {
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.noneDyn(position.getX(), position.getY());
    	return moveBase (MoveObj,prefferedSpeed);
    }

    /**
	 * 
	 * Method for calling 'walk to ball' with specified speed. Both x,y is dynamic
	 * Algorithm chooses most suitable walk for agent by walk distance, preferred speed and saved walk annotations. 
	 * x,y - dynamic
	 * 
	 * @param position
	 * @param walkSlow
	 * @return lowSkillName 
	 */
    public String move(MovementSpeedEnum prefferedSpeed) {
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.BothDyn(WorldModel.getInstance().getBall(),WorldModel.getInstance().getBall());
    	return moveBase (MoveObj,prefferedSpeed);
    }
    
    public String move(DynamicObject x,DynamicObject y,double xOffset,double yOffset,MovementSpeedEnum prefferedSpeed) {
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.BothDyn(x,y);
    	MoveObj.setOffSet(xOffset, yOffset);
    	return moveBase (MoveObj,prefferedSpeed);
    }
    
    /**
     * Method for player when he want to go in specific position.
     * x - dynamic
     * y - static
     * 
     * @param x
     * @param y
     * @param offset_x
     * @param offset_y
     * @param prefferedSpeed
     * @return
     */
    public String move(DynamicObject x,double y,double xOffset,MovementSpeedEnum prefferedSpeed){
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.xIsDyn(x, y);
    	MoveObj.setOffSet(xOffset,0);
    	return moveBase (MoveObj,prefferedSpeed);
    }
    /**
     * Method for player when he want to go in specific position.
     * x - static
     * y - dynamic
     * 
     * @param x
     * @param y
     * @param prefferedSpeed
     * @return
     */
    public String move(Double x,DynamicObject y,double yOffset,MovementSpeedEnum prefferedSpeed){
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.yIsDyn(y, x);
    	MoveObj.setOffSet(0, yOffset);
    	return moveBase (MoveObj,prefferedSpeed);
        }
    /**
     *  Method for player when he want to go in specific position.
     *  x,y - Dynamic
     * @param x
     * @param y
     * @param prefferedSpeed
     * @return
     */
    public String move(DynamicObject x,Vector3D y,double xOffset,double yOffset,MovementSpeedEnum prefferedSpeed){
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.BothDyn(x, y);
    	MoveObj.setOffSet(xOffset,yOffset);
    	return moveBase (MoveObj,prefferedSpeed);
      }
    
    /**
     *  Method for player when he want to go in specific position.
     *  x,y - Dynamic
     * @param x
     * @param y
     * @param prefferedSpeed
     * @return
     */
    public String move(Vector3D x,DynamicObject y,double xOffset,double yOffset,MovementSpeedEnum prefferedSpeed){
    	MoveSkillPostionObject MoveObj = new MoveSkillPostionObject();
    	MoveObj.BothDyn(x, y);
    	MoveObj.setOffSet(xOffset, yOffset);
    	return moveBase (MoveObj,prefferedSpeed);
   }
    
    /**
	 * 
	 * Method for calling 'walk to specified (global) position' with specified speed. 
	 * Algorithm chooses most suitable walk for agent by walk distance, preferred speed and saved walk annotations. 
	 * If position parameter is null, agent will 'walk to ball'.
	 * 
	 * @param position
	 * @param walkSlow
	 * @return lowSkillName 
	 */
    
    public String moveBase (MoveSkillPostionObject movePostion,MovementSpeedEnum prefferedSpeed){
    	 double maxSuitability = 0;
         MovementEnum mostSuitableHighSkill = null;
         for (MovementEnum enumeration : MovementEnum.values()) {
             double value = enumeration.computeSuitability(movePostion, prefferedSpeed);
             if (value > maxSuitability) {
                 maxSuitability = value;
                 mostSuitableHighSkill = enumeration;
             }
         }
         HighSkill a = mostSuitableHighSkill.getMovementSkill();
         HighSkillRunner.getPlanner().addHighskillToQueue(a);
         //ReplanWindow(movePostion);
        

         return mostSuitableHighSkill.getLowSkillName();
    }

//NOT NEEDED. RELATIVE DISTANCE NEEDS TO BE COMPUTED IN HIGHSKILL, BECAUSE IT'S ALWAYS CHANGING
//	/**
//	 * Computing relative agents distance from target position 
//	 * @param position
//	 * @return realDistanceFromTargets
//	 */
//	private double computeRelativeDistance(Vector3D position) {
//		double myRelativeDistance =Vector3D.cartesian(0, 0, 0).getXYDistanceFrom(AgentModel.getInstance().getPosition());
//		double targetRelativeDistance =Vector3D.cartesian(0, 0, 0).getXYDistanceFrom(position);
//		
//		return targetRelativeDistance - myRelativeDistance;
//	}
}
