/**
 * Name:    TurnAround.java
 * Created: Feb 26, 2012
 * 
 * @author: ivan
 */
package sk.fiit.testframework.trainer.testsuite.testcases.tournament;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class KickDistanceGATest extends TestCase {
	private static Logger logger = Logger.getLogger(KickDistanceGATest.class.getName());

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
	private boolean timeExpired = false;
	private boolean wasEpileptic;
	private double startEpilepticTime;
	private boolean inEpileptic;
	private Vector3 lastLocation;
	
	//how much time between start of playmode and start of testing - if ball started to move (ball kicked)
	//because ball moves a bit after set to initial point
	private static double TIME_FOR_INITIAL_BALL_MOVE = 0.5;
	//time for kick
	private int TIME = 10;

//	private AgentMove move;

//	private ParameterGA paramGA;

//	private TestTypeParameter paramTT;

//	private AgentMoveWriter write;
	
	public KickDistanceGATest() {
		super();
//		this.agent=agent;
//		
//		this.move=move;
//		this.paramGA=paramGA;
//		this.paramTT=paramTT;
//		write = new AgentMoveWriter();
	}

	@Override
	public boolean init() {
		super.init();
		logger.info("KickDistanceTest init");
		try {
			initPosBall = new Vector3(0,-0.1,0.2);// new Vector3(0.0,0,0.0);//		
			
			fieldLength = monitor.getSimulationState().getEnvironmentInfo().getFieldLength();
			fieldWidth = monitor.getSimulationState().getEnvironmentInfo().getFieldWidth();
			
			AgentManager.getManager().setAgentWaitTime(30000);
			//agent = AgentManager.getManager().getAgent(1, TeamSide.LEFT.toString(), false);
			agent = AgentManager.getManager().getAgentByOrder(0);
//			String destMove = paramGA.getSource().replace(paramGA.getMoveName(), "n_"+paramGA.getMoveName());
//        	write.write(move, destMove);//
			if (agent != null) {
				agentData = agent.getAgentData();
			} else {
				agentData = new AgentData(1, TeamSide.LEFT, "JA");
			}
//			agent.invokeMove(move.getName());
        	agent.invokeXMLReload();
			logger.info("Test initialized - waiting for KickOff_Left mode.");
			Thread.sleep(2000);
			started = false;
			return true;
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Unable to initialize test", ex);
			return false;
		}

	}
	Player p = null;
	@Override
	public boolean isStopCriterionMet(SimulationState ss) {
		
		
		
		if (!started) {
			try {
				server.setPlayMode(PlayMode.KickOff_Left);
				Thread.sleep(1000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ss.getGameStateInfo().getPlayMode() == PlayMode.KickOff_Left.ordinal()) {
//			if (ss.getGameStateInfo().getPlayMode() == PlayMode.PlayOn.ordinal()) {
//				try {
					logger.info("Test measurement started");
					p = ss.getScene().getPlayers().get(0);		
					wasEpileptic = false;
					lastLocation = p.getLocation();
					inEpileptic = false;
					startEpilepticTime = startTime;
					started = true;
//					server.setBallPosition(initPosBall.asPoint3D());
					//server.setAgentPosition(agentData, new Vector3(-0.7,0,0.4).asPoint3D());
					if (agent != null) {
//						agent.invokeMove("kick_right");
					}
					startTime = getElapsedTime();
//				} catch (IOException e) {
//					logger.log(Level.FINE, "Error running test", e);
//				}
			}
			
			
			return false;
			
		} else {
			p = ss.getScene().getPlayers().get(0);
			double elapsedTime = getElapsedTime();
			
			if (playerBehindBorder(p)) {	// controls if player is put behind border by server
				return true;
			}
			
			//zaznamena, ci spadol hrac
			if(!playerFalled && ss.getScene().getPlayers().get(0).isOnGround()){
				playerFalled = true;
			}
			
			//zaznamena, ci sa lopta uz hybala
			if(!ballMoved && ss.getScene().isBallMoving() && (elapsedTime - startTime > TIME_FOR_INITIAL_BALL_MOVE) ){
				ballMoved = true;
				ballTouchedTime = elapsedTime;
			}
			
			// teleporting player to initial position to prevent touching ball second time
			if (!agentTeleportedAfterKick && ballMoved && ss.getScene().isBallMoving() 
					&& (elapsedTime - ballTouchedTime) >= TIME_BETWEEN_KICK_AND_TELEPORT) {
				try {
					Thread.sleep(2000);
					server.setAgentPosition(agentData, new Vector3(-1.5, 0, 0.30).asPoint3D());
					agentTeleportedAfterKick = true;
				} catch (IOException e) {
					logger.fine("Error teleporting agent after touching ball.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!p.isOnGround()){
				inEpileptic=false;
			}
//			restartAgent(agent);
			if(p.isOnGround() && (Math.abs(lastLocation.getX()-p.getLocation().getX())) <0.001 && (Math.abs(lastLocation.getY()-p.getLocation().getY()))<0.001 && (Math.abs(lastLocation.getZ()-p.getLocation().getZ()))<0.001){
				if(!inEpileptic){
					startEpilepticTime = getElapsedTime();
					inEpileptic=true;
				}else{ 
				if((getElapsedTime()-startEpilepticTime) > 5.0){
					try{
						server.setPlayMode(PlayMode.GameOver);
						logger.info("Restart agent");
						restartAgent(agent);
						
						p = ss.getScene().getPlayers().get(0);
						wasEpileptic = true;						
						return true;
					}catch(IOException ex){
						logger.info("Error in set game over mode" + ex);
					}
				}}
			}
			
			//ak sa hybala a uz stoji, koniec testu
			if(ballMoved && !ss.getScene().isBallMoving()){
				return true;
			}
			
			//ak zaciatok kopnutia trva velmi dlho, koniec testu
			if (!ballMoved && (elapsedTime - startTime > 10)){
				timeExpired = true;
				return true;
			} 

			//inak sa pokracuje
			lastLocation = p.getLocation();
			return false;
		}
	}
	
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
//		Player p = ss.getScene().getPlayers().get(0);
		double distance = ss.getScene().getBallLocation().getXYDistanceFrom(initPosBall);
		/*if (playerFalled) {
			logger.info("Player was penalized for falling during kick, result: " + distance + " / 2.");
			distance = distance / 2;
		} else {
			logger.info("Attempt successful.");
		}*/
//		Fitness f = new Fitness();
//		return f.kickFitnes(paramTT, distance, playerFalled, initPosBall,ss.getScene().getBallLocation());
		return new TestCaseResult(distance);
	}

	@Override
	public void destroy() {
		super.destroy();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.fine("Test case destroyed");
	}	

}
