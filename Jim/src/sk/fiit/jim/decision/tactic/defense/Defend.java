package sk.fiit.jim.decision.tactic.defense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.BallCloseToMe;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallIn3L;
import sk.fiit.jim.decision.situation.octan.BallIn3R;
import sk.fiit.jim.decision.situation.octan.BallIn4L;
import sk.fiit.jim.decision.situation.octan.BallIn4R;
import sk.fiit.jim.decision.situation.octan.EnemyIn2L;
import sk.fiit.jim.decision.situation.octan.EnemyIn2R;
import sk.fiit.jim.decision.situation.octan.EnemyIn3L;
import sk.fiit.jim.decision.situation.octan.EnemyIn3R;
import sk.fiit.jim.decision.tactic.Tactic;
import sk.fiit.jim.enums.PlayGround;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Calm defensive (not matej's attitude)
 * 
 * @author Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class Defend extends BaseDefend {

	public static final int TACTIC_STATE_THEIR_HALF_NEAREST = 1;
	public static final int TACTIC_STATE_THEIR_HALF = 2;
	public static final int TACTIC_STATE_OUR_HALF_NEAREST = 3;
	public static final int TACTIC_STATE_OUR_HALF = 4;
	public static final int TACTIC_STATE_GO_FOR_IT = 5;

	public static double AGENT_OFFSET_TO_BALL = 0.5;
	public static double AGENT_OFFSET_TO_BALL_NEAREST = 0.5;
	public static double AGENT_OFFSET_TO_BALL_X = 1;

	private ArrayList<String> prescribedSituations = new ArrayList<String>(
			Arrays.asList(
					BallIsTheirs.class.getName(),
					// BALL IN OUR HALF
					BallIn2L.class.getName(), BallIn2R.class.getName(),
					BallIn3L.class.getName(), BallIn3R.class.getName(),
					// ENEMY NEAR TO HALF
					EnemyIn2L.class.getName(), EnemyIn2R.class.getName(),
					EnemyIn3L.class.getName(), EnemyIn3R.class.getName()));

	@Override
	public boolean getInitCondition(List<String> currentSituations) {
		boolean isStart = false;
		/* 1 OPTION ::: We don't have ball */
		if (currentSituations.contains(BallIsTheirs.class.getName())) {
			return true;
		}
		return isStart;
	}

	@Override
	public void run() {
		DynamicObject ball = WorldModel.getInstance().getBall();
		Vector3D agent = AgentModel.getInstance().getPosition();
		double agentY = agent.getY();
		double ballY = ball.getPosition().getY();
		switch (this.currentState) {
		case TACTIC_STATE_THEIR_HALF_NEAREST:
			this.moveExec.move(ball, ball, -AGENT_OFFSET_TO_BALL_NEAREST, 0,
					MovementSpeedEnum.WALK_MEDIUM);
			break;
		case TACTIC_STATE_THEIR_HALF:
			this.moveExec.move(ball, agent, -AGENT_OFFSET_TO_BALL_X, 0,
					MovementSpeedEnum.WALK_MEDIUM);
			break;
		case TACTIC_STATE_OUR_HALF_NEAREST:
			this.moveExec.move(ball, ball, -AGENT_OFFSET_TO_BALL_NEAREST, 0,
					MovementSpeedEnum.WALK_MEDIUM);
			break;
		case TACTIC_STATE_OUR_HALF:
			this.moveExec.move(ball, this.getDestinationY(agentY, ballY),
					-AGENT_OFFSET_TO_BALL_X, MovementSpeedEnum.WALK_MEDIUM);
			break;
		case TACTIC_STATE_GO_FOR_IT:
			this.moveExec.move(MovementSpeedEnum.WALK_FAST);
			break;
		default:
			AgentInfo.logState("Undefined state");
		}
	}

	@Override
	public List<String> getPrescribedSituations() {
		return this.prescribedSituations;
	}

	protected double getDestinationY(double agentY, double ballY) {
		if (ballY == agentY) {
			return ballY;
		}
		double positionY = (agentY + (agentY > ballY ? (-Defend.AGENT_OFFSET_TO_BALL)
				: Defend.AGENT_OFFSET_TO_BALL));
		return (positionY == ballY ? agentY : positionY);
	}

	@Override
	protected int checkState(List<String> currentSituations) {

		// BALL IS NEAREST TO ME OR CLOSE TO ME
		if (currentSituations.contains(BallCloseToMe.class.getName())
				|| currentSituations.contains(BallNearestToMe.class.getName())) {
			return Defend.TACTIC_STATE_GO_FOR_IT;
		}

		// IF BALL IS IN THEIR HALF
		if (currentSituations.contains(BallIn4R.class.getName())
				|| currentSituations.contains(BallIn4L.class.getName())
				|| currentSituations.contains(BallIn3R.class.getName())
				|| currentSituations.contains(BallIn3L.class.getName())) {
			return getTheirHalfState();
		}

		// IF BALL IS IN 2nd LINE
		if (currentSituations.contains(BallIn2R.class.getName())
				|| currentSituations.contains(BallIn2L.class.getName())) {
			return getOurHalfState();
		}
		return Tactic.UNDEFINED_STATE;
	}

	private int getTheirHalfState() {
		int playerId = AgentInfo.playerId;
		double ballY = WorldModel.getInstance().getBall().getPosition().getY();

		switch (playerId) {
		case 1:
			if (ballY <= (PlayGround.BASE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_THEIR_HALF_NEAREST;
			}
		case 2:
			if (ballY > (PlayGround.BASE_Y - PlayGround.SIZE_Y / 4)
					&& ballY <= PlayGround.BASE_Y) {
				return TACTIC_STATE_THEIR_HALF_NEAREST;
			}
		case 3:
			if (ballY > PlayGround.BASE_Y
					&& ballY <= (PlayGround.SIZE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_THEIR_HALF_NEAREST;
			}
		case 4:
			if (ballY > (PlayGround.SIZE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_THEIR_HALF_NEAREST;
			}
		}
		return TACTIC_STATE_THEIR_HALF;
	}

	private int getOurHalfState() {
		int playerId = AgentInfo.playerId;
		double ballY = WorldModel.getInstance().getBall().getPosition().getY();

		switch (playerId) {
		case 1:
			if (ballY <= (PlayGround.BASE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_OUR_HALF_NEAREST;
			}
		case 2:
			if (ballY > (PlayGround.BASE_Y - PlayGround.SIZE_Y / 4)
					&& ballY <= PlayGround.BASE_Y) {
				return TACTIC_STATE_OUR_HALF_NEAREST;
			}
		case 3:
			if (ballY > PlayGround.BASE_Y
					&& ballY <= (PlayGround.SIZE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_OUR_HALF_NEAREST;
			}
		case 4:
			if (ballY > (PlayGround.SIZE_Y - PlayGround.SIZE_Y / 4)) {
				return TACTIC_STATE_OUR_HALF_NEAREST;
			}
		}
		return TACTIC_STATE_OUR_HALF;
	}

}
