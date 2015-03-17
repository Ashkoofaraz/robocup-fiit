package sk.fiit.jim.agent.moves.kinematics;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.FOOT_HEIGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HIP_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HIP_OFFSET_Z;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.TIBIA_LENGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.UPPER_ARM_LENGTH;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * <p>
 * Immutable representation of matrices. Contains basic matrix operations.
 * </p>
 * <p>
 * Note that matrix rows and column are indexed from zero.
 * </p>
 * 
 * @author Pidanic
 *
 */
public final class Matrix
{
    /**
     * translation matrix from torso to base joint of left arm
     */
    static final Matrix A_BASE_LEFT_ARM = createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);

    /**
     * transformation matrix from base of left arm to LAE1
     */
    static final Matrix T_01_LEFT_ARM = createDHTransformation(0, -PI / 2, 0, 0);

    /**
     * transformation matrix from LAE1 to LAE2 for left arm
     */
    static final Matrix T_12_LEFT_ARM = createDHTransformation(0, PI / 2, 0, -PI / 2);

    /**
     * transformation matrix from LAE2 to LAE3 for left arm
     */
    static final Matrix T_23_LEFT_ARM = createDHTransformation(0, -PI / 2, UPPER_ARM_LENGTH, 0);

    /**
     * transformation matrix from LAE3 to LAE4 for left arm
     */
    static final Matrix T_34_LEFT_ARM = createDHTransformation(0, PI / 2, 0, 0);

    /**
     * rotation matrix fix for left arm with angle <tt>&pi;/2</tt> about
     * <tt>z</tt>-axis
     */
    static final Matrix R_Z_LEFT_ARM = createRotationZ(PI / 2);

    /**
     * translation matrix from LAE4 to end of left arm
     */
    static final Matrix A_END_LEFT_ARM = createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);

    /**
     * translation matrix from RAE4 to end of right arm
     */
    static final Matrix A_END_RIGHT_ARM = createTranslation(-HAND_OFFSET_X - LOWER_ARM_LENGTH, 0, 0);

    /**
     * inverse matrix of {@link #A_END_RIGHT_ARM}
     */
    static final Matrix INV_A_END_RIGHT_ARM = A_END_RIGHT_ARM.inverse();

    /**
     * translation matrix from torso to base of left leg
     */
    static final Matrix A_BASE_LEFT_LEG = createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z);

    /**
     * inverse matrix of {@link #A_BASE_LEFT_LEG}
     */
    static final Matrix INV_A_BASE_LEFT_LEG = A_BASE_LEFT_LEG.inverse();

    /**
     * translation matrix from LLE6 to end of left foot.
     */
    static final Matrix A_END_LEFT_LEG = createTranslation(0, 0, -FOOT_HEIGHT);

    /**
     * inverse matrix of {@link #A_END_LEFT_LEG}
     */
    static final Matrix INV_A_END_LEFT_LEG = A_END_LEFT_LEG.inverse();

    /**
     * transformation matrix from LLE3 to LLE4
     */
    static final Matrix T_34_LEFT_LEG = createDHTransformation(-THIGH_LENGHT, 0, 0, 0);

    /**
     * transformation matrix from LLE4 to LLE5
     */
    static final Matrix T_45_LEFT_LEG = createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);

    /**
     * transformation matrix from LLE3 to LLE4
     */
    static final Matrix T_56_LEFT_LEG = createDHTransformation(0, -PI / 2, 0, 0);

    /**
     * rotation matrix fix for left leg with angle <tt>&pi;</tt> about
     * <tt>z</tt>-axis
     */
    static final Matrix R_Z_LEFT_LEG = createRotationZ(PI);

    /**
     * rotation matrix fix for left leg with angle <tt>-&pi;/2</tt> about
     * <tt>y</tt>-axis
     */
    static final Matrix R_Y_LEFT_LEG = createRotationY(-PI / 2);

    /**
     * translation matrix from torso to base of right leg
     */
    static final Matrix A_BASE_RIGHT_LEG = Matrix.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);

    /**
     * inverse matrix of {@link A_BASE_RIGHT_LEG}
     */
    static final Matrix INV_A_BASE_RIGHT_LEG = A_BASE_RIGHT_LEG.inverse();

    /**
     * transformation matrix from RLE6 to end of right leg
     */
    static final Matrix A_END_RIGHT_LEG = createTranslation(0, 0, -FOOT_HEIGHT);

    /**
     * inverse matrix of {@link #A_END_RIGHT_LEG}
     */
    static final Matrix INV_A_END_RIGHT_LEG = A_END_RIGHT_LEG.inverse();

    /**
     * transformation matrix from RLE3 to RLE4
     */
    static final Matrix T_34_RIGHT_LEG = createDHTransformation(-THIGH_LENGHT, 0, 0, 0);

    /**
     * transformation matrix from RLE4 to RLE5
     */
    static final Matrix T_45_RIGHT_LEG = createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);

    /**
     * transformation matrix from RLE5 to RLE6
     */
    static final Matrix T_56_RIGHT_LEG = createDHTransformation(0, -PI / 2, 0, 0);

    /**
     * rotation matrix fix for right leg with angle <tt>&pi;</tt> about
     * <tt>z</tt>-axis
     */
    static final Matrix R_Z_RIGHT_LEG = createRotationZ(PI);

    /**
     * rotation matrix fix for right leg with angle <tt>-&pi;/2</tt> about
     * <tt>y</tt>-axis
     */
    static final Matrix R_Y_RIGHT_LEG = createRotationY(-PI / 2);

    /**
     * rotation matrix fix with angle <tt>&pi;/4</tt> about <tt>x</tt>-axis
     */
    static final Matrix R_X_PI_4 = createRotationX(PI / 4);

    /**
     * rotation matrix fix with angle <tt>-&pi;/4</tt> about <tt>x</tt>-axis
     */
    static final Matrix R_X_PI_4_MINUS = createRotationX(-PI / 4);

    /**
     * rotation matrix fix for right arm with angle <tt>-&pi;</tt> about
     * <tt>z</tt>-axis
     */
    static final Matrix R_Z_RIGHT_ARM = createRotationZ(-PI);

    /**
     * default dimension size for transformation matrices in 3D space.
     */
    private static final int DEFAULT_N = 4;

    private final double[][] values;

    private final int rows;

    private final int columns;

    /**
     * Creates empty matrix with given rows and columns count.
     * 
     * @param rows
     *            Row count of new Matrix.
     * @param columns
     *            Column count of new Matrix.
     * @throws IllegalArgumentException
     *             if given column or rows are less than 1.
     */
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

    /**
     * <p>
     * Creates new matrix with given values stored in 2 dimensional array.
     * </p>
     * <p>
     * It is required for a matrix to be full matrix. Elements count must be
     * equal for all rows and all columns.
     * </p>
     * 
     * @param values
     *            Given values of matrix.
     * @throws IllegalArgumentException
     *             if array is empty or elements count is not equal for all rows
     *             and all columns.
     * @throws NullPointerException
     *             if <b>value</b> is <code>null</code>.
     * 
     */
    public Matrix(double[][] values)
    {
        this.validateMatrix(values);
        this.values = values;
        this.rows = values.length;
        this.columns = values[0].length;
    }

    private void validateMatrix(double[][] values)
    {
        if(values == null)
        {
            throw new NullPointerException("Parameter values is null");
        }
        if(values.length == 0)
        {
            throw new IllegalArgumentException("Empty values");
        }
        if(values[0].length == 0)
        {
            throw new IllegalArgumentException("Empty values");
        }

        @SuppressWarnings("unused")
        int rows = values.length;
        int columns = values[0].length;
        for (int i = 1; i < values.length; i++)
        {
            if(values[i] == null)
            {
                throw new NullPointerException("Row " + i + " is null");
            }
            if(values[i].length != columns)
            {
                throw new IllegalArgumentException("Column count in all rows is not equal");
            }
        }
    }

    /**
     * Changes a value at given row and columns index and creates new
     * {@link Matrix}. Former matrix stay unchanged.
     * 
     * @param row
     *            Value at row to be changed.
     * @param column
     *            Value at column to be changed.
     * @param value
     *            new value.
     * @return new {@link Matrix} with changed value at given row and column.
     * 
     * @throws IllegalArgumentException
     *             if row or column is negative or higher than row or column
     *             count of matrix.
     */
    public Matrix changeValueAt(int row, int column, double value)
    {
        if(row < 0)
        {
            throw new IllegalArgumentException("i < 0");
        }
        if(column < 0)
        {
            throw new IllegalArgumentException("j < 0");
        }

        if(row >= rows)
        {
            throw new IllegalArgumentException("i >= rows: " + row + " >= " + rows);
        }
        if(column >= columns)
        {
            throw new IllegalArgumentException("j >= columns: " + column + " >= " + columns);
        }
        Matrix result = new Matrix(values);
        result.values[row][column] = value;
        return result;
    }

    /**
     * Return a value at given row and column.
     * 
     * @param row
     *            Row index.
     * @param column
     *            Column index.
     * @return value at row and column index.
     * 
     * @throws IllegalArgumentException
     *             if row or column is negative or higher than row or column
     *             count of matrix.
     */
    public double getValueAt(int row, int column)
    {
        if(row < 0)
        {
            throw new IllegalArgumentException("i < 0");
        }
        if(column < 0)
        {
            throw new IllegalArgumentException("j < 0");
        }

        if(row >= rows)
        {
            throw new IllegalArgumentException("i >= rows: " + row + " >= " + rows);
        }
        if(column >= columns)
        {
            throw new IllegalArgumentException("j >= columns: " + column + " >= " + columns);
        }
        return values[row][column];
    }

    /**
     * Returns row count of the matrix.
     * 
     * @return row count.
     */
    public int getRows()
    {
        return rows;
    }

    /**
     * Returns column count of the matrix.
     * 
     * @return column count.
     */
    public int getColumns()
    {
        return columns;
    }

    /**
     * Return new inverted matrix of former matrix.
     * 
     * @return inverted matrix.
     */
    public Matrix inverse()
    {
        double determinant = this.determinant();
        return cofactor().transpose().mult(1 / determinant);
    }

    /**
     * Creates new matrix that is result of addition of 2 matrices.
     * 
     * @param matrix
     *            Matrix to add.
     * @return result of 2 matrices addition.
     * 
     * @throws IllegalArgumentException
     *             if dimensions of matrix are not suitable for addition.
     * @throws NullPointerException
     *             if input matrix is <code>null</code>.
     */
    public Matrix add(Matrix matrix)
    {
        if(matrix == null)
        {
            throw new NullPointerException("matrix is null");
        }
        if(matrix.getRows() != rows || matrix.getColumns() != columns)
        {
            throw new IllegalArgumentException("Incorrect matrices dimensions for addition: " + rows + "×" + columns
                    + " != " + matrix.getRows() + "×" + matrix.getColumns());
        }
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = this.values[i][j] + matrix.values[i][j];
            }
        }
        return result;
    }

    /**
     * Creates new matrix that is result of scalar addition.
     * 
     * @param value
     *            Scalar value.
     * @return result of scalar addition.
     * 
     */
    public Matrix add(double value)
    {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = this.values[i][j] + value;
            }
        }
        return result;
    }

    /**
     * Creates new matrix that is result of subtraction of 2 matrices.
     * 
     * @param matrix
     *            Matrix to subtract.
     * @return result of 2 matrices subtraction.
     * 
     * @throws IllegalArgumentException
     *             if dimensions of matrix are not suitable for subtraction.
     * @throws NullPointerException
     *             if input matrix is <code>null</code>.
     */
    public Matrix sub(Matrix matrix)
    {
        if(matrix == null)
        {
            throw new NullPointerException("matrix is null");
        }
        if(matrix.getRows() != rows || matrix.getColumns() != columns)
        {
            throw new IllegalArgumentException("Incorrect matrices dimensions for subtraction: " + rows + "×" + columns
                    + " != " + matrix.getRows() + "×" + matrix.getColumns());
        }

        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = this.values[i][j] - matrix.values[i][j];
            }
        }
        return result;
    }

    /**
     * Creates new matrix that is result of scalar subtraction.
     * 
     * @param value
     *            Scalar value.
     * @return result of scalar subtraction.
     * 
     */
    public Matrix sub(double value)
    {
        return add(-value);
    }

    /**
     * Calculates product of 2 matrices and return new {@link Matrix} as a
     * result.
     * 
     * @param matrix
     *            {@link Matrix} to multiply.
     * @return result of product of 2 matrices.
     * 
     * @throws NullPointerException
     *             if {@code matrix} is null.
     * @throws IllegalArgumentException
     *             if matrix dimensions do not hold
     * 
     *             <pre>
     * (n × m) × (m × p)
     * </pre>
     */
    public Matrix mult(Matrix matrix)
    {
        if(matrix == null)
        {
            throw new NullPointerException("matrix is null");
        }
        if(this.columns != matrix.rows)
        {
            throw new IllegalArgumentException(
                    "Incorrect dimensions for multiplication (n × m) × (m × p). Dimensions: "
                            + String.format("(%d × %d) × (%d × %d)", rows, columns, matrix.rows, matrix.columns));
        }
        Matrix result = new Matrix(rows, matrix.columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < matrix.columns; j++)
            {
                for (int k = 0; k < columns; k++)
                {
                    result.values[i][j] += this.values[i][k] * matrix.values[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Creates new matrix that is result of scalar multiplication.
     * 
     * @param val
     *            Scalar value.
     * @return result of scalar multiplication.
     * 
     */
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

    /**
     * 
     * @return new transposed matrix.
     */
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

    /**
     * <p>
     * Creates new translation matrix in 3D space with size {@code 4×4}.
     * </p>
     * <p>
     * The matrix has the form
     * 
     * <pre>
     * |       x |
     * |   I   y |
     * |       z |
     * | 0 0 0 1 |
     * </pre>
     * 
     * where {@code I} is the identity {@code 3×3} matrix.
     * </p>
     * 
     * @param x
     *            {@code x} coordinate.
     * @param y
     *            {@code y} coordinate.
     * @param z
     *            {@code z} coordinate.
     * @return translation matrix.
     */
    public static Matrix createTranslation(double x, double y, double z)
    {
        // default 4x4
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][3] = x;
        result.values[1][3] = y;
        result.values[2][3] = z;
        return result;
    }

    /**
     * <p>
     * Creates new rotation matrix in 3D space with size {@code 4×4} about
     * {@code x}-axis.
     * </p>
     * 
     * @param angle
     *            angle value in radians.
     * @return rotation matrix.
     */
    public static Matrix createRotationX(double angle)
    {
        // default 4x4
        Matrix result = createIdentity(DEFAULT_N);
        result.values[1][1] = cos(angle);
        result.values[1][2] = -sin(angle);
        result.values[2][1] = sin(angle);
        result.values[2][2] = cos(angle);
        return result;
    }

    /**
     * <p>
     * Creates new rotation matrix in 3D space with size {@code 4×4} about
     * {@code y}-axis.
     * </p>
     * 
     * @param angle
     *            angle value in radians.
     * @return rotation matrix.
     */
    public static Matrix createRotationY(double angle)
    {
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][0] = cos(angle);
        result.values[0][2] = sin(angle);
        result.values[2][0] = -sin(angle);
        result.values[2][2] = cos(angle);
        return result;
    }

    /**
     * <p>
     * Creates new rotation matrix in 3D space with size {@code 4×4} about
     * {@code z}-axis.
     * </p>
     * 
     * @param angle
     *            angle value in radians.
     * @return rotation matrix.
     */
    public static Matrix createRotationZ(double angle)
    {
        Matrix result = createIdentity(DEFAULT_N);
        result.values[0][0] = cos(angle);
        result.values[0][1] = -sin(angle);
        result.values[1][0] = sin(angle);
        result.values[1][1] = cos(angle);
        return result;
    }

    /**
     * <p>
     * Creates new transformation matrix in 3D space with size {@code 4×4} using
     * Denavit-Hartenberg (DH) parameters.
     * </p>
     * <p>
     * DH matrix is a product of 4 matrices: <br>
     * 
     * <pre>
     * T<sub>DH</sub>=R<sub>x</sub>(&alpha;)T<sub>x</sub>(a)R<sub>z</sub>(&theta;)T<sub>z</sub>(d)
     * </pre>
     * 
     * where <tt>R<sub>x</sub>(&alpha;)</tt> is a rotation matrix about
     * <tt>x</tt>-axis with angle size <tt>&alpha;</tt>,
     * <tt>T<sub>x</sub>(a)</tt> is a translation matrix about <tt>x</tt>-axis
     * with size <tt>a</tt>, <tt>R<sub>z</sub>(&theta;)</tt> is a rotation
     * matrix about <tt>z</tt>-axis with angle size <tt>&theta;</tt>,
     * <tt>T<sub>z</sub>(d)</tt> is a translation matrix about <tt>z</tt>-axis
     * with size <tt>d</tt>.
     * </p>
     * 
     * @param a
     *            length of the common normal.
     * @param alpha
     *            angle about the common normal, from <tt>z<sub>i−1</sub></tt>
     *            -axis to <tt>z<sub>i</sub></tt>-axis.
     * @param d
     *            offset along the <tt>z<sub>i−1</sub></tt>-axis to the common
     *            normal.
     * @param theta
     *            angle about the <tt>z<sub>i−1</sub></tt>-axis, from
     *            <tt>x<sub>i−1</sub></tt>-axis to <tt>x<sub>i</sub></tt>-axis.
     * @return transformation matrix.
     */
    public static Matrix createDHTransformation(double a, double alpha, double d, double theta)
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

    /**
     * <p>
     * Creates new transformation matrix in 3D space with size {@code 4×4} with
     * given point and orientation about all axes.
     * </p>
     * <p>
     * A transformation matrix has following form:
     * 
     * <pre>
     * |       px|
     * |   R   py|
     * |       pz|
     * | 0 0 0 1 |
     * </pre>
     * 
     * where <tt>R</tt> is a rotation matrix about all axes, <tt>px</tt> is
     * <tt>x</tt> coordinate of an endpoint, <tt>py</tt> is <tt>y</tt>
     * coordinate of an endpoint, <tt>pz</tt> is <tt>z</tt> coordinate of an
     * endpoint.
     * </p>
     * 
     * @param px
     *            <tt>x</tt> coordinate of an endpoint.
     * @param py
     *            <tt>y</tt> coordinate of an endpoint.
     * @param pz
     *            <tt>z</tt> coordinate of an endpoint.
     * @param rx
     *            angle of rotation about <tt>x</tt>-axis.
     * @param ry
     *            angle of rotation about <tt>y</tt>-axis.
     * @param rz
     *            angle of rotation about <tt>z</tt>-axis.
     * @return transformation matrix.
     */
    public static Matrix createTransformation(double px, double py, double pz, double rx, double ry, double rz)
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

    /**
     * <p>
     * Creates new transformation matrix in 3D space with size {@code 4×4} with
     * given point and orientation about all axes.
     * </p>
     * 
     * @param end
     *            coordinates of an endpoint in 3D space.
     * @param angle
     *            about all axes in 3D space.
     * @return transformation matrix.
     * @see Matrix#createTransformation(double, double, double, double, double,
     *      double)
     */
    public static Matrix createTransformation(Point3D end, Orientation angle)
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

    /**
     * Creates new square identity matrix with given size.
     * 
     * @param n
     *            dimensions of a matrix.
     * @return identity matrix.
     */
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

    /**
     * Calculates a determinant value of square matrix.
     * 
     * @return determinant value.
     * @throws IllegalArgumentException
     *             if given matrix is not a square matrix.
     */
    public double determinant()
    {
        if(rows != columns)
        {
            throw new IllegalArgumentException("not a square matrix: " + rows + "x" + columns);
        }

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
            result += changeSign(i) * values[0][i] * submatrix(0, i).determinant();
        }
        return result;
    }

    private static int changeSign(int i)
    {
        return i % 2 == 0 ? 1 : -1;
    }

    /**
     * Creates new smaller matrix, without given row and column excluded.
     * 
     * @param excludingRow
     *            row to remove.
     * @param excludingColumn
     *            column to remove.
     * @return submatrix of a given matrix.
     * @throws IllegalArgumentException
     *             if given matrix is not a square matrix or <br>
     *             if row or column is negative or higher than row or column
     *             count of matrix.
     */
    public Matrix submatrix(int excludingRow, int excludingColumn)
    {
        if(excludingRow < 0)
        {
            throw new IllegalArgumentException("excludingRow < 0");
        }
        if(excludingColumn < 0)
        {
            throw new IllegalArgumentException("excludingColumn < 0");
        }

        if(excludingRow >= rows)
        {
            throw new IllegalArgumentException("excludingRow >= rows: " + excludingRow + " >= " + rows);
        }
        if(excludingColumn >= columns)
        {
            throw new IllegalArgumentException("excludingColumn >= columns: " + excludingColumn + " >= " + columns);
        }
        if(rows != columns)
        {
            throw new IllegalArgumentException("not a square matrix: " + rows + "x" + columns);
        }

        Matrix result = new Matrix(rows - 1, columns - 1);
        int r = -1;
        for (int i = 0; i < rows; i++)
        {
            if(i == excludingRow)
            {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < rows; j++)
            {
                if(j == excludingColumn)
                {

                    continue;
                }
                c++;
                result.values[r][c] = values[i][j];
            }

        }
        return result;
    }

    /**
     * <p>
     * Creates new matrix which is a cofactor of a given matrix.
     * </p>
     * <p>
     * The cofactor of a matrix <tt>A</tt> is matrix <tt>C</tt> that the value
     * of element <tt>C<sub>ij</sub></tt> equals the determinant of a matrix
     * created by removing row <tt>i</tt> and column <tt>j</tt> from matrix
     * <tt>A</tt>.
     * </p>
     * 
     * @return cofactor of a matrix.
     * @throws IllegalArgumentException
     *             if given matrix is not a square matrix
     */
    public Matrix cofactor()
    {
        if(rows != columns)
        {
            throw new IllegalArgumentException("not a square matrix: " + rows + "x" + columns);
        }
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.values[i][j] = changeSign(i) * changeSign(j) * submatrix(i, j).determinant();
            }
        }
        return result;
    }

    /**
     * <p>
     * Creates new translation matrix in 3D space with size {@code 4×4}.
     * </p>
     * 
     * @param end Endpoint coordinates.
     * 
     * @return translation matrix.
     * 
     * @see #createTranslation(double, double, double)
     */
    public static Matrix createTranslation(Point3D end)
    {
        return createTranslation(end.x, end.y, end.y);
    }

}
