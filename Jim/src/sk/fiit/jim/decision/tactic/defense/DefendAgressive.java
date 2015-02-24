package sk.fiit.jim.decision.tactic.defense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.kick.KickHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.BallAndMeSameQuadrantOurHalf;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.EnemyInFrontOfMe;
import sk.fiit.jim.decision.situation.octan.BallIn1L;
import sk.fiit.jim.decision.situation.octan.BallIn1R;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallInMid1;
import sk.fiit.jim.decision.situation.octan.BallInMid2;
import sk.fiit.jim.decision.situation.octan.EnemyIn1L;
import sk.fiit.jim.decision.situation.octan.EnemyIn1R;
import sk.fiit.jim.decision.situation.octan.EnemyIn2L;
import sk.fiit.jim.decision.situation.octan.EnemyIn2R;
import sk.fiit.jim.decision.situation.octan.MeIn1L;
import sk.fiit.jim.decision.situation.octan.MeIn1R;
import sk.fiit.jim.decision.situation.octan.MeIn2L;
import sk.fiit.jim.decision.situation.octan.MeIn2R;
import sk.fiit.jim.decision.situation.octan.MeInMid1;
import sk.fiit.jim.decision.situation.octan.MeInMid2;
import sk.fiit.jim.decision.tactic.Tactic;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Agressive defend tactic in our half
 * 
 * @author Michal Ceresnak <micoken@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class DefendAgressive extends BaseDefend {
	
	public static final int TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_1 = 1;
	public static final int TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_2 = 2;
	public static final int TACTIC_STATE_RUN_BACKWARD = 3;
	

	private ArrayList<String> prescribedSituations = new ArrayList<String>(
			Arrays.asList(
					BallIsTheirs.class.getName(),
					EnemyInFrontOfMe.class.getName(),
					// BALL IN OUR HALF
					BallIn1L.class.getName(), BallIn1R.class.getName(),
					BallIn2L.class.getName(), BallIn2R.class.getName(),
					BallInMid1.class.getName(), BallInMid2.class.getName(),
					// ENEMY IN OUR HALF
					EnemyIn2L.class.getName(), EnemyIn2R.class.getName(),
					EnemyIn1L.class.getName(), EnemyIn1R.class.getName()));

	@Override
	public boolean getInitCondition(List<String> currentSituations) {
		boolean isStart = false;
		/* 1 OPTION ::: Enemy ball in front of me with ball */
		if (currentSituations.contains(BallIsTheirs.class.getName())
				&& currentSituations.contains(EnemyInFrontOfMe.class.getName())) {
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

		switch (currentState) {
		// "DefendAgressive - Enemy has ball in 1L or 1R or 1Mid";
		case DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_1:
			this.moveExec.move(MovementSpeedEnum.WALK_FAST);
			this.kickExec.kick(Vector3D.cartesian(ballX+5, ballY, 0), KickHighSkill.KickTypeEnum.KICK_STRONG);
		// "DefendAgressive - Enemy has ball in 2L or 2R or 2Mid")
		case DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_2:
			this.moveExec.move(MovementSpeedEnum.WALK_FAST);
			this.kickExec.kick(Vector3D.cartesian(ballX+2, ballY, 0), KickHighSkill.KickTypeEnum.KICK_NORMAL);
		case DefendAgressive.TACTIC_STATE_RUN_BACKWARD:
            switch (playerID) {
                case 1:
                	this.moveExec.move(ball,4,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 2:
                	this.moveExec.move(ball,1,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 3:
                	this.moveExec.move(ball,-1,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 4:
                	this.moveExec.move(ball,-4,0, MovementSpeedEnum.WALK_MEDIUM);
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
		return this.prescribedSituations;
	}

	@Override
	protected int checkState(List<String> currentSituations) {
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - POWER --
		if (currentSituations.contains(MeIn1L.class.getName())
				&& currentSituations.contains(BallIn1L.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_1;
		}
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - POWER --
		if (currentSituations.contains(MeIn1R.class.getName())
				&& currentSituations.contains(BallIn1R.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_1;
		}
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - POWER --
		if (currentSituations.contains(MeInMid1.class.getName())
				&& currentSituations.contains(BallInMid1.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_1;
		}
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - NORMAL --
		if (currentSituations.contains(MeIn2L.class.getName())
				&& currentSituations.contains(BallIn2L.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_2;
		}
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - NORMAL --
		if (currentSituations.contains(MeIn2R.class.getName())
				&& currentSituations.contains(BallIn2R.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_2;
		}
		// BALL AND ME - SAME QUADRANT - MOVE AND KICK - NORMAL --
		if (currentSituations.contains(MeInMid2.class.getName())
				&& currentSituations.contains(BallInMid2.class.getName())) {
			return DefendAgressive.TACTIC_STATE_ME_AND_BALL_IN_QUADRANT_2;
		}
		
		// I AM NOT CLOSEST TO BALL
		if(currentSituations.contains(BallIsTheirs.class.getName()) && 
				currentSituations.contains(BallAndMeSameQuadrantOurHalf.class.getName()) &&
        		!currentSituations.contains(BallNearestToMe.class.getName())){
        	
        	return DefendAgressive.TACTIC_STATE_RUN_BACKWARD;
        }
		return Tactic.UNDEFINED_STATE;
	}
}
