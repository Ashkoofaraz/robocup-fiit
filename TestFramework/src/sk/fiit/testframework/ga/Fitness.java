package sk.fiit.testframework.ga;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import sk.fiit.robocup.library.geometry.Vector3;
import sk.fiit.robocup.library.geometry.Vector3D;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.trainer.testsuite.TestCaseResult;

public class Fitness {
	
	private double fitness;
	
	private int fall = 0;
	
	private double diversion = 0.0;
	
	private static final double PENALIZE_WALK = 10;	
	
	private static final double PENALIZE_KICK = 0.5;
	
	private static final Logger logger = Logger.getLogger(Fitness.class.getName());

	
	enum Diversion{
		angle(0.0,5.0,0.0),
		angle1(5.1,10.0,2.0),
		angle2(10.0,20.0,5.0),
		angle3(20.0,90.0,10.0);
		
		
		private double from;
		private double to;
		private double penalize;
		Diversion(double from, double to, double penalize){
			this.from=from;
			this.to=to;
			this.penalize=penalize;
		}
		
		public double getFrom(){
			return this.from;
		}
		
		public double getTo(){
			return this.to;
		}
		public double getPenalize(){
			return penalize;
		}
		
		private static Map<String, Diversion> diversionPenalty = new HashMap<String, Diversion>(){{
			put("angle", angle);
			put("angle1", angle1);
			put("angle2", angle2);
			put("angle3", angle3);
			
		}};

					
	}
	
	public Fitness (){				
		
	}
	
	//distance + panalize
	/**
	 * compute fitness for kick
	 * 
	 * @param ttParam
	 * @param distance
	 * @param fallen 
	 * @param initPosBall 
	 * @param lastPosBall
	 * @return
	 */ 
	public TestCaseResult kickFitnes(TestTypeParameter ttParam, double distance, boolean fallen, Vector3 initPosBall, Vector3 lastPosBall) {
		if(ttParam.getPenalize()){
			if(fallen){
				fall=1;
				fitness = PENALIZE_KICK*distance;
			}
			else{ 
				fitness = distance;
			}
//			diversion = diversionPenalty(lastPosBall, initPosBall, ttParam.getMaxAngleDiversion());
			fitness = fitness - (diversionPenalty(lastPosBall, initPosBall, ttParam.getMaxAngleDiversion())*0.1);
		}
		else{
			fitness = distance;
		}
		return new TestCaseResult(fitness);
		
		
	}
		//time + penalize
	/**
	 * compute fitness for walk
	 * 
	 * @param ttParam
	 * @param startPosition
	 * @param time 
	 * @param numberFall
	 * @param lastPosition
	 * @return
	 */
	public TestCaseResult walkFitnes(TestTypeParameter ttParam, Vector3 startPosition, double time, int numberFall, Vector3 lastPosition) {
		if(time < ttParam.getMaxTimeOnMove()){			
			fall=numberFall;
			if(ttParam.getPenalize()){
				
				fitness = (numberFall*PENALIZE_WALK)+time + diversionPenalty(lastPosition, startPosition, ttParam.getMaxAngleDiversion());
			}
			else{
				fitness=time;
			}
			return new TestCaseResult(fitness);
		}
		else{
			return new TestCaseResult(0);
		}
			
	}
	
	public double getFitness(){
		return fitness;
	}
	

	public double getDiversion(){
		return diversion;
	}
	
	public int getFall(){
		return fall;
	}
	
	private double diversionPenalty(Vector3 lastPosition, Vector3 startPosition, double maxDiversion) {
		double angle=0;
		angle = Vector3D.fromVector3(lastPosition).subtract(Vector3D.fromVector3(startPosition)).getPhi();
		Vector3 pB = new Vector3(lastPosition.getX(),startPosition.getY(),lastPosition.getZ());
		
		double lenghtA = Math.abs(lastPosition.getXYDistanceFrom(pB));
		double lenghtC = Math.abs(startPosition.getXYDistanceFrom(pB));
		angle = Math.atan(lenghtC/lenghtA);
		logger.info("angle "+Math.atan(lenghtC/lenghtA));
//		logger.info("angle "+Math.toDegrees(angle));
//		logger.info("ANGLES "+angle);
		double penalize = getPenalty(Math.abs(Math.toDegrees(angle)-maxDiversion));
		logger.info("diversion penalty "+penalize);
		return penalize;
	}

	private double getPenalty(double angle){
		diversion=90.0-angle;
		logger.info("Angle on compare>" + diversion);
		for(Diversion div : Diversion.diversionPenalty.values()){
			if(diversion>=div.getFrom() && diversion <= div.getTo()){
				return div.getPenalize();
			}
		}
		return 20.0;
	}

	
}
