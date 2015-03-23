package sk.fiit.jim.agent.moves.kinematics.test;

import sk.fiit.jim.agent.moves.kinematics.OrientationHelper;
import sk.fiit.robocup.library.geometry.Point3D;

public class OrientationHelperTest
{
    public static void main(String[] args)
    {
        System.out.println(OrientationHelper.calculatePointPointAngle(new Point3D(-3.2, 0, -0.1), new Point3D(0, 0, -0.1)));
        System.out.println(OrientationHelper.calculatePointPointAngle(new Point3D(-2, 2, -0.1), new Point3D(0, 0, -0.1)));
        System.out.println(OrientationHelper.calculatePointPointAngle(new Point3D(-3.2, 1, -0.1), new Point3D(0, 0, -0.1)));
    }
}
