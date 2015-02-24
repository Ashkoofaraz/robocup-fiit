package sk.fiit.jim.agent.highskill.move;

import java.math.BigDecimal;
import java.math.RoundingMode;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.GetUp;
import sk.fiit.jim.agent.highskill.Localize;
import sk.fiit.jim.agent.highskill.runner.HighSkillPlanner;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.jim.agent.skills.HighSkillUtils;
import sk.fiit.jim.gui.ReplanWindow;
import sk.fiit.robocup.library.geometry.Angles;
import sk.fiit.robocup.library.geometry.Vector3D;


/**
 * 
 * Walk.java
 * 
 * Abstract class for all walk highskills. In this class are common shared constants and methods.
 * 
 * @Title Jim
 * @author Nemecek, Markech, Linner
 * @author gitmen
 *
 */
public abstract class Walk extends HighSkill{
	
	protected double x1 = 0.04;
	protected double x2 = 0.12;
	protected double y1 = 0.35;
	protected double y2 = 0.3;

	protected double closeDistance = 0.7;
	protected double minDistance = 0.0;

	//rozsah, ktory musi agent dodrzat, ked este stoji
	protected Angles straight_range1 = new Angles(0.0, 15.0);
	protected Angles straight_range2 = new Angles(345.0, 360.0);
	
	//rozsah, ktory musi agent dodrzat, ked uz chodi
	//dolezite pri rychlych chodzach, alebo pri chodzi na urcitu poziciu
	//prisposobene v metode adjustRanges()
	protected Angles straight_range3  = straight_range1;
	protected Angles straight_range4  = straight_range2;
	
	protected Angles right_range = new Angles(180.0, 325.0);
	protected Angles right_range2 = new Angles(325.0, 340.0);
	protected Angles right_range3 = new Angles(340.0, 346.0);
	
	protected Angles left_range = new Angles(35.0, 180.0);
	protected Angles left_range2 = new Angles(20.0, 35.0);
	protected Angles left_range3 = new Angles(14.0, 20.0);

	protected Vector3D relative_target = null;
	protected MoveSkillPostionObject original_target = null;
	protected boolean checkBall = false; // old walk to ball

	protected AgentInfo agentInfo = AgentInfo.getInstance();
	protected AgentModel agentModel = AgentModel.getInstance();
	protected WorldModel worldModel = WorldModel.getInstance();
	protected HighSkillPlanner planner = HighSkillPlanner.getInstance();
	
	protected String lastPickedLowSkill = "";
	protected boolean pickedDifferentLowSkill = false;
	protected final double MAX_LAST_TIME_FLAG_SEEN = 15.0;
	
	protected abstract String getLowSkillName();
	
	protected abstract String getAnnotationName();
	
	@Override
	public LowSkill pickLowSkill() {
		
		computeRelativeTarget();
		
		if (pickedDifferentLowSkill == true){
			lastPickedLowSkill = "different";
		}
		if (lastPickedLowSkill.equals(getLowSkillName())){
			pickedDifferentLowSkill = true;
		}
		
		double targetPositionPhi = relative_target.getPhi();

		if (agentModel.isOnGround() || agentModel.isLyingOnBack()
				|| agentModel.isLyingOnBelly()) {
			planner.addHighskillAsFirst(new GetUp());
			return LowSkills.get("rollback");	//nebudeme ukoncovat tento HighSkill
		} else if ( (checkBall && WorldModel.getInstance().getBall().notSeenLongTime() > 5) ) {
			planner.addHighskillAsFirst(new Localize());
			return LowSkills.get("rollback");	//nebudeme ukoncovat tento HighSkill
		} else if ( (EnvironmentModel.SIMULATION_TIME - agentModel.getLastTimeFlagSeen()) > MAX_LAST_TIME_FLAG_SEEN){
			return LowSkills.get("turn_right_45");
		} else if (relative_target.getR() < closeDistance) {
			
			if ( ! checkBall){
				//ak sa jedna o chodzu na urcitu poziciu, vypocitana relativna pozicia
				//je velmi zasumena, preto nema zmysel nastavovat sa presne na ciel
				
				return null;
			}
			
//			agentInfo.loguj("som pri lopte ");
			if (relative_target.getY() > y1) {
				if (relative_target.getX() < -x2) {
//					agentInfo.loguj("ZONA 3");
					return LowSkills.get("step_left");
				} else if (relative_target.getX() > x2) {
//					agentInfo.loguj("ZONA 2");
					return LowSkills.get("step_right");
				} else {
//					agentInfo.loguj("ZONA 1");
					return LowSkills.get("walk_slow");
				}
			} else if (relative_target.getY() > y2) {
				if (relative_target.getX() < -x2) {
//					agentInfo.loguj("ZONA 3");
					return LowSkills.get("step_left");
				} else if (relative_target.getX() > x2) {
//					agentInfo.loguj("ZONA 2");
					return LowSkills.get("step_right");
				
				//final position
				} else {
					
					return null;	
				}
			}else if (relative_target.getY() < 0) {
				if (relative_target.getX() < -x2) {
//					agentInfo.loguj("ZONA 4");
					return LowSkills.get("walk_back");
				} else if (relative_target.getX() > x2) {
//					agentInfo.loguj("ZONA 5");
					return LowSkills.get("walk_back");
				}else if (relative_target.getX() > 0) {
//					agentInfo.loguj("ZONA 7");
					return LowSkills.get("step_right");
				} else {
//					agentInfo.loguj("ZONA 8");
					return LowSkills.get("step_left");
				}
			}else{
//				agentInfo.loguj("ZONA 6");
				return LowSkills.get("walk_back");
			}
		}
		else {
			if( (lastPickedLowSkill.equals(getLowSkillName()) && HighSkillUtils.isStraight(straight_range3, straight_range4, targetPositionPhi))
					|| (!lastPickedLowSkill.equals(getLowSkillName()) && HighSkillUtils.isStraight(straight_range1, straight_range2, targetPositionPhi)) ){

				lastPickedLowSkill = getLowSkillName();
				pickedDifferentLowSkill = false;
				
				return LowSkills.get(getLowSkillName()); 
				
			}else if (HighSkillUtils.isOnSideAndDistant(right_range, relative_target)) {//right
				return LowSkills.get("turn_right_45");
			}else if (rightAndDistantLess(relative_target)) {
				return LowSkills.get("turn_right_cont_20");
			}else if (rightAndDistantBit(relative_target)) {
				return LowSkills.get("turn_right_cont_20");
			}else if (HighSkillUtils.isOnSideAndDistant(left_range, relative_target)) {//left
				return LowSkills.get("turn_left_45");
			}else if (leftAndDistantLess(relative_target)) {
				return LowSkills.get("turn_left_cont_20");
			}else if (leftAndDistantBit(relative_target)) {
				return LowSkills.get("turn_left_cont_20");
			}else{
				return LowSkills.get("rollback");
			}
		}
	}
	
	protected boolean leftAndDistantBit(Vector3D target2) {
		return left_range3.include(Angles.normalize(relative_target.getPhi()));
	}

	protected boolean leftAndDistantLess(Vector3D target2) {
		return left_range2.include(Angles.normalize(relative_target.getPhi()));
	}

	protected boolean rightAndDistantBit(Vector3D target2) {
		return right_range3.include(Angles.normalize(relative_target.getPhi()));
	}

	protected boolean rightAndDistantLess(Vector3D target2) {
		return right_range2.include(Angles.normalize(relative_target.getPhi()));
	}

	@Override
	public void checkProgress() throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void computeRelativeTarget(){	
		double dynx = original_target.getDynX()+original_target.getXOffset();
		double dyny = original_target.getDynY()+original_target.getYOffset();
		if (original_target.getXDyn()==true && original_target.getYDyn()==true){
			relative_target = agentModel.relativize(Vector3D.cartesian(dynx,dyny, 0) );
			checkBall = true;
			 ReplanWindow.getInstance().getGoingToPositioValue().setText("X:" + round(dynx) + "  Y:" + round(dyny));
				
		}
		else if (original_target.getXDyn()==true){
			relative_target = agentModel.relativize(Vector3D.cartesian(dynx, original_target.getY(), 0) );
			checkBall = true;
			ReplanWindow.getInstance().getGoingToPositioValue().setText("X:" + round(dynx) + "  Y:" + original_target.getY());
			
		}
		else if (original_target.getYDyn()==true){
			relative_target = agentModel.relativize(Vector3D.cartesian(original_target.getX(),dyny, 0) );
			checkBall = true;
			ReplanWindow.getInstance().getGoingToPositioValue().setText("X:" + original_target.getX() + "  Y: " + round(dyny));
			
		}
		else {
			relative_target = agentModel.relativize(Vector3D.cartesian(original_target.getX(),original_target.getY(),0));
			
			//Due to disabled close distance tuning
			if (!checkBall){
				relative_target = Vector3D.cartesian(relative_target.getX(), relative_target.getY() + closeDistance , relative_target.getZ());
			}
			
			ReplanWindow.getInstance().getGoingToPositioValue().setText("X:" + original_target.getX() + "  Y: " + original_target.getY());
		}
		
		//logRelativeTarget();
	}
	public static double round(double value) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	protected void setOriginalTarget(MoveSkillPostionObject moveObj){
			this.original_target = moveObj;
	}
	
	/**
	 * Adjust walk ranges, given the type of walk. Must be called 
	 * after setting the original target with method setOriginalTarget()
	 * @param LowSkillRange
	 */
	protected void adjustRanges(double LowSkillRange){
		if ( ! checkBall){
			straight_range3 = new Angles(0.0, 25.0);
			straight_range4 = new Angles(335, 360.0);
			
			closeDistance = 1.0;
		}
	}
	protected void adjustMinDistance(double LowSkillMinDistance){}

	protected void logRelativeTarget(){
		Vector3D agent_position = agentModel.getPosition();
		
//		AgentInfo.logState(String.format("Walk: relative_target= [ %.2f, %.2f, %.2f] "
//				+ "[phi %.2f, R %.2f, theta %.2f]", 
//				relative_target.getX(), relative_target.getY(), relative_target.getZ(), 
//				relative_target.getPhi(), relative_target.getR(), relative_target.getTheta()));
		
		AgentInfo.logState(String.format("Walk: agent_position= [ %.2f, %.2f, %.2f]", 
				agent_position.getX(), agent_position.getY(), agent_position.getZ()));
	}
	
}
