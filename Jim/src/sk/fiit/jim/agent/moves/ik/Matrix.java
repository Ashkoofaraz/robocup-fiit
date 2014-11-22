package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.FOOT_HEIGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HIP_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HIP_OFFSET_Z;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.TIBIA_LENGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.UPPER_ARM_LENGTH;
import sk.fiit.robocup.library.geometry.Point3D;

public final class Matrix
{
    static final Matrix AbaseLeftArm = createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
    static final Matrix T01LeftArm = createDHTransformation(0, -PI / 2, 0, 0); // a, alpha, d, theta
    static final Matrix T12LeftArm = createDHTransformation(0, PI / 2, 0, -PI / 2);
    static final Matrix T23LeftArm = createDHTransformation(0, -PI / 2, UPPER_ARM_LENGTH, 0);
    static final Matrix T34LeftArm = createDHTransformation(0, PI / 2, 0, 0);
    static final Matrix RzLeftArm = createRotationZ(PI / 2);
    static final Matrix AendLeftArm = createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);

    static final Matrix AendRightArm = createTranslation(-HAND_OFFSET_X - LOWER_ARM_LENGTH, 0, 0);
    static final Matrix invAendRightArm = AendRightArm.inverse();
    
    static final Matrix AbaseLeftLeg = createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z);
    static final Matrix invAbaseLeftLeg = AbaseLeftLeg.inverse();
    static final Matrix AendLeftLeg = createTranslation(0, 0, -FOOT_HEIGHT);
    static final Matrix invAendLeftLeg = AendLeftLeg.inverse();
    static final Matrix T34LeftLeg = createDHTransformation(-THIGH_LENGHT, 0, 0, 0);
    static final Matrix T45LeftLeg = createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);
    static final Matrix T56LeftLeg = createDHTransformation(0, -PI/2, 0, 0);
    static final Matrix RzLeftLeg =  createRotationZ(PI);
    static final Matrix RyLeftLeg =  createRotationY(-PI/2);
    
    static final Matrix AbaseRightLeg = Matrix.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);
    static final Matrix invAbaseRightLeg = AbaseRightLeg.inverse();
    static final Matrix AendRightLeg = createTranslation(0, 0, -FOOT_HEIGHT);
    static final Matrix invAendRightLeg = AendRightLeg.inverse();
    static final Matrix T34RightLeg = createDHTransformation(-THIGH_LENGHT, 0, 0, 0);
    static final Matrix T45RightLeg = createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);
    static final Matrix T56RightLeg = createDHTransformation(0, -PI/2, 0, 0);
    static final Matrix RzRightLeg = createRotationZ(PI);
    static final Matrix RyRightLeg = createRotationY(-PI/2);
    
    static final Matrix ROTATION_X_PI_4 = createRotationX(PI/4);
    static final Matrix ROTATION_X_PI_4_MINUS = createRotationX(-PI/4);
    static final Matrix ROTATION_Z_PI_MINUS = createRotationZ(PI/2);
    
    private static final int DEFAULT_N = 4;

    private final double[][] values;

    private final int rows;

    private final int columns;

    public Matrix(int rows, int columns)
    {
        if(rows < 1)
        {
            throw new IllegalArgumentException("rows < 1");
        }
        if(columns < 1)
        {
            throw new IllegalArgumentException("columns < 1");
        }
        this.rows = rows;
        this.columns = columns;
        this.values = new double[rows][columns];
    }

    private Matrix(double[][] values)
    {
        this.values = values;
        this.rows = values.length;
        this.columns = values[0].length;
    }

    public Matrix changeValueAt(int i, int j, double val)
    {
        if(i < 0)
        {
            throw new IllegalArgumentException("i < 0");
        }
        if(j < 0)
        {
            throw new IllegalArgumentException("j < 0");
        }

        if(i >= rows)
        {
            throw new IllegalArgumentException("i >= rows: " + i + " >= " + rows);
        }
        if(j >= columns)
        {
            throw new IllegalArgumentException("j >= columns: " + j + " >= "
                    + columns);
        }
        Matrix result = new Matrix(values);
        result.values[i][j] = val;
        return result;
    }

    public double getValueAt(int i, int j)
    {
        if(i < 0)
        {
            throw new IllegalArgumentException("i < 0");
        }
        if(j < 0)
        {
            throw new IllegalArgumentException("j < 0");
        }

        if(i >= rows)
        {
            throw new IllegalArgumentException("i >= rows: " + i + " >= " + rows);
        }
        if(j >= columns)
        {
            throw new IllegalArgumentException("j >= columns: " + j + " >= "
                    + columns);
        }
        return values[i][j];
    }

    public int getRows()
    {
        return rows;
    }

    public int getColumns()
    {
        return columns;
    }

    public Matrix inverse()
    {
        double determinant = this.determinant();
        return cofactor().transpose().mult(1 / determinant);
    }

    public Matrix add(Matrix matrix)
    {
        // TODO check dimensions
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result.values[i][j] = this.values[i][j] + matrix.values[i][j];
            }
        }
        return result;
    }

    public Matrix add(double val)
    {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = this.values[i][j] + val;
            }
        }
        return result;
    }

    public Matrix sub(Matrix matrix)
    {
        // TODO check dimensions
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                result.values[i][j] = this.values[i][j] - matrix.values[i][j];
            }
        }
        return result;
    }

    public Matrix sub(double val)
    {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = this.values[i][j] - val;
            }
        }
        return result;
    }

    public Matrix mult(Matrix matrix)
    {
        // TODO check dimensions
        Matrix result = new Matrix(rows, matrix.columns);
        for (int i = 0; i < DEFAULT_N; i++)
        {
            for (int j = 0; j < DEFAULT_N; j++)
            {
                for (int k = 0; k < DEFAULT_N; k++)
                {
                    result.values[i][j] += this.values[i][k]
                            * matrix.values[k][j];
                }
            }
        }
        return result;
    }

    public Matrix mult(double val)
    {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = values[i][j] * val;
            }
        }
        return result;
    }

    public Matrix transpose()
    {
        Matrix result = new Matrix(columns, rows);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[j][i] = this.values[i][j];
            }
        }
        return result;
    }

    public static Matrix createTranslation(double x, double y, double z)
    {
        // expected 4x4
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][3] = x;
        result.values[1][3] = y;
        result.values[2][3] = z;
        return result;
    }

    public static Matrix createRotationX(double angle)
    {
        // expected 4x4
        Matrix result = createIdentity(DEFAULT_N);
        result.values[1][1] = cos(angle);
        result.values[1][2] = -sin(angle);
        result.values[2][1] = sin(angle);
        result.values[2][2] = cos(angle);
        return result;
    }

    public static Matrix createRotationY(double angle)
    {
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][0] = cos(angle);
        result.values[0][2] = sin(angle);
        result.values[2][0] = -sin(angle);
        result.values[2][2] = cos(angle);
        return result;
    }

    public static Matrix createRotationZ(double angle)
    {
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][0] = cos(angle);
        result.values[0][1] = -sin(angle);
        result.values[1][0] = sin(angle);
        result.values[1][1] = cos(angle);
        return result;
    }

    public static Matrix createDHTransformation(double a, double alpha,
            double d, double theta)
    {
        Matrix result = new Matrix(DEFAULT_N, DEFAULT_N);
        result.values[0][0] = cos(theta);
        result.values[0][1] = -sin(theta);
        result.values[0][2] = 0;
        result.values[0][3] = a;
        result.values[1][0] = sin(theta) * cos(alpha);
        result.values[1][1] = cos(theta) * cos(alpha);
        result.values[1][2] = -sin(alpha);
        result.values[1][3] = -sin(alpha) * d;
        result.values[2][0] = sin(theta) * sin(alpha);
        result.values[2][1] = cos(theta) * sin(alpha);
        result.values[2][2] = cos(alpha);
        result.values[2][3] = cos(alpha) * d;
        result.values[3][3] = 1;
        return result;
    }

    public static Matrix createTransformation(double px, double py, double pz,
            double rx, double ry, double rz)
    {
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][0] = cos(rz) * cos(ry);
        result.values[0][1] = cos(rz) * sin(ry) * sin(rx) - sin(rz) * cos(rx);
        result.values[0][2] = cos(rz) * sin(ry) * cos(rx) + sin(rz) * sin(rx);
        result.values[0][3] = px;
        result.values[1][0] = sin(rz) * cos(ry);
        result.values[1][1] = sin(rz) * sin(ry) * sin(rx) + cos(rz) * cos(rx);
        result.values[1][2] = sin(rz) * sin(ry) * cos(rx) - cos(rz) * sin(rx);
        result.values[1][3] = py;
        result.values[2][0] = -sin(ry);
        result.values[2][1] = cos(ry) * sin(rx);
        result.values[2][2] = cos(ry) * cos(rx);
        result.values[2][3] = pz;
        return result;
    }
    
    static Matrix createTransformation(Point3D end, Orientation angle)
    {
        double rx = angle.getAxRadians();
        double ry = angle.getAyRadians();
        double rz = angle.getAzRadians();
        double px = end.x;
        double py = end.y;
        double pz = end.z;
        Matrix result = createTransformation(px, py, pz, rx, ry, rz);
        return result;
    }

    public static Matrix createIdentity(int n)
    {
        Matrix result = new Matrix(n, n);
        for (int i = 0; i < n; i++)
        {
            result.values[i][i] = 1;
        }
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                sb.append(String.format(" %.2f", values[i][j]));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public double determinant()
    {
        // TODO zovseobecni a testuj na stvorcovu maticu
        if(rows == 1)
        {
            return values[0][0];
        }
        if(rows == 2)
        {
            return values[0][0] * values[1][1] - values[0][1] * values[1][0];
        }

        double result = 0;
        for (int i = 0; i < rows; i++)
        {
            result += changeSign(i) * values[0][i]
                    * submatrix(0, i).determinant();
        }
        return result;
    }

    private static int changeSign(int i)
    {
        return i % 2 == 0 ? 1 : -1;
    }

    public Matrix submatrix(int excludingRow, int excludingColumn)
    {
        // TODO zovseobecni a kontroluj stvorcovu maticu
        Matrix result = new Matrix(rows - 1, rows - 1);
        int r = -1;
        for (int i = 0; i < rows; i++)
        {
            if(i == excludingRow)
                continue;
            r++;
            int c = -1;
            for (int j = 0; j < rows; j++)
            {
                if(j == excludingColumn)
                    continue;
                c++;
                result.values[r][c] = values[i][j];
            }

        }
        return result;
    }

    public Matrix cofactor()
    {
        Matrix result = new Matrix(rows, rows);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                result.values[i][j] = changeSign(i) * changeSign(j)
                        * submatrix(i, j).determinant();
            }
        }
        return result;
    }
    
}
