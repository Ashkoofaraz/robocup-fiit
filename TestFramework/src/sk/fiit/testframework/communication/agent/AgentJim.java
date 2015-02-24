package sk.fiit.testframework.communication.agent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import org.apache.commons.net.tftp.TFTPClient;

import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.robocup.library.annotations.TestCovered;
import sk.fiit.robocup.library.annotations.UnderConstruction;

/**
 * 
 * Represents a single agent instance that's connected to the test framework.
 * It can be an agent launched locally by the test framework, or an externally launched
 * agent that's connected to the test framework.
 * 
 */
@UnderConstruction
public class AgentJim {	
	
	private static Logger logger = Logger.getLogger(AgentJim.class.getName());
	
	/**
	 * Used for accumulating all of the agent's standard output text
	 */
	private StringBuilder output = new StringBuilder();
	private int sceneGraphPlayer = -1;
	
	private WorldModel worldModel;
	
	/**
	 * Represents a side that the agent plays on. SimSpark's network protocol
	 * uses this information along with an agent's unifrom number to identify
	 * an agent. 
	 */
	public enum TeamSide {
		RIGHT {
			public String toString() {
				//must be exactly like this!
				//http://simspark.sourceforge.net/wiki/index.php/Network_Protocol
				return "Right";
			}
		},
		
		LEFT {
			public String toString() {
				return "Left"; //must be exactly like this!
			}
		}
	}
	
    private AgentData agentData;
    private TFTPClient tftpClient = new TFTPClient();
    private int tftpPort;
    private String tftpIP;
    
    /**
     * Constructor used when we want to attach to an running Jim agent 
     * connected to the server on address <code>serverAddress</code>. 
     * This is especially useful when debugging, because we don't have to 
     * run new instance of agent everytime we want to use testcase. We can
     * simply run agent in shell and by this constructor treat is like a
     * regular runned agent.
     * 
     * @param agentData
     */
    public AgentJim(AgentData agentData, String tftpIP, int tftpPort) {
        this.agentData = agentData;
        this.tftpPort = tftpPort;
        this.tftpIP = tftpIP;
    }
    
    /**
     * Returns the string builder used for accumulating all of the agent's 
     * standard output text. Only works for agents launched by the test framework.
     */
    public StringBuilder getStdout() {
    	return output;
    }

    public AgentData getAgentData() {
        return agentData;
    }
    
    public int getTftpPort() {
        return tftpPort;
    }

    public String getTftpIP() {
        return tftpIP;
    }

    public InetSocketAddress getTFTPAddress() {
        // TODO: Only localhost now | is that still true???
        return new InetSocketAddress(getTftpIP(), getTftpPort());
    }
    
    //TODO: what's the purpose of these 3 methods?
    //Looks like they're remains from an older version.
    //As far as I know, the agent must always be connected to the server.
    public boolean isConnectedToServer() {
        throw new UnsupportedOperationException();
    }

    public void connectToServer() {
        throw new UnsupportedOperationException();
    }

    public void disconnectFromServer() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * Makes the agent change its planner.
     * Also reloads all its ruby scripts, which will cause a replan.
     * 
     * @param planName the class name of the new plan (NOT the ruby file name)
     * @throws IOException on failed communication with the agent's TFTP server
     */
    public void invokePlanChange(String planName) throws IOException {
    	StringBuilder sb = new StringBuilder();
		sb.append("require \"java\"\n");
		sb.append("include_class \"sk.fiit.jim.init.ScriptBoot\"\n");
		sb.append("include_class \"sk.fiit.jim.agent.Planner\"\n");
		sb.append("include_class \"sk.fiit.robocup.library.init.Script\"\n");
		sb.append("Planner.setPlanner(\""+planName+"\")\n");
		sb.append("ScriptBoot.boot");
		executeRubyScript(sb.toString());
    }
    
    /**
     * 
     * Makes the agent reload all its XML moves (doesn't reload annotations)
     * 
     * @throws IOException on failed communication with the agent's TFTP server
     */
    public void invokeXMLReload() throws IOException {
    	StringBuilder sb = new StringBuilder();
		sb.append("require \"java\"\n");
		sb.append("include_class \"sk.fiit.jim.init.SkillsFromXmlLoader\"\n");
		sb.append("loader = SkillsFromXmlLoader.new(Java::java.io.File.new(\"./moves\"))\n");
		sb.append("loader.load\n");
		executeRubyScript(sb.toString());
    }
	
    /**
     * 
     * Makes the agent reload all its ruby scripts, which will cause a replan 
     * 
     * @throws IOException on failed communication with the agent's TFTP server
     */
	public void invokeReplan() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("require \"java\"\n");
		sb.append("include_class \"sk.fiit.jim.init.ScriptBoot\"\n");
		sb.append("ScriptBoot.boot");
		executeRubyScript(sb.toString());
	}

	/**
	 * <p>Doesn't do anything.</p>
	 * <p>CommunicationThread (a class in the agent that this method relies on)
	 * is deprecated, so I assume that this is as well.</p>
	 */
	@Deprecated
	public void invokeRestart() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("require \"java\"\n");
		sb.append("include_class \"sk.fiit.jim.agent.communication.CommunicationThread\"\n");
		sb.append("CommunicationThread.request_restart");
		executeRubyScript(sb.toString());
	}

	//Added by Bimbo (High5)
	//na toto by asi bolo lepsie vytvorit nejaky samostatny plan (napr. PlanLowSkill)
	//a potom odtialto volat invokePlanChange
	@UnderConstruction
	public void invokeMove(String move) throws IOException {
		logger.finer("Move invoked: "+move);
		StringBuilder sb = new StringBuilder();
		sb.append("Plan.instance.change_skill(\"" + move + "\")");
		executeRubyScript(sb.toString());
		return;
		//sb.append("require 'high_skills/low_skill.rb'\n");
		//sb.append("@move = LowSkill.new(\""+move+"\") \n");
		//sb.append("while !@move.ended? \n");
		//sb.append("		@move.execute \n");
		//sb.append("end \n");
		
//		sb.append("require 'plan/plan.rb' \n");
//		sb.append("class MyPlan < Plan \n");
//		sb.append("end \n");
//		sb.append("@i = MyPlan.new() \n");
//		//sb.append("@i.insert \""+move+"\"  \n");
//		
//		sb.append("@i.insert \""+move+"\"  \n");
//		sb.append("puts \"INSERTED \"  \n");
		
//		executeRubyScript(sb.toString());
	}
	
	/**
	 * Sends a file to remote TFTP server. 
	 * 
	 * @param host	Remote TFTP server.
	 * @param saveFileAs	Determines how file on remote TFTP server will be 
	 * 						saved.
	 * @param inputStream	Input stream of data.
	 * @throws IOException	
	 */
	@TestCovered
	private void sendFile(String saveFileAs, InputStream inputStream) throws IOException {
		tftpClient.open();
		tftpClient.sendFile(saveFileAs, TFTPClient.BINARY_MODE, inputStream, getTFTPAddress().getAddress(), getTFTPAddress().getPort());
		tftpClient.close();
	}
	
	/**
	 * Invokes executing of ruby script on special TFTP server.
	 * Used for internal purposes with agent Jim.
	 * 
	 * @param script	UTF-8 String text to be executed on server side.
	 * @throws IOException
	 */	
	private void executeRubyScript(String script) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(script.getBytes("UTF-8"));
		tftpClient.open();
		tftpClient.sendFile("ruby.exec", TFTPClient.BINARY_MODE, bais, getTFTPAddress().getAddress(), getTFTPAddress().getPort());
		tftpClient.close();
		logger.finer("Executed ruby script on agent: " + script);
	}
	
	/**
	 * Returns a string that is used as a unique identifier of the agent
	 * in the AgentManager class. It consists of the agent's uniform number
	 * followed by its team name (no spaces)
	 */
	public String toString() {
		return String.valueOf(agentData.getUniformNumber()) + agentData.getTeamName();
	}

	public int getSceneGraphPlayer() {
		return sceneGraphPlayer;
	}

	public void setSceneGraphPlayer(int sceneGraphPlayer) {
		this.sceneGraphPlayer = sceneGraphPlayer;
	}
	
	public void setWorldModel(WorldModel model) {
		worldModel = model;
	}
	
	public WorldModel getWorldModel() {
		return worldModel;
	}
}
