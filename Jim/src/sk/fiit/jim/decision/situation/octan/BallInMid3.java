package sk.fiit.jim.decision.situation.octan;

import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.Situation;

/**
 * @author Martin Adamik
 * Situation is true if Ball is in middle between 3L and 3R
 *		  
 *		  Enemy
 * ========___========
 * -------------------
 * 		4L	|	4R
 * -------------------
 * 		3L	|	3R
 * -------------------
 * 		2L	|	2R
 * -------------------
 * 		1L	|	1R
 * ========___========
 *         Our
 *         
 * @return boolean - Returns true only if ball is in middle between 3L and 3R
 */


public class BallInMid3 extends Situation {

	public static final double POSITION_MAX_X = 8;
	public static final double POSITION_MIN_X = 0;
	public static final double POSITION_MIN_Y = -3.5;
	public static final double POSITION_MAX_Y = 3.5;
	
	public boolean checkSituation() {

		if ((WorldModel.getInstance().getBall().getPosition().getX() <= BallInMid3.POSITION_MAX_X)
				&& (WorldModel.getInstance().getBall().getPosition().getX() > BallInMid3.POSITION_MIN_X)
				&& (WorldModel.getInstance().getBall().getPosition().getY() < BallInMid3.POSITION_MAX_Y)
				&& (WorldModel.getInstance().getBall().getPosition().getY() > BallInMid3.POSITION_MIN_Y)) {
			return true;
		}
		return false;
	}
}
