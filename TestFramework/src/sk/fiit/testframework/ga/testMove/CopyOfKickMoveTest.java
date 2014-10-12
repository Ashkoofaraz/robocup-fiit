/**
 * Name:    TurnAround.java
 * Created: Feb 26, 2012
 * 
 * @author: ivan
 */
package sk.fiit.testframework.ga.testMove;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3;
import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.communication.agent.AgentData;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.communication.agent.AgentJim.TeamSide;
import sk.fiit.testframework.communication.agent.AgentManager;
import sk.fiit.testframework.ga.Fitness;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.parsing.models.PlayMode;
import sk.fiit.testframework.trainer.testsuite.TestCase;
import sk.fiit.testframework.trainer.testsuite.TestCaseResult;
import sk.fiit.testframework.worldrepresentation.models.Player;
import sk.fiit.testframework.worldrepresentation.models.SimulationState;

/**
 * TODO: Replace with a brief purpose of class / interface.
 * 
 * @author ivan
 *
 */
public class CopyOfKickMoveTest extends TestCase {
	private static Logger logger = Logger.getLogger(CopyOfKickMoveTest.class.getName());

	private AgentData agentData;
	private AgentJim agent;
	private Vector3 initPosBall;
	private boolean started = false;
	private double startTime;
	
	private double ballTouchedTime = -1;
	private double TIME_BETWEEN_KICK_AND_TELEPORT = 0.2;
	private boolean agentTeleportedAfterKick = false;
	
	private double fieldLength;
	private double fieldWidth;
	
	private boolean ballMoved = false;
	private boolean playerFalled = false;
	//how much time between start of playmode and start of testing - if ball started to move (ball kicked)
	//because ball moves a bit after set to initial point
	private static double TIME_FOR_INITIAL_BALL_MOVE = 0.5;
	private AgentMoveWriter write;
	private AgentMove move;
	private ParameterGA paramGA;
	private TestTypeParameter paramTT;
	private Vector3 initPosAgent;
	
	public CopyOfKickMoveTest(AgentJim agent,AgentMove move, EnvironmentSetting eS, ParameterGA paramGA, TestTypeParameter paramTT) {
		super();
		this.agent=agent;
		
		this.move=move;
		this.paramGA=paramGA;
		this.paramTT=paramTT;
		write = new AgentMoveWriter();   
		
	}

	@Override
	public boolean init() {
		super.init();
		logger.info("KickDistanceTest init");
		initPosBall = new Vector3(0, 0, 0.0);
		initPosAgent = new Vector3(-0.25, -0.04, 0.4);
		try {
			AgentManager.getManager().setAgentWaitTime(30000);
			agent = AgentManager.getManager().getAgentByOrder(0);
        	
			String destMove = paramGA.getSource().replace(paramGA.getMoveName(), "n_"+paramGA.getMoveName());
        	write.write(move, destMove);//
        	
			//agent = AgentManager.getManager().getAgent(1, TeamSide.LEFT.toString(), false);
        	if(agent == null) {
				agentData = new AgentData(1, TeamSide.LEFT, "JA");
			} else {
				agentData = agent.getAgentData();
			}
        	server.setBallPosition(initPosBall.asPoint3D());
        	server.setAgentPosition(agent.getAgentData(), initPosAgent.asPoint3D()); 
        	agent.invokeMove(move.getName());
        	agent.invokeXMLReload();
        	//Thread.sleep(500);        	        	                		
        	/*server.setPlayMode(PlayMode.PlayOn);
        	if(monitor.getSimulationState().getScene().getPlayers().get(0).getLocation() != initPosAgent){	
        		server.setBallPosition(initPosBall.asPoint3D());
            	server.setAgentPosition(agent.getAgentData(),initPosAgent.asPoint3D()); 					
        	}
        	server.setPlayMode(PlayMode.BeforeKickOff);*/
        	Thread.sleep(2000);
        	//agent.invokeMove(paramGA.getMoveName());
			
						
			
			logger.info("Test initialized - waiting for KickOff_Left mode.");
			started = false;
			return true;
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Unable to initialize test", ex);
			return false;
		}

	}

	@Override
	public boolean isStopCriterionMet(SimulationState ss) {
		Player p = null;
		try {
			server.setPlayMode(PlayMode.free_kick_left);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (!started) {
			if (ss.getGameStateInfo().getPlayMode() == PlayMode.free_kick_left.ordinal()) {
				try {
					logger.info("Test measurement started");
					started = true;							
					server.setBallPosition(initPosBall.asPoint3D());
					if (agent != null) {
//						
					}	
					startTime = getElapsedTime();										
				} catch (IOException e) {
					logger.log(Level.FINE, "Error running test", e);
					
				}
				
			}
			return false;
		} else {
			p = ss.getScene().getPlayers().get(0);
			double elapsedTime = getElapsedTime();
			
			/*
			if (playerBehindBorder(p)) {	// controls if player is put behind border by server
				return true;
			}
			*/
			//zaznamena, ci spadol hrac
			if(!playerFalled && ss.getScene().getPlayers().get(0).isOnGround()){
				playerFalled = true;
			}
			
			//zaznamena, ci sa lopta uz hybala
			if(!ballMoved && ss.getScene().isBallMoving() && (elapsedTime - startTime > TIME_FOR_INITIAL_BALL_MOVE) ){
				logger.info("nastav pohyb lopty");
				ballMoved = true;
				ballTouchedTime = elapsedTime;
			}
			
			// teleporting player to initial position to prevent touching ball second time
			/*if (!agentTeleportedAfterKick && ballMoved && ss.getScene().isBallMoving() 
					&& (elapsedTime - ballTouchedTime) >= TIME_BETWEEN_KICK_AND_TELEPORT) {
				try {
					server.setAgentPosition(agentData, new Vector3(-0.25, -0.04, 0.4).asPoint3D());
					agentTeleportedAfterKick = true;
				} catch (IOException e) {
					logger.fine("Error teleporting agent after touching ball.");
				}
			}*/
			
			//ak sa hybala a uz stoji, koniec testu
			if(ballMoved && !ss.getScene().isBallMoving()){
				logger.info("bola v pohybe");
				return true;
			}
			
			//ak zaciatok kopnutia trva velmi dlho, koniec testu
			/*if (!ballMoved && (elapsedTime - startTime > TIME)){
				timeExpired = true;
				return true;
			} */
			if (!ballMoved && (elapsedTime - startTime) > paramTT.getMaxTimeOnMove()){
				logger.info("koniec casu");
				return true;
			}
			//inak sa pokracuje
			return false;
		}
	}
	
	/**
	 * Checks if player is out of the field.
	 * 
	 * @author xsuchac
	 * @author A55-Kickers
	 * 
	 * @param player
	 * @return true if player is behind any border of field, else false.
	 */
	private boolean playerBehindBorder(Player player) {
		double playerLocationX = player.getLocation().getX();
		double playerLocationY = player.getLocation().getY();
		
		if (Math.abs(playerLocationX) > (fieldLength / 2) 
				|| Math.abs(playerLocationY) > (fieldWidth / 2)) {
			return true;
		}
		return false;
	}

	@Override
	public TestCaseResult evaluate(SimulationState ss) {
		Player p = ss.getScene().getPlayers().get(0);
		double distance = ss.getScene().getBallLocation().getXYDistanceFrom(initPosBall);
		/*if (playerFalled) {
			logger.info("Player was penalized for falling during kick, result: " + distance + " / 2.");
			//distance = distance / 2;
		} else {
			logger.info("Attempt successful.");
		}*/
		Fitness f = new Fitness();
		return f.kickFitnes(paramTT, distance, playerFalled,initPosBall,ss.getScene().getBallLocation());
	}

	@Override
	public void destroy() {
		super.destroy();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.fine("Test case destroyed");
	}
	

}
