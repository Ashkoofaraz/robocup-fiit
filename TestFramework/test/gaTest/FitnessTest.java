package gaTest;

import static org.junit.Assert.*;

import org.junit.Test;

import sk.fiit.robocup.library.geometry.Vector3;
import sk.fiit.testframework.ga.Fitness;
import sk.fiit.testframework.parsing.models.EnvironmentPart;

public class FitnessTest {

	@Test
	public void test() {
		Fitness f = new Fitness();
		EnvironmentPart ep = new EnvironmentPart();
		ep.setFieldHeight(30.0);
		ep.setFieldWidth(20.0);
//		System.out.println(f.diversionPenalty(new Vector3(30.0,20,0), 1.0, ep));
	}

}
