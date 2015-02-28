package sk.fiit.jim.agent.models;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * BodyPart.java
 *
 * @author Jan Hudec
 * @author Metod Rybar
 *
 */
public enum BodyPart {
	// body_part (parent, anchor_or_translation, mass, joint, shape)
	TORSO(null, Vector3D.ZERO_VECTOR, 1.2171, null, Shape.BOX, 0.1, 0.1, 0.18),

	NECK(TORSO, Vector3D.cartesian(0, 0, 0.09), 0.05, Joint.HE1,
			Shape.CYLINDER, 0.08, 0.015), HEAD(NECK, Vector3D.cartesian(0, 0,
			0.065), 0.35, Joint.HE2, Shape.SPHERE, 0.065),

	RSHOULDER(TORSO, Vector3D.cartesian(0.098, 0, 0.075), 0.07, Joint.RAE1,
			Shape.SPHERE, 0.01), LSHOULDER(TORSO, Vector3D.cartesian(-0.098, 0,
			0.075), 0.07, Joint.LAE1, Shape.SPHERE, 0.01),

	RUPPERARM(RSHOULDER, Vector3D.cartesian(0.01, 0.02, 0), 0.15, Joint.RAE2,
			Shape.BOX, 0.07, 0.08, 0.06), LUPPERARM(LSHOULDER, Vector3D
			.cartesian(-0.01, 0.02, 0), 0.15, Joint.LAE2, Shape.BOX, 0.07,
			0.08, 0.06),

	RELBOW(RUPPERARM, Vector3D.cartesian(-0.01, 0.07, 0.009), 0.035,
			Joint.RAE3, Shape.SPHERE, 0.01), LELBOW(LUPPERARM, Vector3D
			.cartesian(0.01, 0.07, 0.009), 0.035, Joint.LAE3, Shape.SPHERE,
			0.01),

	RLOWERARM(RELBOW, Vector3D.cartesian(0, 0.05, 0), 0.2, Joint.RAE4,
			Shape.BOX, 0.05, 0.11, 0.05), 
	LLOWERARM(LELBOW, Vector3D.cartesian(
			0, 0.05, 0), 0.2, Joint.LAE4, Shape.BOX, 0.05, 0.11, 0.05),

	RHIP1(TORSO, Vector3D.cartesian(0.055, -0.01, -0.115), 0.09, Joint.RLE1,
			Shape.SPHERE, 0.01), // bedro
	LHIP1(TORSO, Vector3D.cartesian(-0.055, -0.01, -0.115), 0.09, Joint.LLE1,
			Shape.SPHERE, 0.01),

	RHIP2(RHIP1, Vector3D.ZERO_VECTOR, 0.125, Joint.RLE2, Shape.SPHERE, 0.01), LHIP2(
			LHIP1, Vector3D.ZERO_VECTOR, 0.125, Joint.LLE2, Shape.SPHERE, 0.01),

	RTHIGH(RHIP2, Vector3D.cartesian(0, 0.01, -0.04), 0.275, Joint.RLE3,
			Shape.BOX, 0.07, 0.07, 0.14), // stehno
	LTHIGH(LHIP2, Vector3D.cartesian(0, 0.01, -0.04), 0.275, Joint.LLE3,
			Shape.BOX, 0.07, 0.07, 0.14),

	RSHANK(RTHIGH, Vector3D.cartesian(0, 0.005, -0.125), 0.225, Joint.RLE4,
			Shape.BOX, 0.08, 0.07, 0.11), // noha
	LSHANK(LTHIGH, Vector3D.cartesian(0, 0.005, -0.125), 0.225, Joint.LLE4,
			Shape.BOX, 0.08, 0.07, 0.11),

	RANKLE(RSHANK, Vector3D.cartesian(0, -0.01, -0.055), 0.125, Joint.RLE5,
			Shape.SPHERE, 0.01), LANKLE(LSHANK, Vector3D.cartesian(0, -0.01,
			-0.055), 0.125, Joint.LLE5, Shape.SPHERE, 0.01),

	RFOOT(RANKLE, Vector3D.cartesian(0, 0.03, -0.035), 0.2, Joint.RLE6,
			Shape.BOX, 0.08, 0.16, 0.03), LFOOT(LANKLE, Vector3D.cartesian(0,
			0.03, -0.035), 0.2, Joint.LLE6, Shape.BOX, 0.08, 0.16, 0.03);

	private final BodyPart parent;
	private final Vector3D translation;
	private final double mass;
	private final Joint joint;
	private final Shape shape;
	private final double[] shapeParams;

	private BodyPart(BodyPart parent, Vector3D translation, double mass,
			Joint joint, Shape shape, double... shapeParams) {
		this.parent = parent;
		this.translation = translation;
		this.mass = mass;
		this.joint = joint;
		this.shape = shape;
		this.shapeParams = shapeParams;
	}

	public BodyPart getParent() {
		return parent;
	}

	public Vector3D getTranslation() {
		return translation;
	}

	public double getMass() {
		return mass;
	}

	public Shape getShape() {
		return shape;
	}

	public double[] getShapeParams() {
		return shapeParams;
	}

	public Joint getJoint() {
		return this.joint;
	}

	private static final Map<BodyPart, Joint> bodyPartToJoint = new HashMap<BodyPart, Joint>();
	private static final Map<Joint, BodyPart> jointToBodyPart = new HashMap<Joint, BodyPart>();
	static {
		for (BodyPart bodyPart : values()) {
			bodyPartToJoint.put(bodyPart, bodyPart.joint);
			jointToBodyPart.put(bodyPart.joint, bodyPart);
		}
	}

	public static BodyPart fromJoint(Joint joint) {
		return jointToBodyPart.get(joint);
	}

	public static Joint toJoint(BodyPart bodyPart) {
		return bodyPartToJoint.get(bodyPart);
	}

	public static BodyPart getRoot() {
		return TORSO;
	}

	public static BodyPart withCamera() {
		return HEAD;
	}

	public static BodyPart withGyroscope() {
		return TORSO;
	}

	public static BodyPart withAccelerometer() {
		return TORSO;
	}

	/**
	 * Returns relative position of the body part to root
	 *
	 * @author Jan Hudec
	 * @author Metod Rybar
	 * @param bodyPart
	 *            - bodyPart for which position is calculated
	 * @param jointAngles
	 *            - Map of all angles for all joints of body part
	 * @param position
	 *            - vector for body part position
	 * @return relative position of the body part
	 *
	 */
	public static Vector3D relativePositionToRoot(BodyPart bodyPart,
			Map<Joint, Double> jointAngles, Vector3D position) {
		BodyPart parent = bodyPart.getParent();
		if (parent == null)
			return position;

		Joint joint = bodyPart.getJoint();
		Vector3D parentLocation = bodyPart.getTranslation().negate();

		position = position.subtract(joint.getAnchor());
		parentLocation = parentLocation.subtract(joint.getAnchor());

		double angle = jointAngles.get(joint);
		position = position.rotateOver(joint.getAxis(), angle);
		parentLocation = parentLocation.rotateOver(joint.getAxis(), angle);

		position = position.subtract(parentLocation);

		return relativePositionToRoot(parent, jointAngles, position);
	}

	/**
	 * Get relative body position
	 *
	 * @author Jan Hudec
	 * @author Metod Rybar
	 * @param jointAngles
	 *            - Map of all angles for all joints of body part
	 * @param relPosition
	 *            - vectors for body parts positions
	 *
	 */
	public static void computeRelativePositionsToCamera(
			Map<Joint, Double> jointAngles, Map<BodyPart, Vector3D> relPositions) {
		Vector3D cameraPositionRelativeToTorso = relativePositionToRoot(HEAD,
				jointAngles, Vector3D.ZERO_VECTOR);
		double h2Angle = jointAngles.get(HEAD.getJoint()), h1Angle = jointAngles
				.get(NECK.getJoint());
		Vector3D torsoRelativePositionToCamera = vectorFromTorsoToCameraCoordinates(
				cameraPositionRelativeToTorso, h1Angle, h2Angle);
		for (BodyPart bodyPart : values()) {
			Vector3D relativePositionToTorso = relativePositionToRoot(bodyPart,
					jointAngles, Vector3D.ZERO_VECTOR);
			Vector3D relativePositionToCamera = vectorFromTorsoToCameraCoordinates(
					relativePositionToTorso, h1Angle, h2Angle).subtract(
					torsoRelativePositionToCamera);
			relPositions.put(bodyPart, relativePositionToCamera);
		}
	}

	/**
	 * Returns relative position of the body part to body
	 *
	 * @author Jan Hudec
	 * @author Metod Rybar
	 * @param jointAngles
	 *            - Map of all angles for all joints of body part
	 * @param relPosition
	 *            - vectors for body parts positions
	 *
	 */
	public static void computeRelativePositionsToTorso(
			Map<Joint, Double> jointAngles, Map<BodyPart, Vector3D> relPositions) {
		for (BodyPart bodyPart : values()) {
			Vector3D relativePositionToTorso = relativePositionToRoot(bodyPart,
					jointAngles, Vector3D.ZERO_VECTOR);
			relPositions.put(bodyPart, relativePositionToTorso);
		}
	}

	/**
	 * Calculates vector from body to camera coordinates
	 *
	 * @author Jan Hudec
	 * @author Metod Rybar
	 * @param vector
	 * @param h1Angle
	 * @param h2Angle
	 * @return vector from body to camera
	 */
	public static Vector3D vectorFromTorsoToCameraCoordinates(Vector3D vector,
			double h1Angle, double h2Angle) {
		return vector.rotateOverZ(h1Angle).rotateOverX(h2Angle);
	}

}
