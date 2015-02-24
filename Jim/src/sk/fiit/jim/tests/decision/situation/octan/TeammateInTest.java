package sk.fiit.jim.tests.decision.situation.octan;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.octan.TeammateIn1L;
import sk.fiit.jim.decision.situation.octan.TeammateIn1R;
import sk.fiit.jim.decision.situation.octan.TeammateIn2L;
import sk.fiit.jim.decision.situation.octan.TeammateIn2R;
import sk.fiit.jim.decision.situation.octan.TeammateIn3L;
import sk.fiit.jim.decision.situation.octan.TeammateIn3R;
import sk.fiit.jim.decision.situation.octan.TeammateIn4L;
import sk.fiit.jim.decision.situation.octan.TeammateIn4R;
import sk.fiit.robocup.library.geometry.Vector3D;
/**
 * @author Michal Petras
 * test for TeamMateIn
 */
public class TeammateInTest {

	private void setup (Vector3D postition){
		Player teammate = new Player(new WorldModel(new AgentModel()), false, 4);
		teammate.setPosition(postition);
		WorldModel.getInstance().AddTeamPlayers(teammate);
		
		
	}

	/**
	 * Test for TeammateIn1L 
	 * x < -8 
	 * 0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L1Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new TeammateIn1L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new TeammateIn1L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new TeammateIn1L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertTrue(new TeammateIn1L().checkSituation());

	}
	
	/**
	 * Test for TeammateIn1R 
	 * x < -8 
	 * 0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R1Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new TeammateIn1R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new TeammateIn1R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new TeammateIn1R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertTrue(new TeammateIn1R().checkSituation());

	}
	
	/**
	 * Test for TeammateIn2L 
	 * -8 < x < 0 
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L2Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new TeammateIn2L().checkSituation());
		setup(Vector3D.cartesian(1, 2, 0));
		Assert.assertFalse(new TeammateIn2L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new TeammateIn2L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new TeammateIn2L().checkSituation());
		setup(Vector3D.cartesian(1, -2, 0));
		Assert.assertFalse(new TeammateIn2L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertTrue(new TeammateIn2L().checkSituation());

	}
	
	/**
	 * Test for TeammateIn2R 
	 * -8 < x < 0  
	 * 0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R2Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new TeammateIn2R().checkSituation());
		setup(Vector3D.cartesian(1, -2, 0));
		Assert.assertFalse(new TeammateIn2R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new TeammateIn2R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new TeammateIn2R().checkSituation());
		setup(Vector3D.cartesian(1, 2, 0));
		Assert.assertFalse(new TeammateIn2R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertTrue(new TeammateIn2R().checkSituation());

	}
	
	/**
	 * Test for TeammateIn3L 
	 *  0 < x < 8 
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L3Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-1, 2, 0));
		Assert.assertFalse(new TeammateIn3L().checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new TeammateIn3L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(new TeammateIn3L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-1, -2, 0));
		Assert.assertFalse(new TeammateIn3L().checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new TeammateIn3L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertTrue(new TeammateIn3L().checkSituation());

	}
	
	/**
	 * Test for TeammateIn3R 
	 *  0 < x < 8  
	 *  0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R3Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-1, -2, 0));
		Assert.assertFalse(new TeammateIn3R().checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new TeammateIn3R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(new TeammateIn3R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-1, 2, 0));
		Assert.assertFalse(new TeammateIn3R().checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new TeammateIn3R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertTrue(new TeammateIn3R().checkSituation());

	}
	/**
	 * Test for TeammateIn4L 
	 *  8 < x 
	 *  0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L4Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new TeammateIn4L().checkSituation());
		

		// wrong y
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new TeammateIn4L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new TeammateIn4L().checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertTrue(new TeammateIn4L().checkSituation());

	}
	
	/**
	 * Test for TeammateIn4R 
	 *  8 < x  
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R4Test() throws Exception {
		
		// wrong x
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(new TeammateIn4R().checkSituation());
	
		// wrong y
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new TeammateIn4R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(new TeammateIn4R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertTrue(new TeammateIn4R().checkSituation());
	}
}
