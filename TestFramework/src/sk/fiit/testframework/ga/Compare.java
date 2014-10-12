package sk.fiit.testframework.ga;

import java.util.Comparator;

import sk.fiit.testframework.agenttrainer.models.AgentMove;

public class Compare implements Comparator<AgentMove>{
	@Override
	public int compare(AgentMove am1, AgentMove am2) {
		if(am1.getFitness()>am2.getFitness()){
			return -1;
		}
		else if(am1.getFitness()<am2.getFitness()){
			return 1;
		}
		return 0;
	}

	
	
}
