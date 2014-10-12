package sk.fiit.testframework.gp;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

import sk.fiit.testframework.ga.SelectionType;

public class GeneticProgramming {
	
	private static GeneticProgramming instance = null; 
	
//	private LinkedList<LinkedList<String>> postFixRepresentation = new LinkedList<LinkedList<String>>();
	
	private LinkedList<Representation> postFixRepresentation = new LinkedList<Representation>();
	
//	private LinkedList<Representation> inFixRepresentation = new LinkedList<Representation>();
	
	private InfixPostfixEvaluator representation = new InfixPostfixEvaluator();
	
	private String value;

//	private LinkedList<Representation> newInFixRepresentation = new LinkedList<Representation>();

	private LinkedList<Representation> newPostFixRepresentation = new LinkedList<Representation>();
	
	private static Logger logger = Logger.getLogger(GeneticProgramming.class.getName());
	
//	private Crossover crossover;
//	private Selection selection;
	
	public static GeneticProgramming getInstance() {
		if(instance == null){
			instance = new GeneticProgramming();
		}
		return instance;
	}
	
	private GeneticProgramming() {
//		this.inFixRepresentation = generateEquation(5);
//		crossover = new Crossover();
//		selection = new Selection();
	}
	
	public void setXValue(Double value){
		this.value = value.toString();
	}
	
	public Representation generateEquation(int maxDepth){
		int depth = randomGenerator(1, maxDepth);
		System.out.println(depth);
		System.out.println(Math.pow(2, depth));
		LinkedList<String> infix = new LinkedList<String>();
		int countParantheses=0;
		boolean wasParantheses = false;
		infix.add("(");
		String opp = Operands.randomGenOperand();
		infix.add(opp);
		
		for (int i=0; i<Math.pow(2, depth); i++){			
			Operators op = Operators.randomOperators();
			if (op.equals(Operators.SIN) || op.equals(Operators.ZATVORKAL)){
				opp = Operators.randomGenOperators().getOperator();
				infix.add(opp);
				countParantheses++;
				wasParantheses = true;
			}
						
			if(op.equals(Operators.ZATVORKAP) && !wasParantheses){
				continue;
			}else if(op.equals(Operators.ZATVORKAP)){
				countParantheses--;
			}
			infix.add(op.getOperator());
			if(op.equals(Operators.SIN)){
				infix.add(Operators.ZATVORKAL.getOperator());
			}
			if(infix.getLast().equals(Operators.ZATVORKAP.getOperator())){
				infix.add(Operators.randomGenOperators().getOperator());
			}
			opp=Operands.randomGenOperand();
			infix.add(opp);
		}
		while(countParantheses>0){
			infix.add(Operators.ZATVORKAP.getOperator());
			countParantheses--;
		}
		infix.add(")");				
		Representation rep = new Representation();
//		rep.setEquatioin(changeXtovalue(infix));
		rep.setEquatioin(infix);
		
		return  rep.getRepresentation();
	}
	/**
	 * add infix equation to infix list
	 * convert to postfix and add to postfix list 
	 * @param infix
	 */
	public void addNewEquation(LinkedList<String> infix, Double fitness){
		Representation repInf = new Representation();
		repInf.setEquatioin(infix);
		repInf.setFitness(fitness);
		
//		
//		inFixRepresentation.add(repInf);		
		Representation repPost = new Representation();
		repPost.setEquatioin(representation.convert2Postfix(repInf));
		postFixRepresentation.add(repPost);	
		
		Representation repNewPost = new Representation();
		repNewPost.setEquatioin(representation.convert2Postfix(repInf));
		repNewPost.setFitness(fitness);
			
		
		addToNewRepresentation(repNewPost);
	}
	
	public void addToNewRepresentation(Representation postFix){
//		Representation repInf = new Representation();
//		repInf.setEquatioin(infix.getEquatioin());
//		newInFixRepresentation.add(infix);
		Representation repPost = new Representation();
		repPost.setEquatioin(representation.convert2Postfix(postFix));
		repPost.setFitness(postFix.getFitness());
		newPostFixRepresentation.add(repPost);
	}
	public void addToNewPostFixRepresentation(LinkedList<String> postFix, Double fitness){
		Representation rep = new Representation();
		rep.setEquatioin(postFix);
		rep.setFitness(fitness);
		newPostFixRepresentation.add(rep);
	}
	public LinkedList<Representation> getNewPostFixRepresentation(){
		
		return newPostFixRepresentation;
	}
//	public LinkedList<Representation> getNewInFixRepresentation(){
//		return newInFixRepresentation;
//	}
	public void generateAddNewEquation(int maxDepth){
		
		Representation infix = generateEquation(maxDepth);
		Representation repInf = new Representation();
		repInf.setEquatioin(infix.getEquatioin());
//		inFixRepresentation.add(repInf);		
		
		Representation rep = new Representation();
		rep.setEquatioin(representation.convert2Postfix(repInf));
		postFixRepresentation.add(rep);		
	}
		
//	public double evaluateEquation(Representation infix){
//				
//		return representation.evalInfix(infix); 
//	}
	
	public double evaluate(Representation postFix){
		return representation.evaluatePostfix(postFix.getEquatioin());
	}
	
	public Representation changeXtovalue(Representation equation) {
		Representation rep = new Representation();
		LinkedList<String> eq = new LinkedList<>();
		eq.addAll(equation.getEquatioin());
		rep.setEquatioin(eq);
		for(int i=0; i<rep.getEquatioin().size(); i++){
			if(rep.getEquatioin().get(i).equals("X")){
				rep.getEquatioin().set(i, this.value);
			}
		}
		return rep;
//		return equation;
		
	}
	
	public LinkedList<Representation> getPostfixList(){
		return postFixRepresentation;
	}
	
//	public LinkedList<Representation> getInfixList(){
//		return inFixRepresentation;
//	}
	
	
	public int randomGenerator(int max){
		return randomGenerator(0, max);
	}
	
	public int randomGenerator(int min, int max){
		Random rndGen = new Random();		
		return rndGen.nextInt(max) + min;
		
	}
	
	public void crossover(Representation postFixFirst, Representation postFixSecond){
		Representation first = new Representation();
		first.setEquatioin(postFixFirst.getEquatioin());		
		Representation second = new Representation();
		second.setEquatioin(postFixSecond.getEquatioin());
		newPostFixRepresentation.add(first);
		newPostFixRepresentation.add(second);
		Crossover crossover = new Crossover();
		crossover.onePointCrossover(first, second);
	}
	
	public void doSelectioin(SelectionType type, int sizePopulation){
		Selection selection = new Selection();
		logger.info("selection in eq " + postFixRepresentation.size() + " newPopulat " + newPostFixRepresentation.size());
		if(type.equals(SelectionType.Rulette)){
			postFixRepresentation = selection.rulete(postFixRepresentation, newPostFixRepresentation,sizePopulation);			
		}else {
			postFixRepresentation = selection.turnament(postFixRepresentation, newPostFixRepresentation,sizePopulation);
		}
		newPostFixRepresentation.clear();
		for (int i=0; i<postFixRepresentation.size();i++){
			newPostFixRepresentation.add(postFixRepresentation.get(i));
		}
		logger.info("selection out eq" + postFixRepresentation.size() + " newPop " + newPostFixRepresentation.size());
	}
}
