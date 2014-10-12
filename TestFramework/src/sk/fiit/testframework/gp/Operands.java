package sk.fiit.testframework.gp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Operands {
		
	X("X");
	
	private static Random RANDOM = new Random();
	
	private String value;
	private static List<String> values = Collections.unmodifiableList(Arrays.asList("0","1","2","3","4","5","6","7","8","9","X","X","X","X","X","X","X","X"));
	
	Operands(String value){
		this.value = value;
	}
	
	public static String randomGenOperand()  {
	    return values.get(RANDOM.nextInt(18));
	}
	
	public String getValue(){
		return this.value;
	}
	
	
public static boolean isOperands(String op){		 
		
		for(String opp :values){
			if(opp.equals(op)){
				return true;
			}
		}		
		return false;
	}
	
}
