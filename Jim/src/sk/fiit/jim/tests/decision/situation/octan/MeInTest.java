package sk.fiit.jim.tests.decision.situation.octan;

import org.junit.Assert;
import org.junit.Test;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.decision.situation.octan.*;
import sk.fiit.robocup.library.geometry.Vector3D;

public class MeInTest {
	
	
	
	private void setup (Vector3D position){
		AgentModel.getInstance().setPosition(position);
	}

	@Test
	public void L1Test() throws Exception {
		MeIn1L L1 = new MeIn1L();
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
	

	@Test
	public void R1Test() throws Exception {
		MeIn1R R1 = new MeIn1R();
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
	

	@Test
	public void L2Test() throws Exception {
		MeIn2L L2 = new MeIn2L();
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
	

	@Test
	public void R2Test() throws Exception {
		MeIn2R R2 = new MeIn2R();
		
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
	

	@Test
	public void L3Test() throws Exception {
		MeIn3L L3 = new MeIn3L();
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
	
	
	@Test
	public void R3Test() throws Exception {
		MeIn3R R3 = new MeIn3R();
		
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
	
	
	@Test
	public void L4Test() throws Exception {
		MeIn4L L4 = new MeIn4L();
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
	

	@Test
	public void R4Test() throws Exception {
		MeIn4R R4 = new MeIn4R();
		
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
	
	
	
}
