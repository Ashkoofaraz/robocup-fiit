package sk.fiit.jim.agent.moves.kinematics.test;


class CsvOutput
{
    static final class JointAngles {
//        public final double theta1;
//        public final double theta2;
//        public final double theta3;
//        public final double theta4;
//        public final double theta5;
//        public final double theta6;
    }
    
    private static final CsvOutput INSTANCE = new CsvOutput();
    
    private CsvOutput()
    {
    }
    
    public static CsvOutput getInstance() {
        return INSTANCE;
    }
    
}
