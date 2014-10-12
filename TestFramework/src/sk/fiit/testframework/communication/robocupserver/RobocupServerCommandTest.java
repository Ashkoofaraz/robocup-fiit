/**
 * Name:    TrainerCommandTest.java
 * Created: Feb 27, 2011
 * 
 * @author: relation
 */
package sk.fiit.testframework.communication.robocupserver;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * Test with aim to cover most frequently used trainer command tests and their
 * syntax.
 * 
 * @author relation
 * 
 */
public class RobocupServerCommandTest {

	@Test
	public void SexpressionTest() {

		RobocupServerCommand agent = new RobocupServerCommand.Agent(new Point3D(10, 5, 0), new Point3D(0, 0, 0), "ANDROIDS", 10);
		System.out.println(agent.getCommand());
		
	}
	@Test
	public void testJoint(){
		Map<Joint, Double> joint = new HashMap<Joint, Double>();
		joint.put(Joint.HE1, 90.0);
		joint.put(Joint.HE2, 90.0);
		joint.put(Joint.LAE1, 0.0);
		joint.put(Joint.LAE2, 0.0);
		joint.put(Joint.LAE3, 0.0);
		joint.put(Joint.LAE4, 0.0);
		joint.put(Joint.RAE1, 0.0);
		joint.put(Joint.RAE2, 0.0);
		joint.put(Joint.RAE3, 0.0);
		joint.put(Joint.RAE4, 0.0);
		joint.put(Joint.LLE1, 0.0);
		joint.put(Joint.LLE2, 0.0);
		joint.put(Joint.LLE3, 0.0);
		joint.put(Joint.LLE4, 0.0);
		joint.put(Joint.LLE5, 0.0);
		joint.put(Joint.LLE6, 0.0);
		joint.put(Joint.RLE1, 0.0);
		joint.put(Joint.RLE2, 0.0);
		joint.put(Joint.RLE3, 0.0);
		joint.put(Joint.RLE4, 0.0);
		joint.put(Joint.RLE5, 0.0);
		joint.put(Joint.RLE6, 0.0);
		

			
	}
}
