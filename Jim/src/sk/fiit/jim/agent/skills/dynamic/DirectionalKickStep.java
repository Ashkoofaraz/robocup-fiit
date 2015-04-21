package sk.fiit.jim.agent.skills.dynamic;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3D;

public class DirectionalKickStep extends DynamicMove {

    private static final double DIST_STEP_VERY_SMALL = 0.012;
    
    private static boolean kicked = false;
    
    private static boolean stepped = false;
    
    private static int steps = 0;
    
    private static int maxSteps = 0;
    
    private static String step;
    
    //private static String kick;
    
    public DirectionalKickStep(double angle)
    {
        init(angle);
    }
    
    public DirectionalKickStep(Point3D endpoint)
    {
        init(getAlpha(endpoint));
    }
    
    public DirectionalKickStep(Vector3D vector)
    {
        init(getAlpha(vector));
    }
    
    public DirectionalKickStep(Point3D ballStart, Point3D ballEnd)
    {
        init(getAlpha(ballStart, ballEnd));
    }
    
    private static void init(double alpha)
    {
        steps = 0;
        double shift = calculateFootShift(alpha) - 0.055;
        System.out.println("shift: " + shift);
        double diff = shift - AgentModel.getInstance().getPosition().getY();
        maxSteps = (int) (Math.abs(diff)/DIST_STEP_VERY_SMALL);
        if(shift > 0)
        {
            step = "step_left_very_small";
        }
        else
        {
            step = "step_right_very_small";
        }
    }
    
	@Override
	public LowSkill pickLowSkill() {
	    if(!stepped && steps < maxSteps) {
	        steps++;
	        return LowSkills.get(step);
	    }
	    stepped = true;
	    if(!kicked) {
	        kicked = true;
            return LowSkills.get("kick_left_normal_stand");
        }
        return null;
	}

	@Override
	public void checkProgress() throws Exception {
	}
	
    private static double calculateFootShift(double alpha)
    {
        double shift = -2.798259731647e-7 * Math.pow(alpha, 4) + 1.48547703927838e-6 * Math.pow(alpha, 3)
                + 0.0002393458 * Math.pow(alpha, 2) - 0.0037578029 * alpha - 0.0304023051;
        return shift;
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
