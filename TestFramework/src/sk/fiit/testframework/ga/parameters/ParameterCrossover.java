package sk.fiit.testframework.ga.parameters;

import sk.fiit.testframework.ga.CrossoverType;

public class ParameterCrossover {
	CrossoverType type;
	int numberOfPointsForCrossover = 0;
	
	public ParameterCrossover(CrossoverType type, int crossPoint) {
		this.type=type;
		this.numberOfPointsForCrossover = crossPoint;
	}

	public CrossoverType getType() {
		return type;
	}


	public int getNumberOfPointsForCrossover() {
		return numberOfPointsForCrossover;
	}
	
	
}
