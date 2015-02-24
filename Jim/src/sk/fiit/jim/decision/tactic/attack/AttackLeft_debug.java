package sk.fiit.jim.decision.tactic.attack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.highskill.runner.HighSkillPlanner;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.*;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * @author Samuel Benkovic <sppred@gmail.com>, Matej Badal
 *         <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class AttackLeft_debug extends BaseAttack {

    private             ArrayList<String> prescribedSituations            = new ArrayList<String>(
            Arrays.asList(BallIsTheirs.class.getName(), BallAtLeft.class.getName()));
    // state of AttackLeft
    public static final int               TACTIC_STATE_I_AM_NEAREST_FAR   = 1;
    public static final int               TACTIC_STATE_I_AM_NEAREST_CLOSE = 2;
    public static final int               TACTIC_STATE_SUPPORT            = 3;

    private boolean moved = false;
    private boolean moved2 = false;
    private boolean moved3 = false;
    private boolean moved4 = false;
    
    /*
     * Condition which must be true when you start tactic
     */
    public boolean getInitCondition(List<String> currentSituations) {
        if (currentSituations.contains(BallIsTheirs.class.getName())) {
            return false;
        }

        if (currentSituations.contains(BallAtLeft.class.getName())) {
            return true;
        }

        return false;
    }

    /*
     * Implementation of actual tactic run will be executed when state of the
     * tactic was changed.
     */
    public void run() {
        double ballX = WorldModel.getInstance().getBall().getPosition().getX();
        double ballY = WorldModel.getInstance().getBall().getPosition().getY();
        double MeX = AgentModel.getInstance().getPosition().getX();
        double MeY = AgentModel.getInstance().getPosition().getY();

//        switch (currentState) {
//            case AttackLeft_debug.TACTIC_STATE_I_AM_NEAREST_FAR:
//                this.moveExec.move(Vector3D.cartesian(ballX, ballY, 0),
//                                   MovementSpeedEnum.WALK_FAST);
//                AgentInfo.logState("AttackLeft - I AM NEAREST FAR ");
//                break;
//            case AttackLeft_debug.TACTIC_STATE_I_AM_NEAREST_CLOSE:
//                this.moveExec.move(Vector3D.cartesian(ballX, ballY, 0),
//                                   MovementSpeedEnum.WALK_SLOW);
//                AgentInfo.logState("AttackLeft - I AM NEAREST CLOSE");
//                break;
//            case AttackLeft_debug.TACTIC_STATE_SUPPORT:
//                this.moveExec.move(Vector3D.cartesian(ballX, MeY, 0),
//                                   MovementSpeedEnum.WALK_SLOW);
//                AgentInfo.logState("AttackLeft - I AM SUPPORTING ");
//                break;
//
//            default:
//                AgentInfo.logState("AttackLeft - Undefinded state");
//                break;
//        }
        
        HighSkillPlanner planner = HighSkillRunner.getPlanner();
        
        if (!moved){
	        this.moveExec.move(Vector3D.cartesian(5, -6, 0),MovementSpeedEnum.WALK_FAST);
	        AgentInfo.logState("AttackLeft_debug - MOVE ");
	        moved = true;
        }
        else if (moved && !moved2 && planner.getNumberOfPlannedHighSkills() == 0){
        	this.moveExec.move(Vector3D.cartesian(10, 2, 0),MovementSpeedEnum.WALK_FAST);
	        AgentInfo.logState("AttackLeft_debug - MOVE2 ");
	        moved2 = true;
        }
        else if (moved2 && !moved3 && planner.getNumberOfPlannedHighSkills() == 0){
        	this.moveExec.move(Vector3D.cartesian(12, 0, 0),MovementSpeedEnum.WALK_FAST);
	        AgentInfo.logState("AttackLeft_debug - MOVE3 ");
	        moved3 = true;
        }
        else if (moved3 && !moved4 && planner.getNumberOfPlannedHighSkills() == 0){
        	this.moveExec.move(Vector3D.cartesian(15, 0, 0),MovementSpeedEnum.WALK_FAST);
	        AgentInfo.logState("AttackLeft_debug - MOVE4 ");
	        moved4 = true;
        }
        else if (moved4 && planner.getNumberOfPlannedHighSkills() == 0){
	        AgentInfo.logState("AttackLeft_debug - END");
        }
    }

    /*
     * ChackState = method that check if currentstate is actual if not it will
     * change the state and call method run
     */
    @Override
    protected int checkState(List<String> currentSituations) {
        return AttackLeft_debug.TACTIC_STATE_I_AM_NEAREST_FAR;
    }

    @Override
    public List<String> getPrescribedSituations() {
        return this.prescribedSituations;
    }

}
