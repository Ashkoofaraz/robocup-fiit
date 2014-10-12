package sk.fiit.testframework.gp;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Logger;

import sk.fiit.testframework.ga.GeneticAlgorithm;

public class InfixPostfixEvaluator {

	private static Logger logger = Logger.getLogger(InfixPostfixEvaluator.class.getName());

    public Double evalInfix(Representation infix) {
            return evaluatePostfix(convert2Postfix(infix));
    }

    public LinkedList<String> convert2Postfix(Representation infixExpr) {

    	Stack<String> stack = new Stack<String>();
            LinkedList<String> postFix = new LinkedList<String>();
            
          	for(String c: infixExpr.getEquatioin()){
          		if(isOperand(c)|| isNumeric(c)){
          			postFix.add(c);
          		} else if (isOperator(c)) {
                            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                                    if (operatorGreaterOrEqual(stack.peek(), c)) {
                                            postFix.add(stack.pop());
                                    } else {
                                            break;
                                    }
                            }
                            stack.push(c);
                    } else if (c.equals("(")) {
                            stack.push(c);
                    } else if (c.equals(")")) {
                            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                                    postFix.add(stack.pop());
                            }
                            if (!stack.isEmpty()) {
                                    stack.pop();
                            }
                    } 
            }
            while (!stack.empty()) {
                    postFix.add(stack.pop());
            }
            
            return postFix;
    }
    
    private boolean isNumeric(String str){
		try{
			double number = Double.parseDouble(str);
		}catch(NumberFormatException ex){
			return false;
		}
		return true;
	}

    public Double evaluatePostfix(LinkedList<String> postfixExpr) {
            Stack<Double> stack = new Stack<Double>();
            
          for(String c:postfixExpr){
                    
            	
        			if(isNumeric(c)){
        				stack.push(Double.parseDouble(c));
        			}
                    	else if (isOperator(c)) {
                    		evaluate(c, stack);
                    }
            }
            return stack.pop();
    }
    private int getPrecedence(String c) {
            int ret = 0;
            if (c.equals("-") || c.equals("+")) {
                    ret = 1;
            } else if (c.equals("/")) {
                    ret = 2;
            }
            else if(c.equals("*")){
            	ret = 3;
            }else if(c.equals("sin")){
            	ret = 4;
            }
            
            return ret;
    }
    private boolean operatorGreaterOrEqual(String string, String c) {
            return getPrecedence(string) >= getPrecedence(c);
    }

    private boolean isOperator(String c) {
    	return Operators.isOperator(c);
    }
    
    private boolean isOperand(String c){
    	return Operands.isOperands(c);
    }

    
    private void evaluate(String op, Stack<Double> stack){
		double eval=0.0;
		try{
		switch (op) {
			case "/":
				double op1 = stack.pop();
				double op2 = stack.pop();
				eval = op2/op1; 
				stack.push(eval);
				break;
			case "*":
				eval = stack.pop()*stack.pop(); 
				stack.push(eval);
				break;
			case "-":
				eval = -stack.pop()+stack.pop(); 
				stack.push(eval);
				break;
			case "+":
				eval = stack.pop()+stack.pop(); 
				stack.push(eval);
				break;
			case "sin":
				eval = Math.sin(stack.pop()); 
				stack.push(eval);
				break;
		}
		}catch(EmptyStackException ex){
			stack.push(0.0);
//			logger.info(ex.getMessage());
			
		}
	}
    
}
