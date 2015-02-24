package sk.fiit.jim.decision.tactic.attack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.kick.KickHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.BallAtRight;
import sk.fiit.jim.decision.situation.BallFarFromMe;
import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.BallIsTheirs;
import sk.fiit.jim.decision.situation.BallNearestToMe;
import sk.fiit.jim.decision.situation.MostEnemyInLeft;
import sk.fiit.jim.decision.situation.MostEnemyInMid;
import sk.fiit.jim.decision.situation.NoEnemy;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * @author Michal Petras <michpet@gmail.com>, Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class AttackRight extends BaseAttack {

    private ArrayList<String> prescribedSituations = new ArrayList<String>(
            Arrays.asList(BallAtRight.class.getName(),BallIsOurs.class.getName()));

    public static final int TACTIC_STATE_LEAD_BALL_FAR   = 1;
    public static final int TACTIC_STATE_LEAD_BALL_CLOSE = 2;
    public static final int TACTIC_STATE_SUPPORT         = 3;
    public static final int TACTIC_STATE_RUN_FORWARD	 = 4;


    /*
     * Condition which must be true when you start tactic
     */
    public boolean getInitCondition(List<String> currentSituations) {
		if (currentSituations.contains(BallIsTheirs.class.getName())) {
			return false;
		}

		if (!currentSituations.contains(NoEnemy.class.getName())) {
			if (currentSituations.contains(MostEnemyInLeft.class.getName())
					|| currentSituations.contains(MostEnemyInMid.class
							.getName()))
				return true;
		}

		return false;
	}

    /*
     * Implementation of actual tactic
     */
    public void run() {
    	DynamicObject ball = WorldModel.getInstance().getBall();
    	Vector3D agent = AgentModel.getInstance().getPosition();
        double ballX = ball.getPosition().getX();
        double ballY = ball.getPosition().getY();
        int playerID = AgentInfo.playerId;
        double distance_y = 0;

        switch (currentState) {
            case AttackRight.TACTIC_STATE_LEAD_BALL_FAR:
            	// povodne Vector3D.cartesian(ballX, ballY, 0)
                this.moveExec.move(MovementSpeedEnum.WALK_FAST);
                
                distance_y = ballY / BaseAttack.MICHALS_UBER_SUPER_TROUPER_CONST;
                if(ballY > 2 || ballY < -2) {
                	distance_y = ballY;
                }
                
                // predkopava silno pokial sa nedostane do brankoviska
                if(ballX <= 7.5){
                	this.kickExec.kick(Vector3D.cartesian(ballX+5, distance_y, 0), KickHighSkill.KickTypeEnum.KICK_STRONG);	
                } else {
                	this.kickExec.kick(Vector3D.cartesian(ballX+2, distance_y, 0), KickHighSkill.KickTypeEnum.KICK_NORMAL);
                }
                
                break;

            case AttackRight.TACTIC_STATE_LEAD_BALL_CLOSE:
            	// povodne Vector3D.cartesian(ballX, ballY, 0)
                this.moveExec.move(MovementSpeedEnum.WALK_SLOW);

                distance_y = ballY / BaseAttack.MICHALS_UBER_SUPER_TROUPER_CONST;
                // Smerovanie k branke
                if(ballY > 2 || ballY < -2) {
                	distance_y = ballY;
                }
                
                this.kickExec.kick(Vector3D.cartesian(ballX+1, distance_y, 0), KickHighSkill.KickTypeEnum.KICK_SLOW);
                
                break;
                
            case AttackRight.TACTIC_STATE_RUN_FORWARD:
                switch (playerID) {
                case 1:
                    this.moveExec.move(ball,-1,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 2:
        		    this.moveExec.move(ball,-4,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 3:
                	this.moveExec.move(ball,-6,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                case 4:
                	this.moveExec.move(ball,-9,0, MovementSpeedEnum.WALK_MEDIUM);
                    break;
                default:
                	this.moveExec.move(ball,agent,0,0, MovementSpeedEnum.WALK_MEDIUM);
                	break;
                }
            	break;
            	
            case AttackRight.TACTIC_STATE_SUPPORT:
            	this.moveExec.move(ball,agent,0,0, MovementSpeedEnum.WALK_MEDIUM);
                break;
            case AttackRight.UNDEFINED_STATE:
            default:
                break;
        }


    }

    protected int checkState(List<String> currentSituations) {

        if (currentSituations.contains(BallNearestToMe.class.getName())) {

            if (currentSituations.contains(BallFarFromMe.class.getName())) {
                return AttackRight.TACTIC_STATE_LEAD_BALL_FAR;
            }

            return AttackRight.TACTIC_STATE_LEAD_BALL_CLOSE;
        }
        /* lopta je nasa a niesom najblizsie = som iny hrac , nie ten co vedie loptu v utoku*/
        if(currentSituations.contains(BallIsOurs.class.getName()) && 
        		!currentSituations.contains(BallNearestToMe.class.getName())){
        	
        	return AttackRight.TACTIC_STATE_RUN_FORWARD;
        }


        return AttackRight.TACTIC_STATE_SUPPORT;

    }

    @Override
    public List<String> getPrescribedSituations() {
        return this.prescribedSituations;
    }

}
