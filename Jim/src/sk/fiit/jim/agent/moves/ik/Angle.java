package sk.fiit.jim.agent.moves.ik;

class Angle
{
    private final double ax;

    private final double ay;

    private final double az;

    public Angle(double ax, double ay, double az)
    {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }
    
    public double getAx()
    {
        return ax;
    }
    
    public double getAy()
    {
        return ay;
    }
    
    public double getAz()
    {
        return az;
    }
}
