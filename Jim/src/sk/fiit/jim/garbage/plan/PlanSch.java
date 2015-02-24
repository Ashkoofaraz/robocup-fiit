package sk.fiit.jim.garbage.plan;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.*;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Rewrited PlanSch from ruby
 * 
 * @author Bosiak
 */

public class PlanSch extends Plan {
	public static PlanSch getInstance() {
		return new PlanSch();
	}

	public void replan() {
		AgentModel agentModel = AgentModel.getInstance();
		Vector3D target_position = AgentInfo.getInstance()
				.ballControlPosition();
		if (agentModel.isOnGround() || agentModel.isLyingOnBack()
				|| agentModel.isLyingOnBelly()) {

			AgentInfo.logState("GetUp");
			Highskill_Queue.add(new GetUp());
		} else if (!WorldModel.getInstance().isSeeBall()) {
			AgentInfo.logState("Localize");
			Highskill_Queue.add(new Localize());
		} else {
			AgentInfo.logState("GoToBall");
			Highskill_Queue.add(new GotoBall(target_position));

		}
	}
}
