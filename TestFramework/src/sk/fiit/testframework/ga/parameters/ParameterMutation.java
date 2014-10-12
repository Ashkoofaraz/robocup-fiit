package sk.fiit.testframework.ga.parameters;

import java.util.ArrayList;

public class ParameterMutation {

	private int percentOfMutation;
	private int rangeMutation;
	private ArrayList<String> joint = new ArrayList<String>();
	
	public int getPercentOfMutation(){
		return percentOfMutation;
	}
	public int getRangeMutation(){
		return rangeMutation;
	}
	public ArrayList<String> getJoint(){
		return joint;
	}
	
	public void setPercentOfMutation(int percentOfMutation){
		this.percentOfMutation=percentOfMutation;
	}
	public void setRangeMutation(int rangeMutation){
		this.rangeMutation = rangeMutation;
	}
	public void setJoint(ArrayList<String> joint){
		this.joint=joint;
	}
	public void addJoint(String j){
		joint.add(j);
	}
	public boolean isSelected(String joint){		
		//ArrayList list = new ArrayList(Arrays.asList(this.joint));
		/*if(Arrays.asList(this.joint).contains(joint)){
			return true;
		}
		else{
			return false;
		}*/
		for(int i=0;i<this.joint.size();i++){
			if(this.joint.get(i).compareToIgnoreCase(joint)==0){
				return true;
			}
		}
		return false;
	}
	
}
