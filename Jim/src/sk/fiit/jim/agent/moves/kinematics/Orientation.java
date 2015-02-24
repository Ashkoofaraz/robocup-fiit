package sk.fiit.jim.agent.moves.kinematics;

/**
 * 
 * Immutable class that holds rotation angles of corresponding axes.
 * 
 * @author Pidanic
 *
 */
public final class Orientation
{
    private final double ax;

    private final double ay;

    private final double az;

    private Orientation(double ax, double ay, double az)
    {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public static Orientation fromRadians(double ax, double ay, double az)
    {
        return new Orientation(ax, ay, az);
    }

    public static Orientation fromDegrees(double ax, double ay, double az)
    {
        return new Orientation(Math.toRadians(ax), Math.toRadians(ay), Math.toRadians(az));
    }

    public double getAxRadians()
    {
        return ax;
    }

    public double getAyRadians()
    {
        return ay;
    }

    public double getAzRadians()
    {
        return az;
    }

    @Override
    public String toString()
    {
        return String.format("%1$.4f %2$.4f %3$.4f radians", ax, ay, az);
    }
}
