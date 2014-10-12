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

public class AgentJointPositionCalculator extends AbstractAgentJointPositionCalculator{
	
	private static AgentJointPositionCalculator instance = new AgentJointPositionCalculator();	
	
	private static Logger logger = Logger.getLogger(AgentJointPositionCalculator.class.getName());
	
	private static final Double FOOT_ONGROUND_POSITOIN =  0.04;
	
	private static final Double SIDE_FOOT_ONGORUND_POSITION = 0.03;
		
	private Vector3D[] footVector;
	
	private Vector3D zmp;
	
	boolean firstSet = false;
	
	Map<Joint, Double> updatetJoint = new HashMap<Joint, Double>();
	

	public static AgentJointPositionCalculator getInstance(){
				return instance;
		 	}
	
	
	public Vector3D zmpPosition(){
		return AgentModel.getInstance().getZeroMomentPoint();
	}
	
//	public ParsedData getLastDataReceived(){
//		return AgentModel.getInstance().getLastDataReceived();
//	}
	
//	public ForceReceptor getForceReceptor(){
//		return AgentModel.getInstance().getForceReceptor();
//	}
	
	
	
	public Map<Joint, Double> updateJointAbout(Map<Joint,Double> jointAngles){
//		footStabilization();
//		handStabilization();
		Double[] correct = {0.25,0.25};
		Double[] sideCorect = {0.01,0.01};
		
//		footStabilization(correct, sideCorect);
//		handStabilization();
		if(updatetJoint.isEmpty()){
			setInitUpdateJoint();
		}
		jointAngles.put(Joint.HE1, jointAngles.get(Joint.HE1)+updatetJoint.get(Joint.HE1));
		jointAngles.put(Joint.HE2, jointAngles.get(Joint.HE2)+updatetJoint.get(Joint.HE2));
		jointAngles.put(Joint.LAE1, jointAngles.get(Joint.LAE1)+updatetJoint.get(Joint.LAE1));
		jointAngles.put(Joint.LAE2, jointAngles.get(Joint.LAE2)+updatetJoint.get(Joint.LAE2));
		jointAngles.put(Joint.LAE3, jointAngles.get(Joint.LAE3)+updatetJoint.get(Joint.LAE3));
		jointAngles.put(Joint.LAE4, jointAngles.get(Joint.LAE4)+updatetJoint.get(Joint.LAE4));
		jointAngles.put(Joint.RAE1, jointAngles.get(Joint.RAE1)+updatetJoint.get(Joint.RAE1));
		jointAngles.put(Joint.RAE2, jointAngles.get(Joint.RAE2)+updatetJoint.get(Joint.RAE2));
		jointAngles.put(Joint.RAE3, jointAngles.get(Joint.RAE3)+updatetJoint.get(Joint.RAE3));
		jointAngles.put(Joint.RAE4, jointAngles.get(Joint.RAE4)+updatetJoint.get(Joint.RAE4));
		jointAngles.put(Joint.LLE1, jointAngles.get(Joint.LLE1)+updatetJoint.get(Joint.LLE1));
		jointAngles.put(Joint.LLE2, jointAngles.get(Joint.LLE2)+updatetJoint.get(Joint.LLE2));
		jointAngles.put(Joint.LLE3, jointAngles.get(Joint.LLE3)+updatetJoint.get(Joint.LLE3));
		jointAngles.put(Joint.LLE4, jointAngles.get(Joint.LLE4)+updatetJoint.get(Joint.LLE4));
		jointAngles.put(Joint.LLE5, jointAngles.get(Joint.LLE5)+updatetJoint.get(Joint.LLE5));
		jointAngles.put(Joint.LLE6, jointAngles.get(Joint.LLE6)+updatetJoint.get(Joint.LLE6));
		jointAngles.put(Joint.RLE1, jointAngles.get(Joint.RLE1)+updatetJoint.get(Joint.RLE1));
		jointAngles.put(Joint.RLE2, jointAngles.get(Joint.RLE2)+updatetJoint.get(Joint.RLE2));
		jointAngles.put(Joint.RLE3, jointAngles.get(Joint.RLE3)+updatetJoint.get(Joint.RLE3));
		jointAngles.put(Joint.RLE4, jointAngles.get(Joint.RLE4)+updatetJoint.get(Joint.RLE4));
		jointAngles.put(Joint.RLE5, jointAngles.get(Joint.RLE5)+updatetJoint.get(Joint.RLE5));
		jointAngles.put(Joint.RLE6, jointAngles.get(Joint.RLE6)+updatetJoint.get(Joint.RLE6));
		return jointAngles;
	}
	
	protected void setInitUpdateJoint(){
		updatetJoint.put(Joint.HE1, 50.0);
		updatetJoint.put(Joint.HE2, 50.0);
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
	@Override
	public void footStabilization(Correction corrections, Vector3D[] footVector, Vector3D zmp){
		
		Double correction = -0.25;
		Double sideCorrection = 0.01;		
		
		if(footVector !=null){
		Vector3D rFootFR = footVector[0] == null ? Vector3D.ZERO_VECTOR : footVector[0];
		Vector3D lFootFR = footVector[2] == null ? Vector3D.ZERO_VECTOR : footVector[2];
		Vector3D rPointFR = footVector[1]== null ? Vector3D.ZERO_VECTOR : footVector[1];
		Vector3D lPointFR = footVector[3]== null ? Vector3D.ZERO_VECTOR : footVector[3];
		
		setInitUpdateJoint();
		if(rFootFR.getZ() > 0){
			if(Math.abs(rPointFR.getX())>=FOOT_ONGROUND_POSITOIN){
				
				if(corrections != null){
					correction=corrections.getRLE5();
				}
				double rle5 = correction * (rPointFR.getY() > 0 ? 1.0 : -1.0);
				updatetJoint.remove(Joint.RLE5);
				updatetJoint.put(Joint.RLE5, 00.0+rle5);
			}
			if(Math.abs(rPointFR.getY())>=SIDE_FOOT_ONGORUND_POSITION){
				
				if(corrections != null){
					sideCorrection=corrections.getRLE6();
				}
				double rle6 = sideCorrection * (rPointFR.getX() > 0 ? 1.0 : -1.0);
				updatetJoint.remove(Joint.RLE6);
				updatetJoint.put(Joint.RLE6, -0.0+rle6);
			}
			if(Math.abs(zmp.getY())>0.3){
				updatetJoint.remove(Joint.RLE3);
				updatetJoint.remove(Joint.RLE2);
				updatetJoint.put(Joint.RLE3, 1.0);
				updatetJoint.put(Joint.RLE2, 0.3);
				
			}
			
		}else if(lFootFR.getZ() > 0){
			if(Math.abs(lPointFR.getX())>=FOOT_ONGROUND_POSITOIN){
				
				if(corrections != null){
					correction=corrections.getLLE5();
				}
				double lle5 = correction * (lPointFR.getY() > 0 ? 1.0 : -1.0);
				updatetJoint.remove(Joint.LLE5);
				updatetJoint.put(Joint.LLE5, 00.0+lle5);
			}
			if(Math.abs(lPointFR.getY())>=SIDE_FOOT_ONGORUND_POSITION){
				
				if(corrections != null){
					sideCorrection=corrections.getLLE6();
				}
				double lle6 = sideCorrection * (lPointFR.getX() > 0 ? 1.0 : -1.0);
				updatetJoint.remove(Joint.LLE6);
				updatetJoint.put(Joint.LLE6, 0.0+lle6);
			}
			if(Math.abs(zmp.getY())>0.3){
				updatetJoint.remove(Joint.LLE3);
				updatetJoint.remove(Joint.LLE2);
				updatetJoint.put(Joint.LLE3, 1.0);
				updatetJoint.put(Joint.LLE2, 0.3);
				
			}
		}
		}

		
	}
	@Override
	public void handStabilization(Correction corrections, Vector3D[] footVector, Vector3D zmp){
//		Vector3D rFootFR = Vector3D.ZERO_VECTOR;//(getForceReceptor().rightFootForce == null) ? Vector3D.ZERO_VECTOR : getForceReceptor().rightFootForce;
//		Vector3D lFootFR = Vector3D.ZERO_VECTOR;//(getForceReceptor().leftFootForce == null) ? Vector3D.ZERO_VECTOR : getForceReceptor().leftFootForce;
//		Vector3D rPointFR = (getForceReceptor().rightFootPoint == null) ? Vector3D.ZERO_VECTOR : getForceReceptor().rightFootPoint;
//		Vector3D lPointFR = (getForceReceptor().leftFootPoint == null) ? Vector3D.ZERO_VECTOR : getForceReceptor().leftFootPoint;
		Vector3D rFootFR = footVector[0] == null ? Vector3D.ZERO_VECTOR : footVector[0];
		Vector3D lFootFR = footVector[2] == null ? Vector3D.ZERO_VECTOR : footVector[2];
		
		double rollX = rFootFR.getX() + lFootFR.getX();
		double pitchY = rFootFR.getY() + lFootFR.getY(); 
		
//		double rae2 =  getLastDataReceived().agentsJoints.get(Joint.RAE2) - rollX*2;
//		double lae2 =  getLastDataReceived().agentsJoints.get(Joint.LAE2) - rollX*2;
	
		double rae2 =  corrections.getRAE2() - rollX*2;
		double lae2 =  corrections.getLAE2() - rollX*2;
		
		rae2 += Math.abs(rollX)<3 ? 2.0 : 0;
		lae2 -= Math.abs(rollX)<3 ? 2.0 : 0;
		
		updatetJoint.remove(Joint.RAE2);
		updatetJoint.remove(Joint.LAE2);
		updatetJoint.put(Joint.RAE2, rae2);
		updatetJoint.put(Joint.LAE2, lae2);
	}


	public Vector3D[] getFootVector() {
		return footVector;
	}


	public void setFootVector(Vector3D[] footVector) {
		this.footVector = footVector;
	}


	public Vector3D getZmp() {
		return zmp;
	}


	public void setZmp(Vector3D zmp) {
		this.zmp = zmp;
	}
	
	
}
