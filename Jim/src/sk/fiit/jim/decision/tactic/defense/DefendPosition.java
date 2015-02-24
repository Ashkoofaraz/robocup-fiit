package sk.fiit.jim.decision.tactic.defense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.BallAndMeSameQuadrantTheirHalf;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.octan.BallIn3L;
import sk.fiit.jim.decision.situation.octan.BallIn3R;
import sk.fiit.jim.decision.situation.octan.BallIn4L;
import sk.fiit.jim.decision.situation.octan.BallIn4R;
import sk.fiit.jim.decision.situation.octan.BallInMid3;
import sk.fiit.jim.decision.situation.octan.BallInMid4;
import sk.fiit.jim.decision.situation.octan.EnemyIn3L;
import sk.fiit.jim.decision.situation.octan.EnemyIn3R;
import sk.fiit.jim.decision.situation.octan.EnemyIn4L;
import sk.fiit.jim.decision.situation.octan.EnemyIn4R;
import sk.fiit.jim.decision.tactic.Tactic;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Psition defend tactic in their half
 * 
 * @author  Michal Ceresnak <micoken@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public class DefendPosition extends BaseDefend{
	
	public static final int TACTIC_STATE_ME_AND_BALL_IN_SAME_QUADRANT_THEIR_HALF = 1;
	public static final int TACTIC_STATE_RUN_BACKWARD = 2;
	
	private ArrayList<String> prescribedSituations = new ArrayList<String>(
			Arrays.asList(BallIsTheirs.class.getName(),
					// BALL IN THEIR HALF
					BallIn3L.class.getName(), BallIn3R.class.getName(),
					BallIn4L.class.getName(), BallIn4R.class.getName(),
					BallInMid3.class.getName(), BallInMid4.class.getName(),
					// ENEMY IN THEIR HALF
					EnemyIn3L.class.getName(), EnemyIn3R.class.getName(),
					EnemyIn4L.class.getName(), EnemyIn4R.class.getName()));

	@Override
	public boolean getInitCondition(List<String> currentSituations) {
		boolean isStart = false;
		/* 1 OPTION ::: We don't have ball */
		if (currentSituations.contains(BallIsTheirs.class.getName())
				//&& currentSituations.contains(BallAtTheirHalf.class.getName())
				) 
				{
			return true;
		}
		return isStart;
	}

	@Override
	public void run() {
		DynamicObject ball = WorldModel.getInstance().getBall();
		Vector3D agent = AgentModel.getInstance().getPosition();
		double ballX = ball.getPosition().getX();
		double ballY = ball.getPosition().getY();
        int playerID = AgentInfo.playerId;
		
		switch(currentState) {
		// "DefendPosition - Enemy has ball in same quadrant as me on their half"
		case DefendPosition.TACTIC_STATE_ME_AND_BALL_IN_SAME_QUADRANT_THEIR_HALF:
			this.moveExec.move(Vector3D.cartesian(ballX-2, ballY, 0), MovementSpeedEnum.WALK_MEDIUM);
		case DefendAgressive.TACTIC_STATE_RUN_BACKWARD:
            switch (playerID) {
                case 1:
                	this.moveExec.move(ball,4,-1, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 2:
                	this.moveExec.move(ball,1,-1, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 3:
                	this.moveExec.move(ball,-1,-1, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 4:
                	this.moveExec.move(ball,-4,-1, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                default:
                	this.moveExec.move(ball,agent,0,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
            }
        	break;
		default:
			AgentInfo.logState("Undefined state");
		}
	}

	@Override
	public List<String> getPrescribedSituations() {
		return prescribedSituations;
	}

	@Override
	protected int checkState(List<String> currentSituations) {
			// BALL AND ME - SAME QUADRANT
			if (currentSituations.contains(BallAndMeSameQuadrantTheirHalf.class.getName())) {
				return DefendPosition.TACTIC_STATE_ME_AND_BALL_IN_SAME_QUADRANT_THEIR_HALF;
			}
			// I AM NOT CLOSEST TO BALL
			if(currentSituations.contains(BallIsTheirs.class.getName()) && 
					currentSituations.contains(BallAndMeSameQuadrantTheirHalf.class.getName()) &&
	        		!currentSituations.contains(BallNearestToMe.class.getName())){
	        	
	        	return DefendAgressive.TACTIC_STATE_RUN_BACKWARD;
	        }
			return Tactic.UNDEFINED_STATE;	
	}
}
