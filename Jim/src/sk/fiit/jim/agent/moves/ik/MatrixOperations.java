package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;

public final class MatrixOperations
{
    private static final int DEFAULT_N = 4;

    private MatrixOperations()
    {
    }

    public static double[][] createTranslation(double x, double y, double z)
    {
        double[][] result = identity(DEFAULT_N);
        result[0][3] = x;
        result[1][3] = y;
        result[2][3] = z;
        return result;
    }

    public static double[][] createRotationX(double angle)
    {
        double[][] result = identity(DEFAULT_N);
        result[1][1] = cos(angle);
        result[1][2] = -sin(angle);
        result[2][1] = sin(angle);
        result[2][2] = cos(angle);
        return result;
    }

    public static double[][] createRotationY(double angle)
    {
        double[][] result = identity(DEFAULT_N);
        result[0][0] = cos(angle);
        result[0][2] = sin(angle);
        result[2][0] = -sin(angle);
        result[2][2] = cos(angle);
        return result;
    }

    public static double[][] createRotationZ(double angle)
    {
        double[][] result = identity(DEFAULT_N);
        // m.AisIdentity = false;
        result[0][0] = cos(angle);
        result[0][1] = -sin(angle);
        result[1][0] = sin(angle);
        result[1][1] = cos(angle);
        return result;
    }

    public static double[][] createDHTransformation(double a, double alpha,
            double d, double theta)
    {
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        result[0][0] = cos(theta);
        result[0][1] = -sin(theta);
        result[0][2] = 0;
        result[0][3] = a;
        result[1][0] = sin(theta) * cos(alpha);
        result[1][1] = cos(theta) * cos(alpha);
        result[1][2] = -sin(alpha);
        result[1][3] = -sin(alpha) * d;
        result[2][0] = sin(theta) * sin(alpha);
        result[2][1] = cos(theta) * sin(alpha);
        result[2][2] = cos(alpha);
        result[2][3] = cos(alpha) * d;
        result[3][3] = 1;
        return result;
    }
    
    public static double[][]  createTransformation(double px, double py, double pz, double rx, double ry, double rz)
    {
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        result[0][0] = cos(rz) * cos(ry);
        result[0][1] = cos(rz) * sin(ry) * sin(rx) - sin(rz) * cos(rx);
        result[0][2] = cos(rz) * sin(ry) * cos(rx) + sin(rz) * sin(rx);
        result[0][3] = px;
        result[1][0] = sin(rz) * cos(ry);
        result[1][1] = sin(rz) * sin(ry) * sin(rx) + cos(rz) * cos(rx);
        result[1][2] = sin(rz) * sin(ry) * cos(rx) - cos(rz) * sin(rx);
        result[1][3] = py;
        result[2][0] = -sin(ry);
        result[2][1] = cos(ry) * sin(rx);
        result[2][2] = cos(ry) * cos(rx);
        result[2][3] = pz;
        return result;
    }

    public static double[][] identity(int n)
    {
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++)
        {
            result[i][i] = 1;
        }
        return result;
    }

    public static double[][] add(double[][] A, double[][] B)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    public static double[][] sub(double[][] A, double[][] B)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    public static double[][] mult(double[][] A, double[][] B)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                for (int k = 0; k < DEFAULT_N; k++)
                {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] addScalar(double[][] A, double value)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[i][j] = A[i][j] + value;
            }
        }
        return result;
    }

    public static double[][] subScalar(double[][] A, double value)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[i][j] -= value;
            }
        }
        return result;
    }

    public static double[][] multScalar(double[][] A, double value)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[i][j] = A[i][j] * value;
            }
        }
        return result;
    }
    
    public static void print(double[][] A)
    {
        for(int i = 0; i < A.length; i++)
        {
            for(int j = 0; j < A[i].length; j++)
            {
                System.out.printf(" %.2f", A[i][j]);
            }
            System.out.println();
        }
    }
    
    public static double[][] inverse(double[][] A)
    {
        return multScalar(transpose(cofactor(A)), 1/determinant(A));
    }
    
    public static double determinant(double[][] A)
    {
        // TODO zovseobecni a testuj na stvorcovu maticu
        if(A.length == 1)
        {
            return A[0][0];
        }
        if(A.length == 2)
        {
            return A[0][0] * A[1][1] - A[0][1] * A[1][0];
        }
        
        double result = 0;
        for(int i = 0; i < A.length; i++)
        {
            result += changeSign(i) * A[0][i] * determinant(submatrix(A, 0, i));
        }
        return result;
    }
    
    private static int changeSign(int i)
    {
        return i % 2 == 0 ? 1 : -1;
    }
    
    private static double[][] submatrix(double[][] A, int excludingRow, int excludingColumn)
    {
        // TODO zovseobecni a kontroluj stvorcovu maticu
        double[][] result = new double[A.length - 1][A.length - 1];
        int r = -1;
        for(int i = 0; i < A.length; i++)
        {
            if(i == excludingRow) continue;
            r++;
            int c = -1;
            for(int j = 0; j < A.length; j++)
            {
                if(j == excludingColumn) continue;
                c++;
                result[r][c] = A[i][j];
            }
            
        }
        return result;
    }
    
    private static double[][] cofactor(double[][] A)
    {
        double[][] result = new double[A.length][A.length];
        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result.length; j++)
            {
                result[i][j] = changeSign(i) * changeSign(j) * determinant(submatrix(A, i, j));
            }
        }
        return result;
    }
    
    public static double[][] transpose(double[][] A)
    {
        // TODO check dimensions
        double[][] result = new double[DEFAULT_N][DEFAULT_N];
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result[j][i] = A[i][j];
            }
        }
        return result;
    }
}
