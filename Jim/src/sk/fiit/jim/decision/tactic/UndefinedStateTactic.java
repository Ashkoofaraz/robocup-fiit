package sk.fiit.jim.decision.tactic;

import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.EnemyInFrontOfMe;
import sk.fiit.jim.decision.tactic.defense.Defend;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Undefined StateTactic is implemented for undefined state in all tactics because our robot isn't dummy
 *
 * @author Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class UndefinedStateTactic {

	public static double AGENT_OFFSET_TO_BALL_X = 0.5;
	
	protected MovementHighSkill moveExec = new MovementHighSkill();
	
	public void run(List<String> currentSituations) {
		double ballY = WorldModel.getInstance().getBall().getPosition().getY();
		double ballX = WorldModel.getInstance().getBall().getPosition().getX();
		double agentY = AgentModel.getInstance().getPosition().getY();
		AgentInfo.logState("UNDEFINED STATE EXECUTION");
		
		if(currentSituations.contains(BallIsOurs.class.getName())) {
			this.moveExec.move(
					Vector3D.cartesian((ballX+AGENT_OFFSET_TO_BALL_X),
							this.getDestinationY(agentY, ballY), 0),
					MovementSpeedEnum.WALK_MEDIUM);
		} else {
			if(currentSituations.contains(BallNearestToMe.class.getName())) {
				if(currentSituations.contains(EnemyInFrontOfMe.class.getName())) {
					this.moveExec.move(
							Vector3D.cartesian(ballX,
									ballY, 0),
							MovementSpeedEnum.WALK_FAST);
				} else {
					this.moveExec.move(
							Vector3D.cartesian(ballX,
									ballY, 0),
							MovementSpeedEnum.WALK_MEDIUM);
				}
			} else {
				this.moveExec.move(
						Vector3D.cartesian((ballX-AGENT_OFFSET_TO_BALL_X),
								this.getDestinationY(agentY, ballY), 0),
						MovementSpeedEnum.WALK_MEDIUM);
			}
		}
	}
	
	protected double getDestinationY(double agentY, double ballY) {
		if (ballY == agentY) {
			return ballY;
		}
		double positionY = (agentY + (agentY > ballY ? (-Defend.AGENT_OFFSET_TO_BALL)
				: Defend.AGENT_OFFSET_TO_BALL));
		return (positionY == ballY ? agentY : positionY);
	}

}
