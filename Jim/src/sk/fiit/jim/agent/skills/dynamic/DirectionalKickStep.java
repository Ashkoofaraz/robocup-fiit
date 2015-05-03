package sk.fiit.jim.agent.skills.dynamic;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3D;

public class DirectionalKickStep extends DynamicMove {

    private static final double DIST_STEP_VERY_SMALL = 0.012;
    
    private static final double DIST_STEP_SMALL = 0.0514;
    
    private static boolean kicked = false;
    
    private static boolean stepped = false;
    
    private static int steps = 0;
    
    private static int maxSteps = 0;
    
    private static String step;
    
    private static int maxSmallSteps = 0;
    private static int maxVerySmallSteps = 0;
    
    private static int smallSteps = 0;
    private static int verySmallSteps = 0;
    
    private static String smallStep;
    private static String verySmallStep;
    
    public DirectionalKickStep(double angle)
    {
        init2(angle);
    }
    
    public DirectionalKickStep(Point3D endpoint)
    {
        init2(getAlpha(endpoint));
    }
    
    public DirectionalKickStep(Vector3D vector)
    {
        init2(getAlpha(vector));
    }
    
    public DirectionalKickStep(Point3D ballStart, Point3D ballEnd)
    {
        init2(getAlpha(ballStart, ballEnd));
    }
    
    private static void init(double alpha)
    {
        steps = 0;
        double playerShiftFromBall = calculatePlayerShiftLinear(alpha);
        System.out.println("shift: " + playerShiftFromBall);
        double diff = playerShiftFromBall - AgentModel.getInstance().getPosition().getY();
        maxSteps = (int) (Math.abs(diff)/DIST_STEP_VERY_SMALL);
        if(diff > 0)
        {
            step = "step_left_very_small";
        }
        else
        {
            step = "step_right_very_small";
        }
    }
    
    private static void init2(double alpha)
    {
        steps = 0;
        double playerShiftFromBall = calculatePlayerShiftLinear(alpha);
        System.out.println("shift: " + playerShiftFromBall);
        double diff = playerShiftFromBall - AgentModel.getInstance().getPosition().getY();
        
        if(diff > 0)
        {
            verySmallStep = "step_left_very_small";
            smallStep = "step_left_small";
        }
        else
        {
            verySmallStep = "step_right_very_small";
            smallStep = "step_right_small";
        }
        
        maxSmallSteps = (int) (Math.abs(diff)/DIST_STEP_SMALL);
        double smallStepsDistance = maxSmallSteps * DIST_STEP_SMALL;
        
        diff = diff- smallStepsDistance;
        maxVerySmallSteps = (int) (Math.abs(diff)/DIST_STEP_VERY_SMALL);
        
    }
    
	@Override
	public LowSkill pickLowSkill() {
//	    System.out.println("torso y: " + AgentModel.getInstance().getBodyPartAbsPositions().get(BodyPart.TORSO));
//	    System.out.println("foot y: " + AgentModel.getInstance().getBodyPartAbsPositions().get(BodyPart.LFOOT));
//	    if(!stepped && steps < maxSteps) {
//	        steps++;
//	        return LowSkills.get(step);
//	    }
//	    stepped = true;
//	    if(!kicked) {
//	        kicked = true;
//            return LowSkills.get("kick_left_normal_stand");
//        }
//        return null;
        
        if(!stepped && smallSteps < maxSmallSteps) {
            smallSteps++;
            return LowSkills.get(smallStep);
        }
        if(!stepped && verySmallSteps < maxVerySmallSteps) {
            verySmallSteps++;
            return LowSkills.get(verySmallStep);
        }
        
        stepped = true;
        if(stepped && !kicked) {
            kicked = true;
            return LowSkills.get("kick_left_normal_stand");
        }
        return null;
	}

	@Override
	public void checkProgress() throws Exception {
	}
	
	private static double calculatePlayerShift(double alpha)
    {
        double shift = -2.798259731647e-7 * Math.pow(alpha, 4) + 1.48547703927838e-6 * Math.pow(alpha, 3)
                + 0.0002393458 * Math.pow(alpha, 2) - 0.0037578029 * alpha - 0.0854023051;
        return shift;
    }
    
    private static double calculatePlayerShiftLinear(double alpha)
    {
        if(alpha > 24.6)
        {
            return -0.11;
        }
        if(alpha < -25.9)
        {
            return 0.2;
        }
        if(alpha >= -25.9 && alpha <= 7)
        {
            return -0.0015539554 * alpha - 0.018402892;
        }
        if(alpha <= 24.6 && alpha > 7)
        {
            return -0.001020809*alpha - 0.0849254591;
        }
        return 0;
    }
	
	private static double getAlpha(Point3D point)
    {
        return Math.toDegrees(Math.atan2(point.y, point.x));
    }
    
    private static double getAlpha(Vector3D vector)
    {
        return Math.toDegrees(Math.atan2(vector.getY(), vector.getX()));
    }
    
    private static double getAlpha(Point3D start, Point3D end)
    {
        double diffX = end.x - start.x;
        double diffY = end.y - start.y;
        double alpha = Math.toDegrees(Math.atan(diffY/diffX));
        return alpha;
    }
}
