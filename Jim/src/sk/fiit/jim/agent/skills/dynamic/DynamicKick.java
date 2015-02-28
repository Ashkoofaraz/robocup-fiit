package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * @author Pidanic
 *
 */
public class DynamicKick extends DynamicMove{

	static final class Tuple {
		List<Point3D> points;
		List<Orientation> orientations;
	}
	
	@Override
	public LowSkill pickLowSkill() {
	    System.out.println("position: " +AgentModel.getInstance().getPosition());
	    System.out.println("LLE1 Angle: " +AgentModel.getInstance().getJointAngle(Joint.LLE1));
//		Tuple la = createSequenceLeftArm();
		Tuple la = createSequenceLeftLeg();
				
//		LowSkill ls = createDynamicMove("leftArm", la.points, la.orientations);
		LowSkill ls = createDynamicMove("leftLeg", la.points, la.orientations);
		return ls;
	}

	@Override
	public void checkProgress() throws Exception {
	}
	
	private Tuple createSequenceLeftArm() {
		Point3D point1 = new Point3D(195, 98, 75);
		Orientation orientation1 = Orientation.fromRadians(0.0, 0.0, 0.0);

		Point3D point2 = new Point3D(0, 293, 75);
		Orientation orientation2 = Orientation.fromRadians(0.0, 0.0, 0.0);

		Point3D point3 = new Point3D(0, 98, -120);
		Orientation orientation3 = Orientation.fromDegrees(45, 90, 45);

		Point3D point4 = new Point3D(0, 98, 270);
		Orientation orientation4 = Orientation.fromRadians(0.0, 0.0, 0.0);

		Point3D point5 = new Point3D(170, 98, 100);
		Orientation orientation5 = Orientation.fromRadians(0.0, 0.0, 0.0);

		Point3D point6 = new Point3D(123.00, 203.86, -26.25);
		Orientation orientation6 = Orientation.fromRadians(0.93, 0.42, 0.84);

		List<Point3D> points = new ArrayList<>();
		List<Orientation> orientations = new ArrayList<>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		points.add(point5);
		points.add(point6);

		orientations.add(orientation1);
		orientations.add(orientation2);
		orientations.add(orientation3);
		orientations.add(orientation4);
		orientations.add(orientation5);
		orientations.add(orientation6);
		
		Tuple retVal = new Tuple();
		retVal.points = points;
		retVal.orientations = orientations;
		return retVal;
	}
	
	private Tuple createSequenceLeftLeg() {
		Point3D point1 = new Point3D(0.0, 55.0, -385.0);
		Orientation orientation1 = Orientation.fromRadians(0.0, 0.0, 0.0);

//		Point3D point2 = new Point3D(0, 245, -305);
//		Orientation orientation2 = Orientation.fromRadians(0.0, 0.0, 0.0);
//
//		Point3D point3 = new Point3D(190, 55, -305);
//		Orientation orientation3 = Orientation.fromDegrees(0, 90, 45);
//
//		Point3D point4 = new Point3D(188, 74, -303);
//		Orientation orientation4 = Orientation.fromRadians(0.39, -0.79, 0);
//
//		Point3D point5 = new Point3D(234.85, 55.00, -199.85);
//		Orientation orientation5 = Orientation.fromRadians(-0.18, -1.57, 0.18);

		List<Point3D> points = new ArrayList<>();
		List<Orientation> orientations = new ArrayList<>();
		points.add(point1);
//		points.add(point2);
//		points.add(point3);
//		points.add(point4);
//		points.add(point5);

		orientations.add(orientation1);
//		orientations.add(orientation2);
//		orientations.add(orientation3);
//		orientations.add(orientation4);
//		orientations.add(orientation5);

		Tuple retVal = new Tuple();
		retVal.points = points;
		retVal.orientations = orientations;
		return retVal;
	}

}