package sk.fiit.jim.agent.moves.ik;

public final class Constants
{
    // milimeters
    static final double NECK_OFFSET_Z = 126.50;

    static final double SHOULDER_OFFSET_Y = 98.00;

    static final double ELBOW_OFFSET_Y = 15.00;

    static final double UPPER_ARM_LENGTH = 105.00;

    static final double LOWER_ARM_LENGTH = 55.95;

    static final double SHOULDER_OFFSET_Z = 100.00;

    static final double HAND_OFFSET_X = 57.75;

    static final double HIP_OFFSET_Z = 85.00;

    static final double HIP_OFFSET_Y = 50.00;

    static final double THIGH_LENGHT = 100.00;

    static final double TIBIA_LENGHT = 102.90;

    static final double FOOT_HEIGHT = 45.19;

    static final double HAND_OFFSET_Z = 12.31;
    
    private Constants()
    {
    }

    public static double[][] inverseMatrix(double[][] matrix)
    {
        if(matrix == null)
        {
            return null;
        }
        double[][] result = new double[4][4]; // TODO
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public static void print(double[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                System.out.printf(" %3.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    static double normalizeArcsin(double angle)
    {
        if(angle < -1.0)
        {
            return -1.0;
        }
        else if(angle > 1.0)
        {
            return 1.0;
        }
        else
        {
            return angle;
        }
    }

    static double normalizeArccos(double angle)
    {
        if(angle < -1.0)
        {
            return -1.0;
        }
        else if(angle > 1.0)
        {
            return 1.0;
        }
        else
        {
            return angle;
        }
    }
}
