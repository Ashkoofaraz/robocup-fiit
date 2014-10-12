package gaTest;

import static org.junit.Assert.*;

import org.junit.Test;

import sk.fiit.testframework.ga.Crossover;

public class crossoverTest extends AbstractTest{
	Crossover cross = new Crossover();
	@Test
	public void onepointCross() throws Exception {
		start();
		cross.onePointCrossover(move, move2);
	}
	
	@Test
	public void twoPointCrossover() throws Exception{
		start();
		cross.twoPointCrossover(move, move2);
	}
	@Test
	public void morePointCrossover() throws Exception{
		start();
		cross.morePointCrossover(move2, move2, 5);
	}

}
