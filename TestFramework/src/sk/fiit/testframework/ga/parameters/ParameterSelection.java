package sk.fiit.testframework.ga.parameters;

import sk.fiit.testframework.ga.SelectionType;

public class ParameterSelection {
	SelectionType type;
	
	public ParameterSelection(SelectionType type) {
		this.type=type;
	}

	public SelectionType getType() {
		return type;
	}
}
