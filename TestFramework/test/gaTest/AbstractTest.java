package gaTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sk.fiit.testframework.agenttrainer.AgentMoveReader;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.ga.Mutation;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.parameters.ParameterMutation;

public abstract class AbstractTest {
	AgentMoveReader read = new AgentMoveReader();
	ArrayList<AgentMove> oldPop = new ArrayList<AgentMove>();
	ArrayList<AgentMove> newPop = new ArrayList<AgentMove>();
	AgentMove move;
	AgentMove move2;
	Mutation mut = new Mutation();
	ParameterGA paramGA = new ParameterGA();
	protected void start() throws Exception{
		
		move = read.read("E:\\dp-robocup\\Jim\\moves\\walk_forward.xml");
		move.setFitness(5.2);
		move2 = read.read("E:\\dp-robocup\\Jim\\moves\\walk_forward.xml");
		move2.setFitness(6.2);
		AgentMove move3 = read.read("E:\\dp-robocup\\Jim\\moves\\walk_forward.xml");
		move3.setFitness(2.2);
		AgentMove move4 = read.read("E:\\dp-robocup\\Jim\\moves\\walk_forward.xml");
		move4.setFitness(4.2);
		ParameterMutation paramMut = new ParameterMutation();
		paramMut.setRangeMutation(20);
		paramMut.setPercentOfMutation(20);
		//paramMut.setJoint(joint);
		paramGA.setParamMut(paramMut);
		mut.mutateB(move2, paramGA);
		oldPop.add(move);
		oldPop.add(move2);
		
		newPop.add(move);
		newPop.add(move2);
		newPop.add(move3);
		newPop.add(move4);
	}
	

}
