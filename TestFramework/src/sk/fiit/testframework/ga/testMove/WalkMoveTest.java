/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.fiit.testframework.ga.testMove;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import sk.fiit.jim.agent.models.AgentJointPositionCalculator;
import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.parsing.ParsedData;
import sk.fiit.robocup.library.geometry.Vector3;
import sk.fiit.robocup.library.representations.Correction;
import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.communication.agent.AgentData;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.communication.agent.AgentJim.TeamSide;
import sk.fiit.testframework.communication.agent.AgentManager;
import sk.fiit.testframework.ga.Fitness;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.simulation.AlgorithmType;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.gp.GeneticProgramming;
import sk.fiit.testframework.gp.Representation;
import sk.fiit.testframework.monitor.AgentMonitor;
import sk.fiit.testframework.monitor.AgentMonitorMessage;
import sk.fiit.testframework.monitor.AgentMonitorMessage.HighSkill;
import sk.fiit.testframework.monitor.IAgentMonitorListener;
import sk.fiit.testframework.parsing.models.PlayMode;
import sk.fiit.testframework.trainer.testsuite.TestCase;
import sk.fiit.testframework.trainer.testsuite.TestCaseResult;
import sk.fiit.testframework.worldrepresentation.models.Player;
import sk.fiit.testframework.worldrepresentation.models.SimulationState;

public class WalkMoveTest extends TestCase implements IAgentMonitorListener {

	private static Logger logger = Logger.getLogger(WalkMoveTest.class.getName());
	private static final double step = 0.0005;

	private AgentJim agent;
	private AgentData agentData;
	private Vector3 initPos;
	private double startTime;
	private Vector3 lastLocation;

	private int numberFall=0;
	private double lastGroundTouchTime;
	
	private boolean started;

	private static final GeneticProgramming gp = GeneticProgramming.getInstance();


	private AgentMoveWriter write;
	private EnvironmentSetting eS;
	private AgentMove move;
	private ParameterGA paramGA;
	private TestTypeParameter paramTT;
	private Double walkDistance;
	private Representation eq;
	private AlgorithmType algType;
//	= gp.generateEquation(4);
	
	public WalkMoveTest(AgentJim agent,AgentMove move, Representation eq, EnvironmentSetting eS, ParameterGA paramGA, TestTypeParameter paramTT, AlgorithmType algType)  {
		super();
		this.agent=agent;
		
		this.eS=eS;
		this.move=move;
		this.paramGA=paramGA;
		this.paramTT=paramTT;
		this.eq = eq;
		this.algType = algType;
		write = new AgentMoveWriter();     
		AgentMonitor.setMessageListener(1, "JA", this, AgentMonitorMessage.TYPE_HIGHSKILL);
	}

	@Override
	public boolean init() {
		super.init();
		try {									       	
        	// zapise pohyb
			agent = AgentManager.getManager().getAgentByOrder(0);
			agent=AgentManager.getManager().getAgentByOrder(AgentManager.getManager().getAgentCount()-1);
        	if(algType.isGeneticAlgorithm()){
				String destMove = paramGA.getSource().replace(paramGA.getMoveName(), "n_"+paramGA.getMoveName());
	        	write.write(move, destMove);//   
        	}     
        	if(agent == null) {
				agentData = new AgentData(1, TeamSide.LEFT, "JA");
			} else {
				agentData = agent.getAgentData();
			}
        	
        	server.setBallPosition(eS.getPoint(eS.getBallPosition()));
        	server.setAgentPosition(agent.getAgentData(), eS.getPoint(eS.getPlayerPosition())); 
        	//agent.invokePlanChange("PlanZakladny.config_instance(\\\"" + move.getName()  +"\\\")");
			
//        	if(algType.isGeneticAlgorithm()){
	        	agent.invokeMove(move.getName());
				agent.invokeXMLReload();
	        	
//        	}
        	Thread.sleep(500);
//        	server.setPlayMode(PlayMode.PlayOn);
//        	//double startTime = monitor.getSimulationState().getGameStateInfo().getTime();
//        	if(monitor.getSimulationState().getScene().getPlayers().get(0).getLocation() != eS.getVector(eS.getPlayerPosition())){	
//				server.setBallPosition(eS.getPoint(eS.getBallPosition()));
//				server.setAgentPosition(agent.getAgentData(), eS.getPoint(eS.getPlayerPosition())); 					
//			}
//        	server.setPlayMode(PlayMode.GameOver);
        	
        	logger.info("invoke " + move.getName());
      	        	
        	walkDistance=0.0;        	        	
			logger.info("Test initialized.");
			return true;
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Unable to initialize test", ex);
			return false;
		}

	}
	
	Player p = null;
	private boolean wasEpileptic;
	private double startEpilepticTime;
	private boolean inEpileptic;

	private void restartAgent(AgentJim agent){
		AgentJim pomJim = agent;
		AgentManager.getManager().removeAgent(agent);

		String team = agent.getAgentData().getTeamName();
		int uniform = AgentManager.getManager().getFreeUniform(team);
		try{
			Thread.sleep(5000);
			AgentManager.getManager().getAgent(uniform, team, true);
			Thread.sleep(6000);
			
		}catch(InterruptedException ex){}											
		agent=pomJim;
		
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public boolean isStopCriterionMet(SimulationState ss) {
		try {
			server.setPlayMode(PlayMode.PlayOn);
		} catch (IOException e1) {
			logger.fine("Error in start PlayOn mode");
			e1.printStackTrace();
		}
		if (!started) {
			if (ss.getGameStateInfo().getPlayMode() == PlayMode.PlayOn.ordinal()) {
				logger.info("Test measurement started");
				started = true;					
				p = ss.getScene().getPlayers().get(0);								
				startTime = getElapsedTime();
				lastGroundTouchTime = startTime;
				wasEpileptic = false;
				lastLocation = p.getLocation();
				initPos=p.getLocation();
				numberFall = 0;
				startTime = getElapsedTime();
				inEpileptic = false;
				startEpilepticTime = startTime;
				
//				actualJointAngle = agent.getJointsAngle();

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
			return false;
		} else {

			logger.log(Level.FINE,"Check stop criterion");
															
			if (p.isOnGround() && (getElapsedTime() - lastGroundTouchTime) > 3) {								
					lastGroundTouchTime = getElapsedTime();
					numberFall ++;
					inEpileptic = false;
			}
//			restartAgent(agent);
			
			if(!p.isOnGround()){
				if(inEpileptic){
					inEpileptic = false;
				}
			}
//			if(p.isOnGround() && (Math.abs(lastLocation.getX()-p.getLocation().getX())) <1.0 && (Math.abs(lastLocation.getY()-p.getLocation().getY()))<1.0 && (Math.abs(lastLocation.getZ()-p.getLocation().getZ()))<1.0){
			if(p.isOnGround() && (lastLocation.getX()-p.getLocation().getX()) <0.0001 && (lastLocation.getY()-p.getLocation().getY())<0.001 && (lastLocation.getZ()-p.getLocation().getZ())<0.0005){
				if(!inEpileptic){
					startEpilepticTime = getElapsedTime();
					inEpileptic=true;
				}else{
//					if((getElapsedTime()-startEpilepticTime) > 3){
				if((getElapsedTime()-startEpilepticTime) > 2.7){
					try{
						server.setPlayMode(PlayMode.GameOver);
						logger.info("Restart agent");
						restartAgent(agent);
						
						p = ss.getScene().getPlayers().get(0);
						wasEpileptic = true;
						walkDistance = 0.0;						
						return true;
					}catch(IOException ex){
						logger.info("Error in set game over mode" + ex);
					}
				}}
			}
			
			
			if((p.getLocation().getXYDistanceFrom(lastLocation) > step)  /*&& !wasEpileptic*/ && !(p.isOnGround())){
				walkDistance += p.getLocation().getXYDistanceFrom(lastLocation);
			}	
			
			if ((getElapsedTime() - startTime) >= paramTT.getMaxTimeOnMove())
				return true;
			
			if(walkDistance>=paramTT.getLenghtWalk()){
				return true;
			}
			if(algType.isGeneticPrograming()){
//				logger.info("stabilo");
				stabilization();
			}
			lastLocation = p.getLocation();
//			agent.getLastData();
//			p.getAssociatedAgent();
//			agent.getJointsAngle();
						
			return false;
		}
	}
	
	
	private void stabilization(){
//		logger.log(Level.INFO, "ZMP> "+ agent.getZMP());
//		logger.log(Level.INFO, "foot> "+ agent.getForceVectors()[0]);
//		logger.log(Level.INFO, "LLE5" + agent.getJointsAngle().get(Joint.LLE5));

		Correction corrections = new Correction();				
		corrections.setLLE5(setCorrextion(Joint.LLE5));
		corrections.setLLE6(setCorrextion(Joint.LLE6));
		corrections.setRLE5(setCorrextion(Joint.RLE5));
		corrections.setRLE6(setCorrextion(Joint.RLE6));
		
		corrections.setRAE2(agent.getJointsAngle().get(Joint.RAE2));
		corrections.setLAE2(agent.getJointsAngle().get(Joint.LAE2));
		

//		logger.log(Level.INFO, "pred LLE5" + agent.getJointsAngle().get(Joint.LLE5));
		AgentJointPositionCalculator.getInstance().footStabilization(corrections, agent.getForceVectors(), agent.getZMP());
		AgentJointPositionCalculator.getInstance().handStabilization(corrections, agent.getForceVectors(), agent.getZMP());
		agent.stabilization();
//		logger.log(Level.INFO, "po LLE5" + agent.getJointsAngle().get(Joint.LLE5));
	}
	

	
	private Double setCorrextion(Joint joint) {
		Double number;
		Representation pomEq = new Representation();
		
		gp.setXValue(agent.getJointsAngle().get(joint));
		pomEq = gp.changeXtovalue(eq);

//		number = gp.evaluateEquation(pomEq);
		number = gp.evaluate(pomEq);
		
//		logger.info(number.toString());
//		if(number == null || Double.isInfinite(number)||Double.isNaN(number)){
			if(number == null || number.equals(null)||number.equals("null")){
//				logger.info("null pointer");
				number = 1.0;
			}else if(Double.isInfinite(number)||Double.isNaN(number)){
//				logger.info("nan or infinity");
				number = 1.0;
			}
//		}
//			logger.info(number.toString());
//		eq=pomEq;
		//number = number > joint.getUp() ? number:number%joint.getUp();			
		return number%5;
	}


	@Override
	public TestCaseResult evaluate(SimulationState ss) {
		Player player = ss.getScene().getPlayers().get(0);
		
		@SuppressWarnings("deprecation")
		double currentPlayerLocationX = player.getLocation().getX();
		double resultTime = getElapsedTime() - startTime;
		
		logger.fine("player location.x: " + currentPlayerLocationX);
		logger.fine("Time: " + resultTime);
		if(wasEpileptic){
			resultTime = paramTT.getMaxTimeOnMove()+1;
		}
		
			logger.info("Attempt successful.");
			logger.info("Time: " + resultTime);
			logger.info("number of fall: " + numberFall);
			logger.info("distance: " + walkDistance);
			Fitness f = new Fitness();
			TestCaseResult result = f.walkFitnes(paramTT, initPos, resultTime, numberFall,lastLocation);
			if(algType.isGeneticPrograming()){
				result.setDiversion(f.getDiversion());
				result.setFall(f.getFall());
			}
			return result;
			
	}

	@Override
	public void destroy() {
		super.destroy();
		//AgentMonitor.removeMessageListener(1, "JA", this);		
    	
		try {
			
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.fine("Test case destroyed");
	}

	@Override
	public void receivedMessage(int uniform, String team, AgentMonitorMessage message) {
		// registrovany bol listener iba na typ LOWSKILL takze ClassCasting je bezpecny
		AgentMonitorMessage.HighSkill msg = (HighSkill) message;
		switch(msg.action) {
		case start:
			//				msg.move_name a msg.playertime
			break;
		case stop:
			//				msg.move_name a msg.playertime
			break;
		}
	}



}
