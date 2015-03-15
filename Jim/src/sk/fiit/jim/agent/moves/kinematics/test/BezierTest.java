package sk.fiit.jim.agent.moves.kinematics.test;

import java.util.ArrayList;
import java.util.List;

import sk.fiit.jim.agent.moves.kinematics.Bezier;
import sk.fiit.robocup.library.geometry.Point3D;

public class BezierTest
{
    public static void main(String[] args)
    {
        Point3D control1 = new Point3D(120, 160, 0);
        Point3D control2 = new Point3D(35, 200, 0);
        Point3D control3 = new Point3D(220, 260, 0);
        Point3D control4 = new Point3D(220, 40, 0);
        
        Point3D result = Bezier.calculateCubic(0.5, control1, control2, control3, control4);
        System.out.println(result);
        
        List<Point3D> controlPoints = new ArrayList<>();
        controlPoints.add(control1);
        controlPoints.add(control2);
        controlPoints.add(control3);
        controlPoints.add(control4);
        result = Bezier.calculateNth(3, controlPoints , 0.5);
        System.out.println(result);
    }
}
