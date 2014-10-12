/**
 * Name:    TurnAround.java
 * Created: Feb 26, 2012
 * 
 * @author: ivan
 */
package sk.fiit.testframework.ga.testMove;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.init.Implementation;
import sk.fiit.testframework.init.ImplementationFactory;
import sk.fiit.testframework.trainer.testsuite.ITestCaseObserver;
import sk.fiit.testframework.trainer.testsuite.TestCaseResult;

/**
 * TODO: Replace with a brief purpose of class / interface.
 * 
 * @author ivan
 *
 */
public class KickMove implements Runnable, ITestCaseObserver {
	private static Logger logger = Logger.getLogger(KickMove.class.getName());

	private List<TestCaseResult> testResults;

	private AgentJim agent;

	private ParameterGA paramGA;
	private AgentMove move;
	private TestTypeParameter paramTT;
	private EnvironmentSetting eS;
	private AgentMoveWriter write;
	private boolean finish=false;
	
	public KickMove(AgentJim agent,AgentMove move, EnvironmentSetting eS, ParameterGA paramGA, TestTypeParameter paramTT){
		testResults = new ArrayList<TestCaseResult>();
		this.agent=agent;
		this.eS=eS;
		this.move=move;
		this.paramGA=paramGA;
		this.paramTT=paramTT;
		write = new AgentMoveWriter(); 
	}
	@Override
	public void run() {
		
		Implementation impl = ImplementationFactory.getImplementationInstance();
		for(int i=0;i<paramTT.getNumberTestOnMove();i++){
			impl.enqueueTestCase(new KickMoveTest(agent, move,  eS,  paramGA,  paramTT), this);
		}
	}

	public void evaluateResult(List<TestCaseResult> results) {
		double result = 0;
		for (TestCaseResult testCaseResult : results) {
			double fitnes = testCaseResult.getFitness();
			if (fitnes > result){
				result = fitnes;
				move.setFitness(result);
			}
		}
		logger.info("TEST CASE ENDED successfully with result: " + result);
	}
	
	@Override
	public void testFinished(TestCaseResult result) {
		Date date = new Date();
    	DateFormat df = new SimpleDateFormat("HH_mm_ss");
		testResults.add(result);
		if (testResults.size() == paramTT.getNumberTestOnMove()) {
			evaluateResult(testResults);
			testResults.clear();
			try {
				write.write(move, paramGA.getDestination()+"\\"+ paramGA.getMoveName() +"_" + df.format(date) + "_"+ move.getFitness()+".xml");
				finish=true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isFinished(){
		return finish;
	}
}
