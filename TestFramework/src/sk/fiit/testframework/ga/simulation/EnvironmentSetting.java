package sk.fiit.testframework.ga.simulation;

import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3;

public class EnvironmentSetting {
	
	private String playerPosition;
	private String ballPosition;
	
	public void setPlayerPosition(String playerPosition){
		this.playerPosition = playerPosition;
	}
	public void setBallPosition(String ballPosition){
		this.ballPosition = ballPosition;
	}
	
	public String getPlayerPosition(){
		return playerPosition;
	}
	
	public String getBallPosition(){
		return ballPosition;
	}
	 public Point3D getPoint(String point){
		 String[] split = point.split(":");
			if (split.length != 3) {
				throw new IllegalArgumentException(
						"String to split doesn't have 3 items: " + point);
			}
			try {
				double x = new Double(split[0]).doubleValue();
				double y = new Double(split[1]).doubleValue();
				double z = new Double(split[2]).doubleValue();
				return new Point3D(x, y, z); 
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"String isn't a valid sequence of numbers: " + point);
			}
		 
	 }
	 public Vector3 getVector(String point){
		 String[] split = point.split(":");
		 if(split.length!=3){
			 throw new IllegalArgumentException(
						"String to split doesn't have 3 items: " + point);
		 }
		 try{
			 double x = new Double(split[0]).doubleValue();
			 double y = new Double(split[1]).doubleValue();
			 double z = new Double(split[2]).doubleValue();
			 return new Vector3(x, y, z);
		 }
		 catch(NumberFormatException e){
			 throw new IllegalArgumentException(
						"String isn't a valid sequence of numbers: " + point);
		 }
		 
	 }
}
