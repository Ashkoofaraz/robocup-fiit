package sk.fiit.jim.agent.highskill.move;

import sk.fiit.jim.agent.models.DynamicObject;
import sk.fiit.robocup.library.geometry.Vector3D;

public class MoveSkillPostionObject {
	private boolean xDyn;
	private boolean yDyn;
	private double x;
	private double y;
	private DynamicObject dynObjX;
	private DynamicObject dynObjY;
	private double xOffset;
	private double yOffset;
	
	public void xIsDyn(DynamicObject dyn, double y){
		this.xDyn = true;
		this.dynObjX = dyn;
		this.y = y;
	}
	public void yIsDyn(DynamicObject dyn, double x){
		this.yDyn = true;
		this.dynObjY = dyn;
		this.x = x;
	}
	public void BothDyn(DynamicObject dynX,DynamicObject dynY){
		this.yDyn = true;
		this.xDyn = true;
		this.dynObjX = dynX;	
		this.dynObjY = dynY;
	}
	public void BothDyn(DynamicObject dynX,Vector3D dynY){
		this.yDyn = true;
		this.xDyn = true;
		this.dynObjX = dynX;	
		this.dynObjY = new DynamicObject();
		this.dynObjY.setPosition(dynY);
	}
	public void BothDyn(Vector3D dynX,DynamicObject dynY){
		this.yDyn = true;
		this.xDyn = true;
		this.dynObjX = new DynamicObject();
		this.dynObjY.setPosition(dynX);
		this.dynObjY = dynY;
	}
	public void noneDyn (double x, double y){
		this.yDyn = false;
		this.xDyn = false;
		this.x = x;
		this.y = y;
	}
	public void setOffSet(double x, double y){
		this.xOffset=x;
		this.yOffset=y;
	}
	
	public double getXOffset(){
		return this.xOffset;
	}
	public double getYOffset(){
		return this.yOffset;
	}
	
	public boolean getXDyn(){
		return this.xDyn; 
	}
	public boolean getYDyn(){
		return this.yDyn; 
	}
	public double getX(){
		return this.x; 
	}
	public double getY(){
		return this.y; 
	}
	public double getDynX(){
		if (this.dynObjX==null) return 0;
		return this.dynObjX.getPosition().getX(); 
	}
	public double getDynY(){
		if (this.dynObjY==null) return 0;
		return this.dynObjY.getPosition().getY(); 
	}
	
}

