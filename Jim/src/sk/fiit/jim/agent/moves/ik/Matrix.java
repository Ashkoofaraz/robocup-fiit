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
    static final Matrix AbaseLeftArm = createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);

    static final Matrix T01LeftArm = createDHTransformation(0, -PI / 2, 0, 0); // a,
                                                                               // alpha,
                                                                               // d,
                                                                               // theta

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

    static final Matrix T56LeftLeg = createDHTransformation(0, -PI / 2, 0, 0);

    static final Matrix RzLeftLeg = createRotationZ(PI);

    static final Matrix RyLeftLeg = createRotationY(-PI / 2);

    static final Matrix AbaseRightLeg = Matrix.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);

    static final Matrix invAbaseRightLeg = AbaseRightLeg.inverse();

    static final Matrix AendRightLeg = createTranslation(0, 0, -FOOT_HEIGHT);

    static final Matrix invAendRightLeg = AendRightLeg.inverse();

    static final Matrix T34RightLeg = createDHTransformation(-THIGH_LENGHT, 0, 0, 0);

    static final Matrix T45RightLeg = createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);

    static final Matrix T56RightLeg = createDHTransformation(0, -PI / 2, 0, 0);

    static final Matrix RzRightLeg = createRotationZ(PI);

    static final Matrix RyRightLeg = createRotationY(-PI / 2);

    static final Matrix ROTATION_X_PI_4 = createRotationX(PI / 4);

    static final Matrix ROTATION_X_PI_4_MINUS = createRotationX(-PI / 4);

    static final Matrix ROTATION_Z_PI_MINUS = createRotationZ(PI / 2);

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
            result += changeSign(i) * values[0][i] * submatrix(0, i).determinant();
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
        Matrix result = new Matrix(rows - 1, columns - 1);
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

    // The cofactor of a matrix A is matrix C that the value of element Cij
    // equals the determinant of a matrix created by removing row i and column j
    // from matrix A. Here is the method that calculates the cofactor matrix
    public Matrix cofactor()
    {
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

}
