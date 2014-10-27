package sk.fiit.jim.agent.moves.ik;

public class HeadIk
{
    // milimeters
    private static final double NECK_OFFSET_Z = 126.50;
    
    private static final double CAMERA_X_TOP = 53.9;
    private static final double CAMERA_X_BOTTOM = 48.8;
    private static final double CAMERA_Z_TOP = 67.9;
    private static final double CAMERA_Z_BOTTOM = 23.8;
    
    private double theta1;
    
    private double theta2;
    
    private double l1 = CAMERA_X_TOP;  // CameraX
    
    private double l2 = CAMERA_X_TOP; // CameraZ
    
    private double l3; // NechOfffsetZ
    
    public double getTheta2(double px, double py, double pz)
    {
        theta2 = Math.asin((-1*pz + l3)/(Math.sqrt(l1*l1 + l2*l2))) - Math.atan(l1/l2) + Math.PI/2; 
        return theta2;
    }
    
    public double getTheta2_2(double px, double py, double pz)
    {
        theta2 = getTheta2(px, py, pz);
        theta2 = Math.PI - theta2;
        return theta2;
    }
    
    public double getTheta1(double px, double py, double pz)
    {
        
        double denominator = l2 * Math.cos(theta2 - Math.PI/2) - l1 * Math.sin(theta2 - Math.PI/2) ;
        theta1 = Math.acos(px / denominator);
        // +-theta1
        return theta1;
    }
    
}
