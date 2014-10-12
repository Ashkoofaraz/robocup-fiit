package sk.fiit.testframework.gp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public enum Operators{
	PLUS("+"),
	MINUS("-"),
	KRAT("*"),
	DELENE("/"),
	SIN("sin"),
	ZATVORKAL("("),
	ZATVORKAP(")");
	
	private String operator;
	
	private static final List<Operators> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final List<Operators> GeneralOperator =
		    Collections.unmodifiableList(Arrays.asList(PLUS,
		    	MINUS,
		    	KRAT,
		    	DELENE
		    	));
	private static final int SIZE = VALUES.size();
	private static final int GENSIZE = GeneralOperator.size();
  	private static final Random RANDOM = new Random();

		  
	Operators(String operator) {
		this.operator = operator;	
	}
	
	public static Operators randomOperators()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	public static Operators randomGenOperators()  {
	    return GeneralOperator.get(RANDOM.nextInt(GENSIZE));
	}
	
	public static boolean isOperator(String op){		 
		
		for(Operators opp :GeneralOperator){
			if(opp.getOperator().equals(op)){
				return true;
			}
		}
		if(op.equals(Operators.SIN.getOperator())){
			return true;
		}
		return false;
	}
	public String getOperator(){
		return operator;
	}
	
	public static List<Operators> getListOperators(){
		return VALUES;
	}
	
}
