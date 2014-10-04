package sk.fiit.robocup.library.math;

import sk.fiit.robocup.library.init.Script;
import sk.fiit.robocup.library.review.ReviewOk;

/**
 *  MathExpressionEvaluator.java
 *  
 *  	Transforms a mathematical expression given as a String into a
 *  	numerical result.
 *  <p>new {@link MathExpressionEvaluator}("7+5").getInt() == 12</p>
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
@ReviewOk
public class MathExpressionEvaluator{
	private	 static class DoubleHolder{
		public double value = 0.0;
	}
	private final String expression;

	public MathExpressionEvaluator(String expression){
		this.expression = expression;
	}
	
	/**
	 * @return the result of the expression, trimmed onto an integer value
	 */
	public int getInt(){
		return (int)getDouble();
	}
	
	/**
	 * @return the result of the expression
	 */
	public double getDouble(){
		String code = String.format("$result.value = (%s)", expression);
		Script script = Script.createScript(code);
		DoubleHolder holder = new DoubleHolder();
		script.registerBean("result", holder);
		script.execute();
		return holder.value;
	}
}