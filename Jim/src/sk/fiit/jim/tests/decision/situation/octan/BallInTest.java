package sk.fiit.jim.tests.decision.situation.octan;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.octan.BallIn1L;
import sk.fiit.jim.decision.situation.octan.BallIn1R;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallIn3L;
import sk.fiit.jim.decision.situation.octan.BallIn3R;
import sk.fiit.jim.decision.situation.octan.BallIn4L;
import sk.fiit.jim.decision.situation.octan.BallIn4R;
import sk.fiit.jim.decision.situation.octan.BallInMid1;
import sk.fiit.jim.decision.situation.octan.BallInMid2;
import sk.fiit.jim.decision.situation.octan.BallInMid3;
import sk.fiit.jim.decision.situation.octan.BallInMid4;
import sk.fiit.robocup.library.geometry.Vector3D;
/**
 * @author Samuel Benkovic
 * test for Ball In
 */
public class BallInTest {
	
	
	
	private void setup (Vector3D postition){
		WorldModel.getInstance().getBall().setPosition(postition);
		WorldModel.getInstance().getBall().setPosition(postition);
	}
	/**
	 * Test for BallInL1
	 * x < -8
	 * y >  0
	 * 
	 * @throws Exception
	 */
	@Test
	public void L1Test() throws Exception {
		BallIn1L L1 = new BallIn1L();
		// wrong x
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(L1.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(L1.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(L1.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-9, 8, 0));
		Assert.assertTrue(L1.checkSituation());
		
		}
	
	/**
	 * Test for BallIn1R
	 * x < -8
	 * y <  0
	 * 
	 * @throws Exception
	 */
	@Test
	public void R1Test() throws Exception {
		BallIn1R R1 = new BallIn1R();
		// wrong x
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(R1.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(R1.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(R1.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertTrue(R1.checkSituation());
		}
	
	/**
	 * Test for BallIn2L
	 * -8 < x < 0
	 *  y > 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void L2Test() throws Exception {
		BallIn2L L2 = new BallIn2L();
		// wrong x
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(L2.checkSituation());
		setup(Vector3D.cartesian(2, 2, 0));
		Assert.assertFalse(L2.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertFalse(L2.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(L2.checkSituation());
		setup(Vector3D.cartesian(2, -2, 0));
		Assert.assertFalse(L2.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertTrue(L2.checkSituation());
		}
	
	/**
	 * Test for BallIn2R
	 * -8 < x < 0
	 *  y < 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void R2Test() throws Exception {
		BallIn2R R2 = new BallIn2R();
		
		// wrong x
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(R2.checkSituation());
		setup(Vector3D.cartesian(2, -2, 0));
		Assert.assertFalse(R2.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-7, 2, 0));
		Assert.assertFalse(R2.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(R2.checkSituation());
		setup(Vector3D.cartesian(2, 2, 0));
		Assert.assertFalse(R2.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-7, -2, 0));
		Assert.assertTrue(R2.checkSituation());
		}
	
	/**
	 * Test for BallIn3L
	 * 	0 < x < 8
	 * 	y > 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void L3Test() throws Exception {
		BallIn3L L3 = new BallIn3L();
		// wrong x
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(L3.checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(L3.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(L3.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(L3.checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(L3.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertTrue(L3.checkSituation());
		}
	
	/**
	 * Test for BallIn3R
	 * 	0 < x < 8
	 * 	y < 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void R3Test() throws Exception {
		BallIn3R R3 = new BallIn3R();
		
		// wrong x
		setup(Vector3D.cartesian(-9, -2, 0));
		Assert.assertFalse(R3.checkSituation());
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(R3.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(R3.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-9, 2, 0));
		Assert.assertFalse(R3.checkSituation());
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(R3.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertTrue(R3.checkSituation());
		}
	
	/**
	 * Test for BallIn4L
	 * 	8 < x 
	 * 	y > 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void L4Test() throws Exception {
		BallIn4L L4 = new BallIn4L();
		// wrong x
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(L4.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertFalse(L4.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(L4.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertTrue(L4.checkSituation());
		}
	
	/**
	 * Test for BallIn4L
	 * 	8 < x 
	 * 	y < 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void R4Test() throws Exception {
		BallIn4R R4 = new BallIn4R();
		
		// wrong x
		setup(Vector3D.cartesian(7, -2, 0));
		Assert.assertFalse(R4.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(9, 2, 0));
		Assert.assertFalse(R4.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(7, 2, 0));
		Assert.assertFalse(R4.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(9, -2, 0));
		Assert.assertTrue(R4.checkSituation());
		}
	
	/**
	 * Test for Mid1Test
	 * 	x < -8 
	 * 	-3,5 < y <  3,5
	 * 
	 * @throws Exception
	 */
	@Test
	public void M1Test() throws Exception {
		BallInMid1 M1 = new BallInMid1();
		
		// wrong x
		setup(Vector3D.cartesian(-7, 0, 0));
		Assert.assertFalse(M1.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-9, 4, 0));
		Assert.assertFalse(M1.checkSituation());
		setup(Vector3D.cartesian(-9, -4, 0));
		Assert.assertFalse(M1.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-7, 4, 0));
		Assert.assertFalse(M1.checkSituation());
		setup(Vector3D.cartesian(-7, -4, 0));
		Assert.assertFalse(M1.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-9, 0, 0));
		Assert.assertTrue(M1.checkSituation());
		}
	
	/**
	 * Test for Mid2Test
	 * -8   <   x   < 0 
	 * -3,5 <   y   <  3,5
	 * 
	 * @throws Exception
	 */
	@Test
	public void M2Test() throws Exception {
		BallInMid2 M2 = new BallInMid2();
		
		// wrong x
		setup(Vector3D.cartesian(-9, 0, 0));
		Assert.assertFalse(M2.checkSituation());
		setup(Vector3D.cartesian(4, 0, 0));
		Assert.assertFalse(M2.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(-7, 4, 0));
		Assert.assertFalse(M2.checkSituation());
		setup(Vector3D.cartesian(-7, -4, 0));
		Assert.assertFalse(M2.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-9, 4, 0));
		Assert.assertFalse(M2.checkSituation());
		setup(Vector3D.cartesian(4, 4, 0));
		Assert.assertFalse(M2.checkSituation());
		setup(Vector3D.cartesian(-9, -4, 0));
		Assert.assertFalse(M2.checkSituation());
		setup(Vector3D.cartesian(4, -4, 0));
		Assert.assertFalse(M2.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(-7, 0, 0));
		Assert.assertTrue(M2.checkSituation());
		}
	
	/**
	 * Test for Mid3Test
	 * 0    <   x   < 8 
	 * -3,5 <   y   <  3,5
	 * 
	 * @throws Exception
	 */
	@Test
	public void M3Test() throws Exception {
		BallInMid3 M3 = new BallInMid3();
		
		// wrong x
		setup(Vector3D.cartesian(-7, 0, 0));
		Assert.assertFalse(M3.checkSituation());
		setup(Vector3D.cartesian(9, 0, 0));
		Assert.assertFalse(M3.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(7, 4, 0));
		Assert.assertFalse(M3.checkSituation());
		setup(Vector3D.cartesian(7, -4, 0));
		Assert.assertFalse(M3.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(-7, 4, 0));
		Assert.assertFalse(M3.checkSituation());
		setup(Vector3D.cartesian(9, 4, 0));
		Assert.assertFalse(M3.checkSituation());
		setup(Vector3D.cartesian(-7, -4, 0));
		Assert.assertFalse(M3.checkSituation());
		setup(Vector3D.cartesian(9, -4, 0));
		Assert.assertFalse(M3.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(7, 0, 0));
		Assert.assertTrue(M3.checkSituation());
		}
	
	/**
	 * Test for Mid4Test
	 * 8    <   x 
	 * -3,5 <   y   <  3,5
	 * 
	 * @throws Exception
	 */
	@Test
	public void M4Test() throws Exception {
		BallInMid4 M4 = new BallInMid4();
		
		// wrong x
		setup(Vector3D.cartesian(0, 0, 0));
		Assert.assertFalse(M4.checkSituation());
		
		// wrong y
		setup(Vector3D.cartesian(9, 4, 0));
		Assert.assertFalse(M4.checkSituation());
		setup(Vector3D.cartesian(9, -4, 0));
		Assert.assertFalse(M4.checkSituation());
		
		// wrong x,y
		setup(Vector3D.cartesian(0, 4, 0));
		Assert.assertFalse(M4.checkSituation());
		setup(Vector3D.cartesian(0, -4, 0));
		Assert.assertFalse(M4.checkSituation());
		
		// both ok.
		setup(Vector3D.cartesian(9, 0, 0));
		Assert.assertTrue(M4.checkSituation());
		}
	
}
