package sk.fiit.jim.agent.moves.ik;

public class LeftArmIk
{
    // milimeters
    private static final double NECK_OFFSET_Z = 126.50;
    private static final double  SHOULDER_OFFSET_Y = 98.00;
    private static final double ELBOW_OFFSET_Y =15.00;
    private static final double UPPER_ARM_LENGTH =105.00;
    private static final double LOWER_ARM_LENGTH =55.95;
    private static final double SHOULDER_OFFSET_Z= 100.00;
    private static final double HAND_OFFSET_X =57.75;
    private static final double HIP_OFFSET_X= 85.00;
    private static final double HIP_OFFSET_Y =50.00;
    private static final double THIGH_LENGHT= 100.00;
    private static final double TIBIA_LENGHT =102.90;
    private static final double FOOT_HEIGHT =45.19;
    private static final double HAND_OFFSET_Z= 12.31;
    
    private double theta1;
    
    private double theta2;
    
    private double theta3;
    
    private double theta4;
    
    private double l1;  // ShoulderOffsetY + ElbowOffsetY
    
    private double l2; // ShoulderOffsetZ
    
    private double l3; // UpperArmLength
    
    private double l4; // HandOffsetX + LowerArmLength.
    
    // leftShoulderPitch (sx, sy, sz) = (0, l1, l2)
    
    private double sx;
    
    private double sy;
    
    private double sz;
    
    // radians
    public double getTheta4(double px, double py, double pz)
    {
        // T24 == py
        double d = Math.sqrt((sx - px)*(sx - px) + (sy - py)*(sy - py) + (sz - pz)*(sz - pz));
        double nominator = l3 * l3 + l4*l4 - d*d;
        double denominator = 2*l3*l4;
        theta4 = -1 * (Math.PI - Math.acos(nominator / denominator));
        return theta4;
    }
    
    public double getTheta2(double px, double py, double pz)
    {
        double T22 = 0.0; // TODO
        double nominator = py - l1 - ((l4 - Math.sin(theta4 * T22))/(Math.cos(theta4)));
        double denominator = l3 + l4 * Math.cos(theta4)+ l4*(Math.sin(theta4) * Math.sin(theta4)) / Math.cos(theta4);
        // v diplomovke je +- cela operacia
        theta2 = Math.acos(nominator/denominator) + Math.PI/2;
        return theta2;
    }
    
    public double getTheta3_1(double px, double py, double pz)
    {
        double T23 = 0.0; // TODO
        theta3 = -1 * Math.asin(T23 /(Math.sin(theta2 - Math.PI/2)));
        return theta3;
    }
    
    public double getTheta3_2(double px, double py, double pz)
    {
        theta3 = -1 * (Math.PI - getTheta3_1(px, py, pz));
        return theta3;
    }
    
    public double getTheta1(double px, double py, double pz)
    {
        if(Math.abs(theta3) != Math.PI / 2)
        {
            double T33 = 0.0; // TODO
            double T13 = 0.0; // TODO
            double nominator = T33 + ((T13 * Math.sin(-1 * theta3) * Math.cos(theta3 - Math.PI/2))/(Math.cos(-1 * theta3)));
            double denominator = Math.cos(-1 * theta3) + (Math.cos(theta2 - Math.PI/2) * Math.cos(theta2 - Math.PI/2) * Math.sin(-1 * theta3) * Math.sin(-1 * theta3))/(Math.cos(-1 * theta3));
            // +- theta1
            theta1 = Math.acos(nominator/denominator);
        }
        else if((Math.abs(theta3) == Math.PI / 2) && theta2 != 0.0)
        {
            double T13 = 0.0; // TODO
            // +- theta1
            theta1 = Math.acos((T13)/(Math.cos(theta2 - Math.PI/2)*Math.sin(-1*theta3)));
        }
        else if((Math.abs(theta3) == Math.PI / 2) && theta2 == 0.0)
        {
            double T14_ = 0.0; // TODO
            // +- theta1
            theta1 = Math.acos(T14_ / l3);
        }
        return theta1;
    }
    
}
