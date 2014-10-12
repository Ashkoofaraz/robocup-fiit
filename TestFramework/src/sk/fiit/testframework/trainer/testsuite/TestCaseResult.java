/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.fiit.testframework.trainer.testsuite;

/**
 *
 * @author relation
 */
public class TestCaseResult {

    private double fitness;
    
    private double diversion;
    
    private int fall;

    public TestCaseResult(){}
    
    public TestCaseResult(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    public double getDiversion() {
		return diversion;
	}

	public void setDiversion(double diversion) {
		this.diversion = diversion;
	}

	public int getFall() {
		return fall;
	}

	public void setFall(int fall) {
		this.fall = fall;
	}

	@Override
    public String toString() {
        return String.valueOf(fitness);
    }

}
