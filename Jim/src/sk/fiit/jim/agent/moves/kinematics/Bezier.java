package sk.fiit.jim.agent.moves.kinematics;

import java.util.List;

import sk.fiit.robocup.library.geometry.Point3D;

public class Bezier
{
    public static Point3D calculateCubic(double t, Point3D control1, Point3D control2, Point3D  control3, Point3D control4)
    {
        double x = control1.x * Math.pow(1 - t, 3) + 3 * control2.x * Math.pow(1 - t, 2) * t + 3 * control3.x * (1 - t) * Math.pow(t, 2) + control4.x * Math.pow(t, 3);
        double y = control1.y * Math.pow(1 - t, 3) + 3 * control2.y * Math.pow(1 - t, 2) * t + 3 * control3.y * (1 - t) * Math.pow(t, 2) + control4.y * Math.pow(t, 3);
        double z = control1.z * Math.pow(1 - t, 3) + 3 * control2.z * Math.pow(1 - t, 2) * t + 3 * control3.z * (1 - t) * Math.pow(t, 2) + control4.z * Math.pow(t, 3);
        Point3D result = new Point3D(x, y, z);
        return result;
    }
    
    public static Point3D calculateNth(int n, List<Point3D> controlPoints, double t)
    {
        if(n + 1 != controlPoints.size()) 
        {
            throw new IllegalArgumentException("Bezier curve of nth dimension must have n+1 control points. n: " + n + ", points: " + controlPoints.size());
        }
        double x = 0;
        double y = 0;
        double z = 0;
        
        for(int i = 0; i <= n; i++)
        {
            Point3D controlPoint = controlPoints.get(i);
            int binomial = binomial(n, i);
            x += binomial * Math.pow(1 - t, n - i) * Math.pow(t, i) * controlPoint.x;
            y += binomial * Math.pow(1 - t, n - i) * Math.pow(t, i) * controlPoint.y;
            z += binomial * Math.pow(1 - t, n - i) * Math.pow(t, i) * controlPoint.z;
        }
        Point3D result = new Point3D(x, y, z);
        return result;
    }
    
    private static int binomial(int n, int m) 
    {
        return factorial(n) / (factorial(n - m) * factorial(m));
    }
    
    private static int factorial(int n)
    {
        if(n == 0)
        {
            return 1;
        }
        return n * factorial(n-1);
    }
}
