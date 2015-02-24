
package sk.fiit.jim.agent.highskill.kick;

import java.util.LinkedList;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.GetUp;
import sk.fiit.jim.agent.highskill.Localize;
import sk.fiit.jim.agent.highskill.kick.KickHighSkill.KickTypeEnum;
import sk.fiit.jim.agent.highskill.runner.HighSkillPlanner;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.jim.agent.skills.HighSkillUtils;
import sk.fiit.jim.annotation.data.Annotation;
import sk.fiit.jim.annotation.data.AnnotationManager;
import sk.fiit.robocup.library.geometry.Angles;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 *
 * @author Inkognito
 */
public class Kick extends HighSkill {

    private KickTypeEnum type = KickTypeEnum.KICK_NORMAL;

    private HighSkillPlanner planner = HighSkillPlanner.getInstance();

    private String activeLowSkill = "kick_faster_left";
    private boolean kicked = false;

    private Vector3D target = Vector3D.ZERO_VECTOR;

    private Annotation annotation;

    protected Angles straight_range1 = new Angles(0.0, 15.0);
    protected Angles straight_range2 = new Angles(345.0, 360.0);

    protected Angles right_range = new Angles(180.0, 325.0);
    protected Angles right_range2 = new Angles(325.0, 340.0);
    protected Angles right_range3 = new Angles(340.0, 346.0);

    protected Angles left_range = new Angles(35.0, 180.0);
    protected Angles left_range2 = new Angles(20.0, 35.0);
    protected Angles left_range3 = new Angles(14.0, 20.0);

    private String walkSkill = "walk_slow";
    
    //Kick to ball.
    public Kick(Vector3D kickTarget, KickTypeEnum preferredType) {
        this.target = kickTarget;
        this.type = preferredType;

        employBestLowSkill();
    }

    //Kick to ball, default action.
    public Kick() {
        employBestLowSkill();
    }

    @Override
    public LowSkill pickLowSkill() {

        AgentInfo agentInfo = AgentInfo.getInstance();
        AgentModel agentModel = AgentModel.getInstance();
        Vector3D ball = WorldModel.getInstance().getBall().getPosition();

        Vector3D relativizedBall = agentModel.relativize(ball);
        AgentInfo.logState("Relativized target " + relativizedBall.toString());
        Vector3D me = agentModel.getPosition();

        if (agentModel.isOnGround()) {
            AgentInfo.logState("Kick - onGround");
            planner.addHighskillAsFirst(new GetUp());
            return LowSkills.get("rollback");	//nebudeme ukoncovat tento, Kick, HighSkill
        } else if (kicked) {
            stopHighSkill();
            AgentInfo.logState("Kick - END");
            return LowSkills.get("rollback");
        } else if (AgentInfo.isballLongUnseen(3)) {
            AgentInfo.logState("Kick - ball unseen");
            planner.addHighskillAsFirst(new Localize());
            return LowSkills.get("rollback");	//nebudeme ukoncovat tento, Kick, HighSkill
        } else if (!isInVerticalRange(relativizedBall)) {
            AgentInfo.logState("Move vertical");

            if (moveForward(relativizedBall)) {
                AgentInfo.logState("Move forward");
                return LowSkills.get(walkSkill);
            } else if (moveBack(relativizedBall)) {
                AgentInfo.logState("Move back");
                return LowSkills.get("walk_back");
            } else {
                return kick();
            }
        } else if (!isInHorizontalRange(relativizedBall)) {
            AgentInfo.logState("Move horizontal");

            if (moveLeft(relativizedBall)) {
                AgentInfo.logState("Move left");

                AgentInfo.logState("Kick - step left");
                return LowSkills.get("step_left_very_small");
            } else if (moveRight(relativizedBall)) {
                AgentInfo.logState("Move right");

                AgentInfo.logState("Kick - step right");
                return LowSkills.get("step_right_very_small");
            } else {
                if (moveForward(relativizedBall)) {
                    AgentInfo.logState("Move forward");

                    return LowSkills.get(walkSkill);
                } else if (moveBack(relativizedBall)) {
                    AgentInfo.logState("Move back");
                    return LowSkills.get("walk_back");
                } else {
                    return kick();
                }
            }
        } 
        
        if (!kicked) {
            return kick();
        }

        AgentInfo.logState("Unkwnown state");
        AgentInfo.logState("Distance from ball" + String.valueOf(ball.getXYDistanceFrom(me)));
        stopHighSkill();
        return LowSkills.get("rollback");
    }

    private LowSkill kick() {
            kicked = true;
            AgentInfo.logState("kick 2");
            AgentInfo.logState(getLowSkillName());
            return LowSkills.get(getLowSkillName());
    }
    
    private boolean isInHorizontalRange(Vector3D relativizedBall) {
        //AgentInfo.logState("Horizontal range");
    	double maxX;
        double minX;
    	if(annotation.getAgentPosition().getMaxY() < 0)	{
    			minX = annotation.getAgentPosition().getMaxY();
    			maxX = annotation.getAgentPosition().getMinY();
        	}
        else {
        		maxX = annotation.getAgentPosition().getMaxY();
        		minX = annotation.getAgentPosition().getMinY();
        	}
    	//double maxX = Math.max(Math.abs(annotation.getAgentPosition().getMaxY()), Math.abs(annotation.getAgentPosition().getMinY()));
        //double minX = Math.min(Math.abs(annotation.getAgentPosition().getMaxY()), Math.abs(annotation.getAgentPosition().getMinY()));

        double bX = relativizedBall.getX();
        
        System.out.println(bX + "  " + minX + "  " + maxX);
        return (bX <= maxX) && (bX >= minX);
    }

    private boolean isInVerticalRange(Vector3D relativizedBall) {
        // AgentInfo.logState("Vertical range")
        double maxY = Math.max(Math.abs(annotation.getAgentPosition().getMaxX()), Math.abs(annotation.getAgentPosition().getMinX()));
        double minY = Math.min(Math.abs(annotation.getAgentPosition().getMaxX()), Math.abs(annotation.getAgentPosition().getMinX()));

        double bY = Math.abs(relativizedBall.getY());
        
        return (bY <= maxY) && (bY >= minY);
    }

    private boolean moveForward(Vector3D relativizedBall) {
        //  AgentInfo.logState("Move forward");
        double maxX = Math.abs(annotation.getAgentPosition().getMaxX());
        double bY = Math.abs(relativizedBall.getY());
        
        AgentInfo.logState("Move forward?" + String.valueOf(maxX) + " " + String.valueOf(bY) + " " + annotation.getName());
        return bY > maxX;
    }

    private boolean moveBack(Vector3D relativizedBall) {
        //  AgentInfo.logState("Move back");
        double minX = Math.abs(annotation.getAgentPosition().getMinX());

        boolean case1 = 0 > relativizedBall.getY();
        boolean case2 = relativizedBall.getY() < minX;
        boolean case3 = case1 || case2;

        //AgentInfo.logState("minX " + String.valueOf(minX) + "rel Y " + String.valueOf(relativizedBall.getY()));
        //AgentInfo.logState("c1 " + String.valueOf(case1) + " c2 " + String.valueOf(case2) + " c3 " + String.valueOf(case3));
        return case3;
    }

    private boolean moveLeft(Vector3D relativizedBall) {
        double minX = annotation.getAgentPosition().getMinY();
        double maxX = annotation.getAgentPosition().getMaxY();

        double bX = relativizedBall.getX();
        
        if (minX < 0)
          return bX < maxX;
        else
            return bX < minX;
    }

    private boolean moveRight(Vector3D relativizedBall) {
        double minX = annotation.getAgentPosition().getMinY();
        double maxX = annotation.getAgentPosition().getMaxY();

        double bX = relativizedBall.getX();
        
        if (minX < 0)
          return bX > minX;
        else
            return bX > maxX;
    }

    @Override
    public void checkProgress() throws Exception {
        // Not supported yet.
    }

    public String getLowSkillName() {
        return activeLowSkill;
    }

    private void employBestLowSkill() {

        AnnotationManager manager = AnnotationManager.getInstance();
        List<Annotation> annotationsList = new LinkedList<Annotation>();

        double speedKoef = 1.0;
        double distanceKoef = 1.0;
        double successKoef = 1.0;
        double speed, success = 0.0;

        Vector3D ball = WorldModel.getInstance().getBall().getPosition();
        Vector3D relativizedTarget = AgentModel.getInstance().relativize(ball);

        // NOTE: pre zakomentovane kopy nefunguje korektne nastavovanie na kop 
        // kedze hrac nevidi loptu
        
        //get only first annotation for move       
        if (relativizedTarget.getX() < 0.0) {
            annotationsList.add(manager.getAnnotations("kick_left_faster").get(0));
           // annotationsList.add(manager.getAnnotations("kick_left_normal").get(0));
           // annotationsList.add(manager.getAnnotations("kick_left_slow").get(0));
            annotationsList.add(manager.getAnnotations("kick_step_strong_left").get(0));
            annotationsList.add(manager.getAnnotations("kick_left_template").get(0));
        } else {
            annotationsList.add(manager.getAnnotations("kick_right_faster").get(0));
          // annotationsList.add(manager.getAnnotations("kick_right_normal").get(0));
          //  annotationsList.add(manager.getAnnotations("kick_right_slow").get(0));
            annotationsList.add(manager.getAnnotations("kick_step_strong_right").get(0));
            annotationsList.add(manager.getAnnotations("kick_right_template").get(0));
        }

        if (type == KickTypeEnum.KICK_FAST) {
            speedKoef = 2.0;
            distanceKoef = 1.0;
            successKoef = 1.0;
        } else if (type == KickTypeEnum.KICK_NORMAL) {
            speedKoef = 1.0;
            distanceKoef = 1.0;
            successKoef = 1.0;
        } else if (type == KickTypeEnum.KICK_SLOW) {
            speedKoef = 1.0;
            distanceKoef = 0.5;
            successKoef = 1.0;
        } else if (type == KickTypeEnum.KICK_STRONG) {
            speedKoef = 1.0;
            distanceKoef = 1.5;
            successKoef = 1.0;
        } else if (type == KickTypeEnum.KICK_UNKNOWN) {
            speedKoef = 1.5;
            distanceKoef = 1.5;
            successKoef = 1.0;
        }

        double currentSuitability = 0.0;
        double bestSuitability = 0.0;
        String bestLowSkill = "";
        if (!annotationsList.isEmpty()) {
            for (Annotation annotation : annotationsList) {

                speed = 10 * annotation.getKickTime();
                success = annotation.getKickSuccessfulness();

                currentSuitability = speed * speedKoef + annotation.getKickDistance() * distanceKoef + success * successKoef + annotation.getKickDeviation().getAvg();

                if (currentSuitability < bestSuitability || bestSuitability == 0.0) {
                    bestSuitability = currentSuitability;
                    bestLowSkill = annotation.getName();
                }
            }
        }

        // NOTE: testing purposes

        // bestLowSkill = "kick_step_strong_right"; 
        //bestLowSkill = "kick_right_normal";
        // bestLowSkill = "kick_right_slow";
        // bestLowSkill = "kick_right_template";
        //bestLowSkill = "kick_right_faster";
        //bestLowSkill = "kick_left_faster";
        this.annotation = manager.getAnnotations(bestLowSkill).get(0);
        System.out.println("--------------" + annotation.getName());
        if (!bestLowSkill.equals("")) {
            AgentInfo.logState("Kick - the most suitable kick: " + bestLowSkill);
            activeLowSkill = bestLowSkill;
        }
    }
}
