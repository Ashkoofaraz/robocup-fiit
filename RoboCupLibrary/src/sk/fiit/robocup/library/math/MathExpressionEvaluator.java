package sk.fiit.robocup.library.math;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
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
 *@author Tomas Nemecek
 */
@ReviewOk
public class MathExpressionEvaluator{
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
		Evaluator mEvaluator = new Evaluator();
		try {
			String value = mEvaluator.evaluate(expression);
			double a = Double.parseDouble(value);
			return a;
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		return 0;
	}
}