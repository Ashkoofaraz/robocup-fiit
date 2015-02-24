package sk.fiit.jim.decision.tactic;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.BeamHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.robocup.library.geometry.Vector3D;

public class MatchStarterTactic {

	public static final int KICKOFF_PLAYER_ID = 2;
	public static final int INITIAL_DISTANCE = 1;
	protected MovementHighSkill moveExec = new MovementHighSkill();
	protected BeamHighSkill beamExec = new BeamHighSkill();

	public void runStart() {
		double agentX = AgentModel.getInstance().getPosition().getX();
		if (AgentInfo.playerId == KICKOFF_PLAYER_ID) {
			this.moveExec.move(Vector3D.cartesian(0, 0, 0),
					MovementSpeedEnum.WALK_MEDIUM);
		}
		this.moveExec.move(
				Vector3D.cartesian((agentX + INITIAL_DISTANCE), 0, 0),
				MovementSpeedEnum.WALK_MEDIUM);
	}

	public void runBeam() {
		switch (AgentInfo.playerId) {

		// BEAM PLAYERS WITH ID 1,2 IN LEFT
		case 1:
			this.beamExec.BeamAgent(Vector3D.cartesian(-4, 7, 0.1));
			System.out.println("BEAM - LEFT 7");
			break;
		case 2:
			this.beamExec.BeamAgent(Vector3D.cartesian(-1, 3, 0.1));
			System.out.println("BEAM - LEFT 3");
			break;

		// BEAM IN RIGHT
		case 3:
			this.beamExec.BeamAgent(Vector3D.cartesian(-1, -3, 0.1));
			System.out.println("BEAM - LEFT -3");
			break;
		case 4:
			this.beamExec.BeamAgent(Vector3D.cartesian(-4, -7, 0.1));
			System.out.println("BEAM - LEFT -7");
			break;

		// BEAM IN MID
		default:
			this.beamExec.BeamAgent(Vector3D.cartesian(-8, 0, 0.1));
		}

		System.out.println("Beam");
	}

}
