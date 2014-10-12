/**
 * Name:    AgentMove.java
 * Created: 25.4.2011
 * 
 * @author: Roman Kovac
 */
package sk.fiit.testframework.agenttrainer.models;

import java.util.ArrayList;

import org.w3c.dom.Document;

/**
 * Represents a class holding information about agent move created by
 * AgentMoveReader.
 * 
 * @author Roman Kovac
 * 
 */
public class Representation implements Cloneable{

	private Document doc;
	private ArrayList<AgentMovePhase> phases;
	private String name;
	private Double fitness;
	
	
	
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Document getDoc() {
		return doc;
	}

	public void setPhases(ArrayList<AgentMovePhase> phases) {
		this.phases = phases;
	}

	public ArrayList<AgentMovePhase> getPhases() {
		return phases;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setFitness(Double fitness){
		this.fitness = fitness;
	}
	public Double getFitness(){
		return fitness;
	}
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
}
