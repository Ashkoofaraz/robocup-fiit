package sk.fiit.jim.agent.highskill.move;

import java.util.List;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.annotation.data.Annotation;
import sk.fiit.jim.annotation.data.AnnotationManager;

/**
 * 
 * MovementHighSkill.java
 * 
 * This class is wrapper for walk enums {@link MovementEnum}. It contains also util methods for computing suitability
 * of walk enums.
 * 
 * @Title Jim
 * @author Nemecek, Markech
 * @author gitmen
 *
 */

public class MovementSkills {
	
	/**
	 * This enum is <b>final</b> enumeration of all walk movements which agents knows and can perform. If there is  
	 * some new walk skill you must add this skill to this enum.
	 * 
	 * @author Markech, Nemecek, Linner
	 *
	 */
	protected enum MovementEnum {
		WALK_SLOW(new WalkSlow()) {
			@Override
			public double computeSuitability(MoveSkillPostionObject position, MovementSpeedEnum speedEnum) {
				newMovementSkillInstance(new WalkSlow());
				return getWalkSuitability(position, speedEnum);
			}
		},
		WALK_SLOW2(new WalkSlow2()) {
			@Override
			public double computeSuitability(MoveSkillPostionObject position, MovementSpeedEnum speedEnum) {
				newMovementSkillInstance(new WalkSlow2());
				return getWalkSuitability(position, speedEnum);
			}
		},
		WALK_MEDIUM(new WalkMedium()) {
			@Override
			public double computeSuitability(MoveSkillPostionObject position, MovementSpeedEnum speedEnum) {
				newMovementSkillInstance(new WalkMedium());
				return getWalkSuitability(position, speedEnum);
			}
		},
		WALK_FAST(new WalkFast()) {
			@Override
			public double computeSuitability(MoveSkillPostionObject position, MovementSpeedEnum speedEnum) {
				newMovementSkillInstance(new WalkFast());
				return getWalkSuitability(position, speedEnum);
			}

		};

		private String annotationsName;
		private String lowSkillName;
		private Walk walk;

		public String getAnnotationName() {
			return annotationsName;
		}
		
		public String getLowSkillName() {
			return lowSkillName;
		}
		
		public Walk getMovementSkill() {
			return walk;
		}
		
		protected void newMovementSkillInstance(Walk instance){
			walk = instance;
		}

		MovementEnum(Walk walk1) {
			annotationsName = walk1.getAnnotationName();
			lowSkillName = walk1.getLowSkillName();
			walk = walk1;
		}
		
		public abstract double computeSuitability(MoveSkillPostionObject position, MovementSpeedEnum speedEnum);

		/**
		 * Method for computing walk skills suitability for current action. The current computed atributes are
		 * speed, distance to target and stability of skill(how much % 
		 *  from 100runs agents performs the skill without falling).
		 * @param relativeDistanceToTarget
		 * @return skillSuitability
		 */
		protected double getWalkSuitability(MoveSkillPostionObject MoveObj, MovementSpeedEnum speedEnum) {
			// TODO consider relativeDistanceToTarget, consider annotation's min_distance
			List<Annotation> annotationsList = AnnotationManager.getInstance().getAnnotations(getAnnotationName());

			if(annotationsList.size() == 0){
				return 0;
			}

			double speed_koef = 1.0;
			double range_koef = 1.0;
			double success_koef = 1.0;			
			if (speedEnum == MovementSpeedEnum.WALK_SLOW){
				speed_koef = 0.5;
				range_koef = 1.0;
				success_koef = 1.5;
			}
			else if (speedEnum == MovementSpeedEnum.WALK_MEDIUM) {
				speed_koef = 1.0;
				range_koef = 0.8;
				success_koef = 1.2;
			}
			else if (speedEnum == MovementSpeedEnum.WALK_FAST){
				speed_koef = 1.5;
				range_koef = 0.5;
				success_koef = 0.01;
			}
			
			double speed = 0.0, success = 0.0, range = 0.0, min_distance = 0.0;
			
			double current_suitability = 0.0;
			double best_suitability = 0.0;
			double best_range = 0.0;
			double best_min_distance = 0.0;
			for (Annotation annotation : annotationsList) {
				speed = 10 * annotation.getWalkSpeed();
				range = 10 / annotation.getRotation().getZ().getAvg();
				success = 100 - annotation.getFall();
				min_distance = annotation.getMinDistance();
				
				current_suitability = speed*speed_koef + range*range_koef + success*success_koef;
				if (current_suitability > best_suitability){
					best_suitability = current_suitability;
					best_range = range;
					best_min_distance = min_distance;
				}
			}

			this.getMovementSkill().setOriginalTarget(MoveObj);
			this.getMovementSkill().adjustRanges(10 / best_range);
			this.getMovementSkill().adjustMinDistance(best_min_distance);
			
			return best_suitability;
		}
	
	//OLD METHOD
//	protected double getWalkSuitability(double relativeDistanceToTarget, MovementSpeedEnum speedEnum) {
//		// TODO MAKE BETTER COMPUTING OF SUITABILITY!!! OR I"LL KILL YA!!!!
//		// zaujima nas spolahlivost pohybu, rychlost a minimalna vzdialenost
//		// na ktoru sa da pouzit
//		// TREBA ANOTACIE OD CHALANOV
//		List<Annotation> annotationsList = AnnotationManager.getInstance().getAnnotations(getAnnotationName());
//
//		if(annotationsList.size() == 0){
//			return 0;
//		}
//		double annotationMinDistance = 0;
//		double distanceKoeficient = 0;
//		double speed = 0;
//		double stability = 0;
//		for (Annotation annotation : annotationsList) {
//
//			annotationMinDistance = annotation.getMinDistance();
//			distanceKoeficient = (relativeDistanceToTarget - annotationMinDistance) >= 0 ? 1.25 : 1;
//			if(annotationMinDistance != 0){
//				speed = speedEnum.getSpeed();
//			}else{
//				speed = 1 / annotation.getDuration().getAvg();
//			}
//			stability = 1 - annotation.getFall();
//		}
//
//		// TODO vzorec
//		double suitability = distanceKoeficient * speed * stability;
//		return suitability;
//	}
		
	}
}