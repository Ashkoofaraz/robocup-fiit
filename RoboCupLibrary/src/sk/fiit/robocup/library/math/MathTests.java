package sk.fiit.robocup.library.math;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sk.fiit.robocup.library.geometry.AnglesTest;
import sk.fiit.robocup.library.geometry.Vector3DTest;

/**
 *  MathTests.java
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AnglesTest.class,
	KalmanTest.class,
	Vector3DTest.class
})
public class MathTests{}