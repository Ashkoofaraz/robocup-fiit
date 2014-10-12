/**
 * Name:    TurnAround.java
 * Created: Feb 26, 2012
 * 
 * @author: ivan
 */
package sk.fiit.testframework.trainer.testsuite.testcases.tournament;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
public class KickDistanceGA implements Runnable, ITestCaseObserver {
	private static Logger logger = Logger.getLogger(KickDistanceGA.class.getName());

	private List<TestCaseResult> testResults;
	
	@Override
	public void run() {
		testResults = new ArrayList<TestCaseResult>();
		Implementation impl = ImplementationFactory.getImplementationInstance();
		for (int i=0;i<100;i++){
		impl.enqueueTestCase(new KickDistanceGATest(), this);}
//		impl.enqueueTestCase(new KickDistanceTest(), this);
//		impl.enqueueTestCase(new KickDistanceTest(), this);
//		impl.enqueueTestCase(new KickDistanceTest(), this);
//		impl.enqueueTestCase(new KickDistanceTest(), this);
	}

	public void evaluateResult(List<TestCaseResult> results) {
		double result = 0;
		for (TestCaseResult testCaseResult : results) {
			double fitnes = testCaseResult.getFitness();
			if (fitnes > result) result = fitnes;
		}
		writeToFile(results);
		logger.info("TEST CASE ENDED successfully with result: " + result);
	}
	
	@Override
	public void testFinished(TestCaseResult result) {
		testResults.add(result);
		if (testResults.size() == 100) {
			evaluateResult(testResults);
			testResults.clear();
		}
	}
	
	private void writeToFile(List<TestCaseResult> results) {
		Writer writer = null;
		try {
			int i=0;
			writer = new BufferedWriter(new FileWriter(new File("E:\\dp-robocup\\kick100.csv"),true));
		    for (TestCaseResult result:results){
		    	writer.append(""+ (i++) +";");
		    	writer.append(""+result.getFitness());
		    	writer.append(";");
		    	writer.append("\r\n");
		    }
		    
		    
		    
		} catch (IOException ex) {
		} finally {
		   try {
			   writer.close();
		   } catch (Exception ex) {
			   
		   }
		}
		
	}
	
}
