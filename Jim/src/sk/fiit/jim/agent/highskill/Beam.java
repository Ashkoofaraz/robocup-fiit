package sk.fiit.jim.agent.highskill;

import sk.fiit.jim.agent.communication.Communication;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 *
 * Reimplemented from Ruby to Java by roman moravcik
 */

public class Beam extends HighSkill {

    private Vector3D position;
    private boolean isRunning;
    private AgentModel agentModel = AgentModel.getInstance();  

    public Beam(Vector3D position) {
    	super();
    	this.isRunning = false;
    	this.position = position;
    	agentModel.setPosition(position);
    }

    @Override
    public LowSkill pickLowSkill() {    	
        /*
    	if (!isRunning) {
        	//LowSkill currentSkill = LowSkills.get("rollback");
        	this.isRunning = true;
            return currentSkill;
        } 
*/
        double yPos = position.getY();
        double xPos = position.getX();

        Communication.getInstance().addToMessage("(beam " + Double.toString(xPos) + " " + Double.toString(yPos) + " 0.0)");
        //     ypos = 1.0 + Java::sk.fiit.jim.agent.AgentInfo.playerId;
        //     Java::sk.fiit.jim.agent.communication.Communication.instance.addToMessage "(beam -5.0 " + ypos.to_s + " 0.0)"
        return null;
    }

    @Override
    public void checkProgress() {
    }
}
