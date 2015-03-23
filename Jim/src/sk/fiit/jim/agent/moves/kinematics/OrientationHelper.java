package sk.fiit.jim.agent.moves.kinematics;

import sk.fiit.robocup.library.geometry.Point3D;

public class OrientationHelper
{
    public static double calculatePointPointAngle(Point3D point1, Point3D point2)
    {
        double a = Math.abs(point2.x - point1.x);
        double b = Math.abs(point2.y - point1.y);
        return Math.atan(b / a);
    }
}

