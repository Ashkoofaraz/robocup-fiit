package sk.fiit.jim.decision.situation;

public abstract class Situation {

    public static final double EDGE_DISTANCE_FROM_BALL = 2;
	
	/**
	 * Checks if situation is happening
	 * @return boolean
	 */
	public abstract boolean checkSituation();
	
}