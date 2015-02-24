package sk.fiit.jim.tests.decision.situation.octan;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.octan.EnemyIn1L;
import sk.fiit.jim.decision.situation.octan.EnemyIn1R;
import sk.fiit.jim.decision.situation.octan.EnemyIn2L;
import sk.fiit.jim.decision.situation.octan.EnemyIn2R;
import sk.fiit.jim.decision.situation.octan.EnemyIn3L;
import sk.fiit.jim.decision.situation.octan.EnemyIn3R;
import sk.fiit.jim.decision.situation.octan.EnemyIn4L;
import sk.fiit.jim.decision.situation.octan.EnemyIn4R;
import sk.fiit.robocup.library.geometry.Vector3D;
/**
 * @author Samuel Benkovic
 * test for Ball In
 */
public class EnemyInTest {

	private void setup (Vector3D postition){
		Player newOpponent = new Player(new WorldModel(new AgentModel()), false, 4);
		newOpponent.setPosition(postition);
		WorldModel.getInstance().AddOpponentPlayers(newOpponent);
		
		
	}

	/**
	 * Test for EnemyIn1L 
	 * x < -8 
	 * 0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L1Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new EnemyIn1L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new EnemyIn1L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new EnemyIn1L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertTrue(new EnemyIn1L().checkSituation());

	}
	
	/**
	 * Test for EnemyIn1R 
	 * x < -8 
	 * 0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R1Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new EnemyIn1R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new EnemyIn1R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new EnemyIn1R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertTrue(new EnemyIn1R().checkSituation());

	}
	
	/**
	 * Test for EnemyIn2L 
	 * -8 < x < 0 
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L2Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new EnemyIn2L().checkSituation());
		setup(Vector3D.cartesian(1, 2, 0));
		Assert.assertFalse(new EnemyIn2L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new EnemyIn2L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new EnemyIn2L().checkSituation());
		setup(Vector3D.cartesian(1, -2, 0));
		Assert.assertFalse(new EnemyIn2L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertTrue(new EnemyIn2L().checkSituation());

	}
	
	/**
	 * Test for EnemyIn2R 
	 * -8 < x < 0  
	 * 0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R2Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(new EnemyIn2R().checkSituation());
		setup(Vector3D.cartesian(1, -2, 0));
		Assert.assertFalse(new EnemyIn2R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new EnemyIn2R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(new EnemyIn2R().checkSituation());
		setup(Vector3D.cartesian(1, 2, 0));
		Assert.assertFalse(new EnemyIn2R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertTrue(new EnemyIn2R().checkSituation());

	}
	
	/**
	 * Test for EnemyIn3L 
	 *  0 < x < 8 
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L3Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-1, 2, 0));
		Assert.assertFalse(new EnemyIn3L().checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new EnemyIn3L().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(new EnemyIn3L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-1, -2, 0));
		Assert.assertFalse(new EnemyIn3L().checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new EnemyIn3L().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertTrue(new EnemyIn3L().checkSituation());

	}
	
	/**
	 * Test for EnemyIn3R 
	 *  0 < x < 8  
	 *  0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R3Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-1, -2, 0));
		Assert.assertFalse(new EnemyIn3R().checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new EnemyIn3R().checkSituation());

		// wrong y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(new EnemyIn3R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-1, 2, 0));
		Assert.assertFalse(new EnemyIn3R().checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new EnemyIn3R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertTrue(new EnemyIn3R().checkSituation());

	}
	/**
	 * Test for EnemyIn4L 
	 *  8 < x 
	 *  0 < y
	 * 
	 * @throws Exception
	 */
	@Test
	public void L4Test() throws Exception {

		// wrong x
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(new EnemyIn4L().checkSituation());
		

		// wrong y
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(new EnemyIn4L().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(new EnemyIn4L().checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertTrue(new EnemyIn4L().checkSituation());

	}
	
	/**
	 * Test for EnemyIn4R 
	 *  8 < x  
	 *  0 > y
	 * 
	 * @throws Exception
	 */
	@Test
	public void R4Test() throws Exception {
		
		// wrong x
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(new EnemyIn4R().checkSituation());
	
		// wrong y
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(new EnemyIn4R().checkSituation());

		// wrong x,y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(new EnemyIn4R().checkSituation());

		// both ok.
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertTrue(new EnemyIn4R().checkSituation());
	}
}
