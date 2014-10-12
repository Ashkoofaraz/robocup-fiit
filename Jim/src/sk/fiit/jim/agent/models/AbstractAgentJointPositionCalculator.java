package sk.fiit.jim.agent.models;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.parsing.ForceReceptor;
import sk.fiit.jim.agent.parsing.ParsedData;
import sk.fiit.robocup.library.geometry.Vector3D;
import sk.fiit.robocup.library.representations.Correction;

public abstract class AbstractAgentJointPositionCalculator {
	
//	private static AbstractAgentJointPositionCalculator instance = new AbstractAgentJointPositionCalculator();	
	
	private static Logger logger = Logger.getLogger(AbstractAgentJointPositionCalculator.class.getName());
	
//	protected static final Double FOOT_ONGROUND_POSITOIN =  0.04;
//	
//	protected static final Double SIDE_FOOT_ONGORUND_POSITION = 0.03;
//		
//	static boolean firstSet = false;
	
	protected static Map<Joint, Double> updatetJoint = new HashMap<Joint, Double>();
	

//	public static AbstractAgentJointPositionCalculator getInstance(){
//				return instance;
//		 	}
	
	
//	public Vector3D zmpPosition(){
//		return AgentModel.getInstance().getZeroMomentPoint();
//	}
//	
//	public ParsedData getLastDataReceived(){
//		return AgentModel.getInstance().getLastDataReceived();
//	}
	
//	public ForceReceptor getForceReceptor(){
//		return AgentModel.getInstance().getForceReceptor();
//	}
	
	
	
//	public static Map<Joint, Double> updateJointAbout(Map<Joint,Double> agentsJoints){
////		footStabilization();
////		handStabilization();
////		AgentModel.getInstance().footStabilization(null,null);
////		AgentModel.getInstance().handStabilization();
//		
//		
//		Map<Joint, Double> joint = new HashMap<Joint, Double>();
//
//		
//		joint.put(Joint.HE1, agentsJoints.get(Joint.HE1)+updatetJoint.get(Joint.HE1));
//		joint.put(Joint.HE2, agentsJoints.get(Joint.HE2)+updatetJoint.get(Joint.HE2));
//		joint.put(Joint.LAE1, agentsJoints.get(Joint.LAE1)+updatetJoint.get(Joint.LAE1));
//		joint.put(Joint.LAE2, agentsJoints.get(Joint.LAE2)+updatetJoint.get(Joint.LAE2));
//		joint.put(Joint.LAE3, agentsJoints.get(Joint.LAE3)+updatetJoint.get(Joint.LAE3));
//		joint.put(Joint.LAE4, agentsJoints.get(Joint.LAE4)+updatetJoint.get(Joint.LAE4));
//		joint.put(Joint.RAE1, agentsJoints.get(Joint.RAE1)+updatetJoint.get(Joint.RAE1));
//		joint.put(Joint.RAE2, agentsJoints.get(Joint.RAE2)+updatetJoint.get(Joint.RAE2));
//		joint.put(Joint.RAE3, agentsJoints.get(Joint.RAE3)+updatetJoint.get(Joint.RAE3));
//		joint.put(Joint.RAE4, agentsJoints.get(Joint.RAE4)+updatetJoint.get(Joint.RAE4));
//		joint.put(Joint.LLE1, agentsJoints.get(Joint.LLE1)+updatetJoint.get(Joint.LLE1));
//		joint.put(Joint.LLE2, agentsJoints.get(Joint.LLE2)+updatetJoint.get(Joint.LLE2));
//		joint.put(Joint.LLE3, agentsJoints.get(Joint.LLE3)+updatetJoint.get(Joint.LLE3));
//		joint.put(Joint.LLE4, agentsJoints.get(Joint.LLE4)+updatetJoint.get(Joint.LLE4));
//		joint.put(Joint.LLE5, agentsJoints.get(Joint.LLE5)+updatetJoint.get(Joint.LLE5));
//		joint.put(Joint.LLE6, agentsJoints.get(Joint.LLE6)+updatetJoint.get(Joint.LLE6));
//		joint.put(Joint.RLE1, agentsJoints.get(Joint.RLE1)+updatetJoint.get(Joint.RLE1));
//		joint.put(Joint.RLE2, agentsJoints.get(Joint.RLE2)+updatetJoint.get(Joint.RLE2));
//		joint.put(Joint.RLE3, agentsJoints.get(Joint.RLE3)+updatetJoint.get(Joint.RLE3));
//		joint.put(Joint.RLE4, agentsJoints.get(Joint.RLE4)+updatetJoint.get(Joint.RLE4));
//		joint.put(Joint.RLE5, agentsJoints.get(Joint.RLE5)+updatetJoint.get(Joint.RLE5));
//		joint.put(Joint.RLE6, agentsJoints.get(Joint.RLE6)+updatetJoint.get(Joint.RLE6));
//		
//		
//		return joint;
//	}
	
	protected void setInitUpdateJoint(){
		updatetJoint.put(Joint.HE1, 0.0);
		updatetJoint.put(Joint.HE2, 0.0);
		updatetJoint.put(Joint.HE1, 0.0);
		updatetJoint.put(Joint.HE2, 0.0);
		updatetJoint.put(Joint.LAE1, 0.0);
		updatetJoint.put(Joint.LAE2, 0.0);
		updatetJoint.put(Joint.LAE3, 0.0);
		updatetJoint.put(Joint.LAE4, 0.0);
		updatetJoint.put(Joint.RAE1, 0.0);
		updatetJoint.put(Joint.RAE2, 0.0);
		updatetJoint.put(Joint.RAE3, 0.0);
		updatetJoint.put(Joint.RAE4, 0.0);
		updatetJoint.put(Joint.LLE1, 0.0);
		updatetJoint.put(Joint.LLE2, 0.0);
		updatetJoint.put(Joint.LLE3, 0.0);
		updatetJoint.put(Joint.LLE4, 0.0);
		updatetJoint.put(Joint.LLE5, 0.0);
		updatetJoint.put(Joint.LLE6, 0.0);
		updatetJoint.put(Joint.RLE1, 0.0);
		updatetJoint.put(Joint.RLE2, 0.0);
		updatetJoint.put(Joint.RLE3, 0.0);
		updatetJoint.put(Joint.RLE4, 0.0);
		updatetJoint.put(Joint.RLE5, 0.0);
		updatetJoint.put(Joint.RLE6, 0.0);
	}
	
	public abstract void footStabilization(Correction correction, Vector3D[] footVector, Vector3D zmp);
	
	public abstract void handStabilization(Correction correction, Vector3D[] footVector, Vector3D zmp);
	
	public abstract Map<Joint, Double> updateJointAbout(Map<Joint,Double> agentsJoints);
	
	
}
