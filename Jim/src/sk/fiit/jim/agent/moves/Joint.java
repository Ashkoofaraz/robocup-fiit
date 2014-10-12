package sk.fiit.jim.agent.moves;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import sk.fiit.robocup.library.geometry.Vector3D;

/**
 *  Effector.java
 *  
 *  Enumeration of joints robot has. Is also capable of translating incoming
 *  joint IDs into appropriate {@link Joint}. Holds information about its
 *  available angle range and allows programmer to trim an angle to this range.
 *  
 *  ANGLES SHOULD BE SUPPLIED IN DEG SYSTEM
 *
 *@Title	Jim
 *@author	marosurbanec
 */
@SuppressWarnings("serial")
public enum Joint implements Serializable {
	//range, anchor,axis
	HE1 (-120.0, 120.0, Vector3D.ZERO_VECTOR, Vector3D.Z_AXIS),
	HE2 (-45.0, 45.0, Vector3D.cartesian(0, 0, -0.005), Vector3D.X_AXIS),
	
	RLE1 (-90.0, 1.0, Vector3D.ZERO_VECTOR, Vector3D.cartesian(-0.7071, 0, 0.7071)),
	RLE2 (-45.0, 25.0, Vector3D.ZERO_VECTOR, Vector3D.Y_AXIS),
	RLE3 (-25.0, 100.0, Vector3D.cartesian(0.0, 0.010, -0.040).negate(), Vector3D.X_AXIS),
	RLE4 (-130.0, 1.0, Vector3D.cartesian(0, -0.010, 0.045),Vector3D.X_AXIS),
	RLE5 (-45.0, 75.0, Vector3D.ZERO_VECTOR, Vector3D.X_AXIS),
	RLE6 (-25.0, 45.0, Vector3D.cartesian(0, 0.030, -0.035).negate(),Vector3D.Y_AXIS),
	
	RAE1 (-120.0, 120.0, Vector3D.ZERO_VECTOR, Vector3D.X_AXIS),
	RAE2 (-95.0, 1.0, Vector3D.cartesian(0.01, 0.02, 0).negate(), Vector3D.Z_AXIS),
	RAE3(-120.0, 120.0, Vector3D.ZERO_VECTOR, Vector3D.Y_AXIS),
	RAE4(-1.0, 90.0, Vector3D.cartesian(0, 0.05, 0).negate(), Vector3D.Z_AXIS),

	
	LLE1 (-90.0, 1.0, Vector3D.ZERO_VECTOR,Vector3D.cartesian(-0.7071, 0, -0.7071)),
	LLE2 (-25.0, 45.0, Vector3D.ZERO_VECTOR, Vector3D.Y_AXIS),
	LLE3 (-25.0, 100.0, Vector3D.cartesian(0.0, 0.010, -0.040).negate(), Vector3D.X_AXIS),
	LLE4 (-130.0, 1.0, Vector3D.cartesian(0, -0.010, 0.045),Vector3D.X_AXIS),
	LLE5 (-45.0, 75.0, Vector3D.ZERO_VECTOR, Vector3D.X_AXIS),
	LLE6 (-45.0, 25.0, Vector3D.cartesian(0, 0.030, -0.035).negate(),Vector3D.Y_AXIS),
	
	LAE1 (-120.0, 120.0, Vector3D.ZERO_VECTOR, Vector3D.X_AXIS),
	LAE2 (-1.0, 95.0, Vector3D.cartesian(-0.01, 0.02, 0).negate(), Vector3D.Z_AXIS),
	LAE3(-120.0, 120.0, Vector3D.ZERO_VECTOR, Vector3D.Y_AXIS),
	LAE4(-90.0, 1.0, Vector3D.cartesian(0, 0.05, 0).negate(), Vector3D.Z_AXIS);

	
	
	
	private double lowerLimit;
	private double upperLimit;
	private Vector3D anchor;
	private Vector3D axis;
	
	private Joint(double lowerLimit, double upperLimit, Vector3D anchor, Vector3D axis){
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.anchor = anchor;
		this.axis = axis;
	}
	public final double getLow(){
		return lowerLimit;
	}
	
	public final double getUp(){
		return upperLimit;
	}
	
	private static Map<String, Joint> serverNotation = new HashMap<String, Joint>(){{
		put("hj1", HE1);
		put("hj2", HE2);
		
		put("raj1", RAE1);
		put("raj2", RAE2);
		put("raj3", RAE3);
		put("raj4", RAE4);

		put("laj1", LAE1);
		put("laj2", LAE2);
		put("laj3", LAE3);
		put("laj4", LAE4);
		
		put("rlj1", RLE1);
		put("rlj2", RLE2);
		put("rlj3", RLE3);
		put("rlj4", RLE4);
		put("rlj5", RLE5);
		put("rlj6", RLE6);
		
		put("llj1", LLE1);
		put("llj2", LLE2);
		put("llj3", LLE3);
		put("llj4", LLE4);
		put("llj5", LLE5);
		put("llj6", LLE6);
	}};
	
	/**
	 * Returns Joint from server by specified string id. 
	 *
	 * @param jointId
	 * @return
	 */
	public static Joint fromServerNotation(String jointId){
		return serverNotation.get(jointId.toLowerCase());
	}
	/**
	 * returns key for specific joint
	 * @param value
	 * @return
	 */
	public static Joint getKey(String value){
		for(String key: serverNotation.keySet()){
			if(serverNotation.get(key).toString().equals(value)){
				return serverNotation.get(key);
			}
		}
		return null;
	}
	
	/**
	 * Returns lower limit of Joint angle if specified angle parameter is 
	 * less than lower limit or upper limit of Joint angle if specified angle
	 * parameter is greater than upper limit. Otherwise returns specified angle.
	 *
	 * @param angleInXml
	 * @return
	 */
	public double trim(double angleInXml){
		if (angleInXml < lowerLimit) return lowerLimit;
		if (angleInXml > upperLimit) return upperLimit;
		return angleInXml;
	}
	
	public Vector3D getAnchor(){
		return this.anchor;
	}
	public Vector3D getAxis(){
		return this.axis;
	}
	
}