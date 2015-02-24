package sk.fiit.jim.agent.highskill.move;

import sk.fiit.jim.agent.highskill.GetUp;
import sk.fiit.jim.agent.highskill.Localize;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.skills.HighSkillUtils;
import sk.fiit.robocup.library.geometry.Angles;

/**
 * 
 * WalkFast.java
 * 
 * Specific implementation of fast speed walk.( Author of low skill is P. Passak)
 * 
 * @Title Jim
 * @author Nemecek, Markech, Linner
 * @author gitmen
 *
 */
public class WalkFast extends Walk {	
	public String name = "WalkFast";
	
	protected  WalkFast(){		
	}
	
	@Override
	protected String getAnnotationName() {
		return "walk_turbo";
	}
	
	@Override
	public String getLowSkillName() {
		return "walk_turbo";
	}
	
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
				return LowSkills.get("rollback");	//nebudeme ukoncovat tento, Walk, HighSkill
			} else if (checkBall && worldModel.getBall().notSeenLongTime() > 5) {
				planner.addHighskillAsFirst(new Localize());
				return LowSkills.get("rollback");	//nebudeme ukoncovat tento, Walk, HighSkill
			} else if ( (EnvironmentModel.SIMULATION_TIME - agentModel.getLastTimeFlagSeen()) > MAX_LAST_TIME_FLAG_SEEN){
				return LowSkills.get("turn_right_45");
			} else if (relative_target.getR() < closeDistance) {
				
				if ( ! checkBall){
					//ak sa jedna o chodzu na urcitu poziciu, vypocitana relativna pozicia
					//je velmi zasumena, preto nema zmysel nastavovat sa presne na ciel
					
					return null;
				}
				
//				agentInfo.loguj("som pri lopte ");
				if (relative_target.getY() > y1) {
					if (relative_target.getX() < -x2) {
//						agentInfo.loguj("ZONA 3");
						return LowSkills.get("step_left");
					} else if (relative_target.getX() > x2) {
//						agentInfo.loguj("ZONA 2");
						return LowSkills.get("step_right");
					} else {
//						agentInfo.loguj("ZONA 1");
						return LowSkills.get("walk_slow");
					}
				} else if (relative_target.getY() > y2) {
					if (relative_target.getX() < -x2) {
//						agentInfo.loguj("ZONA 3");
						return LowSkills.get("step_left");
					} else if (relative_target.getX() > x2) {
//						agentInfo.loguj("ZONA 2");
						return LowSkills.get("step_right");
					
					//final position
					} else {
						
						return null;	
					}
				}else if (relative_target.getY() < 0) {
					if (relative_target.getX() < -x2) {
//						agentInfo.loguj("ZONA 4");
						return LowSkills.get("walk_back");
					} else if (relative_target.getX() > x2) {
//						agentInfo.loguj("ZONA 5");
						return LowSkills.get("walk_back");
					}else if (relative_target.getX() > 0) {
//						agentInfo.loguj("ZONA 7");
						return LowSkills.get("step_right");
					} else {
//						agentInfo.loguj("ZONA 8");
						return LowSkills.get("step_left");
					}
				}else{
//					agentInfo.loguj("ZONA 6");
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

		protected void adjustRanges(double LowSkillRange){
			if (checkBall){
				straight_range1 = new Angles(0.0, 5.0);
				straight_range2 = new Angles(355.0, 360.0);
				
				straight_range3 = new Angles(0.0, LowSkillRange * 3);
				straight_range4 = new Angles(360 - LowSkillRange * 3, 360.0);
				
				right_range3 = new Angles(340.0, 356.0);
				left_range3 = new Angles(4.0, 20.0);
			}
			else {
				straight_range3 = new Angles(0.0, LowSkillRange * 4);
				straight_range4 = new Angles(360 - LowSkillRange * 4, 360.0);
				
				closeDistance = 1.0;
			}
		}
		
		protected void adjustMinDistance(double LowSkillMinDistance){
			minDistance = LowSkillMinDistance;
		}
}
