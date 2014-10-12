package sk.fiit.testframework.ga.simulation;

public class TestTypeParameter {
	private boolean penalize;
	private double minKickDistance;
	private int maxAngleDiversion;
	private double lenghtWalk;
	private String moveType;
	private double maxTimeOnMove;
	private int numberOfTestOnMove;
	
	public void setPenalize(boolean penalize){
		this.penalize = penalize;
	}
	public void setMinKickDistance(double minKickDistance){
		this.minKickDistance = minKickDistance;
	}
	public void setMaxAngleDiversion(int maxAngleDiversion){
		this.maxAngleDiversion = maxAngleDiversion;
	}
	public void setLenghtWalk(double lenghtWalk){
		this.lenghtWalk = lenghtWalk;
	}
	public void setMoveType(String moveType){
		this.moveType = moveType;
	}
	public void setMaxTimeOnMove(double maxTimeOnMove){
		this.maxTimeOnMove = maxTimeOnMove;
	}
	public void setNumberTestOnMove(int numberOfTestOnMove) {
		this.numberOfTestOnMove=numberOfTestOnMove;		
	}
	
	public boolean getPenalize(){
		return penalize;
	}
	public double getMinKickDistance(){
		return minKickDistance;
	}
	public int getMaxAngleDiversion(){
		return maxAngleDiversion;
	}
	public double getLenghtWalk(){
		return lenghtWalk;
	}
	public String getMoveType(){
		return moveType;
	}
	public double getMaxTimeOnMove(){
		return maxTimeOnMove;
	}
	public int getNumberTestOnMove(){
		return numberOfTestOnMove;
	}
	
	

}
