/**
 * Name:    Walk.java
 * Created: Feb 26, 2012
 * 
 * @author: ivan
 */
package sk.fiit.testframework.ga.testMove;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.simulation.AlgorithmType;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.gp.Representation;
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
public class WalkMove implements Runnable, ITestCaseObserver {
	private static Logger logger = Logger.getLogger(WalkMove.class.getName());
	
	private List<TestCaseResult> testResults;
	private AgentJim agent;

	private ParameterGA paramGA;
	private AgentMove move;
	private TestTypeParameter paramTT;
	private EnvironmentSetting eS;
	private AgentMoveWriter write;
	private boolean finish=false;
	private Representation eq;
	private AlgorithmType algType;
	
	public WalkMove(AgentJim agent,AgentMove move, Representation eq, EnvironmentSetting eS, ParameterGA paramGA, TestTypeParameter paramTT, AlgorithmType algType) {
		testResults = new ArrayList<TestCaseResult>();
		this.agent=agent;
		this.eq = eq;
		this.eS=eS;
		this.move=move;
		this.paramGA=paramGA;
		this.paramTT=paramTT;
		this.algType = algType;
		write = new AgentMoveWriter();     
	}
	
	@Override
	public void run() {
		Implementation impl = ImplementationFactory.getImplementationInstance();
		for(int i=0;i<paramTT.getNumberTestOnMove();i++){
			impl.enqueueTestCase(new WalkMoveTest(agent, move,  eq, eS,  paramGA,  paramTT, algType), this);
		}
		//impl.enqueueTestCase(new WalkStableTest(), this);
	}

	public void evaluateStableTestResults(List<TestCaseResult> results) {
		double result = paramTT.getMaxTimeOnMove()*100;//Double.MAX_VALUE;
		for (TestCaseResult testCaseResult : results) {
			double fitnes = testCaseResult.getFitness();
			if (fitnes <= result && fitnes > 0) {
				result = fitnes;
				move.setFitness(result);
				if(algType.isGeneticPrograming()){
					eq.setFitness(result);
					eq.setDiversion(testCaseResult.getDiversion());
					eq.setFall(testCaseResult.getFall());
				}
			}
		}
		if(result == paramTT.getMaxTimeOnMove()*100){
			result = 0.0;
			move.setFitness(null);
			if(algType.isGeneticPrograming())
			eq.setFitness(null);
		}
		
		logger.info("TEST CASE ENDED successfully with result: " + result);
	}
	
	@Override
	public void testFinished(TestCaseResult result) {
		Date date = new Date();
    	DateFormat df = new SimpleDateFormat("HH_mm_ss");
		testResults.add(result);
		if (testResults.size() == paramTT.getNumberTestOnMove()) {
			evaluateStableTestResults(testResults);
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
