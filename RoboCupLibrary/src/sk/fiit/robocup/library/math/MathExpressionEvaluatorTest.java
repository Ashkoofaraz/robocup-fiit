package sk.fiit.robocup.library.math;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.Matchers.closeTo;

import static org.junit.Assert.assertThat;

/**
 *  MathExpressionEvaluatorTest.java
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public class MathExpressionEvaluatorTest{
	@Test
	public void intResult(){
		assertThat(new MathExpressionEvaluator("7 +5").getInt(), is(12));
	}
	
	@Test
	public void doubleResult(){
		assertThat(new MathExpressionEvaluator("2.0**0.5").getDouble(), is(closeTo(1.41, 0.01)));
	}
}