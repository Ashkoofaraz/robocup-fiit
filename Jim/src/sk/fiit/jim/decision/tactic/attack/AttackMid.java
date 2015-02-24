package sk.fiit.jim.decision.tactic.attack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.kick.KickHighSkill;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.decision.situation.BallAtMid;
import sk.fiit.jim.decision.situation.BallFarFromMe;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.MostEnemyInLeft;
import sk.fiit.jim.decision.situation.MostEnemyInRight;
import sk.fiit.jim.decision.situation.NoEnemy;
import sk.fiit.jim.decision.tactic.Tactic;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * Default tactic implementation
 *
 * @author Matej Badal <matejbadal@gmail.com>, Martin Adamik <mertoniak@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class AttackMid extends BaseAttack {

    private ArrayList<String> prescribedSituations = new ArrayList<String>(
            Arrays.asList(BallAtMid.class.getName(),BallIsOurs.class.getName()));

    public static final int TACTIC_STATE_LEAD_BALL_FAR   = 1;
    public static final int TACTIC_STATE_LEAD_BALL_CLOSE = 2;
    public static final int TACTIC_STATE_SUPPORT         = 3;
    public static final int TACTIC_STATE_RUN_FORWARD     = 4;

    /**
     * Condition which must be true when you start tactic
     *
     * @param currentSituations List of current situations
     * @return boolean
     */


	public boolean getInitCondition(List<String> currentSituations) {
		if (currentSituations.contains(BallIsTheirs.class.getName())) {
			return false;
		}

		if (currentSituations.contains(NoEnemy.class.getName())
				|| currentSituations.contains(MostEnemyInLeft.class.getName())
				|| currentSituations.contains(MostEnemyInRight.class.getName())) {
			return true;
		}

		return false;

    }

    /**
     * Execution body of tactic
     */
    public void run() {

    	DynamicObject ball = WorldModel.getInstance().getBall();
    	Vector3D agent = AgentModel.getInstance().getPosition();
        double ballY = ball.getPosition().getY();
        double meY = agent.getY();
        int PlayerID = AgentInfo.playerId;
        double distance_y = 0;

        switch (currentState) {
            case AttackMid.TACTIC_STATE_LEAD_BALL_FAR:
            	//povodne Vector3D.cartesian(ballX, ballY, 0)
                this.moveExec.move(MovementSpeedEnum.WALK_FAST);

                break;

            case AttackMid.TACTIC_STATE_LEAD_BALL_CLOSE:

            	// povodne Vector3D.cartesian(ballX, ballY, 0)
                this.moveExec.move(MovementSpeedEnum.WALK_SLOW);

                distance_y = ballY-meY;
                distance_y = Math.abs(distance_y);

				if( distance_y<0.5 ) {
                	this.kickExec.kick(Tactic.TheyGoal, KickHighSkill.KickTypeEnum.KICK_STRONG);
                } else if(distance_y<1 ) {
                	this.kickExec.kick(Tactic.TheyGoal, KickHighSkill.KickTypeEnum.KICK_FAST);
                } else if (distance_y<2){
                	this.kickExec.kick(Tactic.TheyGoal, KickHighSkill.KickTypeEnum.KICK_NORMAL);
                }
                else {
                	this.kickExec.kick(Tactic.TheyGoal, KickHighSkill.KickTypeEnum.KICK_SLOW);
                }

                break;

            case AttackMid.TACTIC_STATE_RUN_FORWARD:
                switch (PlayerID) {
                    case 1:
                        this.moveExec.move(ball,6,0, MovementSpeedEnum.WALK_MEDIUM);
                        break;
                    case 2:
            		    this.moveExec.move(ball,2,0, MovementSpeedEnum.WALK_MEDIUM);
                        break;
                    case 3:
                    	this.moveExec.move(ball,-2,0, MovementSpeedEnum.WALK_MEDIUM);
                        break;
                    case 4:
                    	this.moveExec.move(ball,-6,0, MovementSpeedEnum.WALK_MEDIUM);
                        break;
                    default:
                    	this.moveExec.move(ball,agent,0,0, MovementSpeedEnum.WALK_MEDIUM);
                        break;
                }

            	break;

            case AttackMid.TACTIC_STATE_SUPPORT:
            	this.moveExec.move(ball,agent,0,0, MovementSpeedEnum.WALK_MEDIUM);
                break;
            case AttackMid.UNDEFINED_STATE:
            default:
                break;
        }
    }

    @Override
    protected int checkState(List<String> currentSituations) {


        if (currentSituations.contains(BallNearestToMe.class.getName())) {

            if (currentSituations.contains(BallFarFromMe.class.getName())) {
                return AttackMid.TACTIC_STATE_LEAD_BALL_FAR;
            }

            return AttackMid.TACTIC_STATE_LEAD_BALL_CLOSE;
        }
        
        /* lopta je nasa a niesom najblizsie = som iny hrac , nie ten co vedie loptu v utoku*/
        if(currentSituations.contains(BallIsOurs.class.getName()) &&
        		!currentSituations.contains(BallNearestToMe.class.getName())){

        	return AttackMid.TACTIC_STATE_RUN_FORWARD;
        }


        return AttackMid.TACTIC_STATE_SUPPORT;
    }

    @Override
    public List<String> getPrescribedSituations() {
        return this.prescribedSituations;
    }

}
