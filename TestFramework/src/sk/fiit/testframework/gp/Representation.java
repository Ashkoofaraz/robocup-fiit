package sk.fiit.testframework.gp;

import java.util.LinkedList;

public class Representation {
	
	private  LinkedList<String> equatioin;
	
	private Double fitness;
	
	private Double diversion;
	
	private int fall;

	public LinkedList<String> getEquatioin() {
		return equatioin;
	}

	public void setEquatioin(LinkedList<String> equatioin) {
		this.equatioin = equatioin;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	
	public Representation getRepresentation() {
		return this;
	}

	public Double getDiversion() {
		return diversion;
	}

	public void setDiversion(Double diversion) {
		this.diversion = diversion;
	}

	public int getFall() {
		return fall;
	}

	public void setFall(int fall) {
		this.fall = fall;
	}
		
}
