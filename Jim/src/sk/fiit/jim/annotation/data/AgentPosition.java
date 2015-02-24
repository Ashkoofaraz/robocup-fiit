package sk.fiit.jim.annotation.data;

/**
 * @author Michal Blanarik
 * Ideal position of the Agent to kick ball based on annotation
 */

public class AgentPosition {

	/**
	 * Minimal value of x axis.
	 */
	double minX = 0;
	/**
	 * Maximal value of x axis.
	 */
	double maxX = 0;
	/**
	 * Minimal value of y axis.
	 */
	double minY = 0;
	/**
	 * Maximal value of y axis.
	 */
	double maxY = 0;
	
	public void setMinX(double min){
		this.minX = min;
	}
	
	public void setMaxX(double max){
		this.maxX = max;
	}
	
	public void setMinY(double min){
		this.minY = min;
	}
	
	public void setMaxY(double max){
		this.maxY = max;
	}
	
	
	public double getMinX(){
		return this.minX;
	}
	
	public double getMaxX(){
		return this.maxX;
	}
	
	public double getMinY(){
		return this.minY;
	}
	
	public double getMaxY(){
		return this.maxY;
	}

	@Override
	public String toString() {
		return "Values [minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY + "]";
		
	}
	
}
