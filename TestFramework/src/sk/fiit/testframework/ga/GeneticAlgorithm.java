package sk.fiit.testframework.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import sk.fiit.testframework.agenttrainer.AgentMoveReader;
import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.simulation.AlgorithmType;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.ga.testMove.KickMove;
import sk.fiit.testframework.ga.testMove.WalkMove;
import sk.fiit.testframework.gp.GeneticProgramming;
import sk.fiit.testframework.gp.Representation;


public class GeneticAlgorithm {
	private static GeneticAlgorithm instance;
	private ArrayList<AgentMove> aMoves;
	private ArrayList<AgentMove> aNewPopulation;
	private ParameterGA paramGA;
	private Mutation mutation;
	private Crossover crossover;
	private AgentMove inMove;
	private AgentJim agent;
	private EnvironmentSetting envSetting;
	private TestTypeParameter paramTT;
	private Selection selection;
	private static final GeneticProgramming gp = GeneticProgramming.getInstance();
	private static final int MAX_DEPTH = 5;
	private final static File MOVECACHE = new File("e:\\dp-robocup\\Jim\\movecache");
	
	private final static String SAVEEQUTATIONS = "e:\\dp-robocup\\Jim\\equtation\\";
	
	private static final DateFormat DF = new SimpleDateFormat("dd_MM_HH_mm");
	private String child;
	private AlgorithmType algType;
	
	private static Logger logger = Logger.getLogger(GeneticAlgorithm.class.getName());
	
	public static GeneticAlgorithm getInstance(AgentMove agentMove, ParameterGA paramGA, AgentJim agent, EnvironmentSetting eS, TestTypeParameter paramTT, AlgorithmType algType){
		if(instance==null){
			instance = new GeneticAlgorithm(agentMove,paramGA, agent, eS, paramTT, algType);
		}
		return instance;
	}
	
	private GeneticAlgorithm(AgentMove agentMove, ParameterGA paramGA, AgentJim agent, EnvironmentSetting eS, TestTypeParameter paramTT, AlgorithmType algType){
		try {
			
			agentMove.setName(paramGA.getMoveName());
			inMove = cpMove(agentMove);
			
			inMove.setName(agentMove.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		aMoves = new ArrayList<AgentMove>();
		aMoves.add(agentMove);
		
		
		aNewPopulation = new ArrayList<AgentMove>();
		this.paramGA = paramGA;
		this.paramTT = paramTT;
		this.envSetting = eS;
		this.agent = agent;
		this.algType = algType;
		new File(paramGA.getDestination()).mkdir();
		mutation = new Mutation();
		crossover = new Crossover();
		//new Evaluate();
		selection = new Selection();
		writeParam();
		Date date = new Date();
		child = DF.format(date).toString()+".txt";
	}
	
	public void addMove(AgentMove move){
		aMoves.add(move);
	}
	
	public ArrayList<AgentMove> getMove(){
		return aMoves;
	}
	
	private AgentMove cpMove(AgentMove move) throws Exception{
		AgentMove m;		
		
		AgentMoveReader read = new AgentMoveReader();
		m = read.read(move.getDoc());
		int eff = 0;
		for(int i = 0; i<move.getPhases().size(); i++){
			for (int j=0; j<move.getPhases().get(i).getEffectors().size();j++){
				eff = move.getPhases().get(i).getEffectors().get(j).getEnd();
				m.getPhases().get(i).getEffectors().get(j).setEnd(eff);
			}
			int duration = move.getPhases().get(i).getDuration();
			m.getPhases().get(i).setDuration(duration);
			
			
		}
		//cp.setPhases(move.getPhases());
		return m;//(AgentMove) move.clone();
	}
	
	private AgentMove copyMove(AgentMove move) throws Exception{								 
				
		AgentMoveReader read = new AgentMoveReader();
		AgentMove m ;
		
		m = read.read(move.getDoc());
		int eff = 0;
		for(int i = 0; i<move.getPhases().size(); i++){
			for (int j=0; j<move.getPhases().get(i).getEffectors().size();j++){
				eff = move.getPhases().get(i).getEffectors().get(j).getEnd();
				m.getPhases().get(i).getEffectors().get(j).setEnd(eff);
			}
			int duration = move.getPhases().get(i).getDuration();
			m.getPhases().get(i).setDuration(duration);
			
			
		}
		/*for (AgentMovePhase phase : m.getPhases()){
			phase.setName("n"+phase.getName());
		}
		*/
		m.setName("n_"+move.getName());		
		//m.setPhases(m.getPhases());
		Document d = move.getDoc();
		String firsttEL = d.getElementsByTagName("low_skill").item(0).getAttributes().item(0).getTextContent();
		String secEl = d.getElementsByTagName("low_skill").item(0).getAttributes().item(1).getTextContent();
		if(firsttEL.substring(0, 2).compareTo("n_")!=0 ){
			d.getElementsByTagName("low_skill").item(0).getAttributes().item(0).setNodeValue("n_"+firsttEL);
			d.getElementsByTagName("low_skill").item(0).getAttributes().item(1).setNodeValue("n_"+secEl);
		}
		for(int i=0;i<m.getPhases().size();i++){
			for(int j=0;j<d.getElementsByTagName("phase").item(i).getAttributes().getLength();j++){
				String nameP = d.getElementsByTagName("phase").item(i).getAttributes().item(j).getTextContent();
				if(nameP.substring(0, 2).compareTo("n_")!=0 && d.getElementsByTagName("phase").item(i).getAttributes().item(j).getNodeName().compareTo("isFinal")!=0){
					d.getElementsByTagName("phase").item(i).getAttributes().item(j).setNodeValue("n_"+nameP);
					
				}
			}						
			
		}
		for(int i=0;i<d.getElementsByTagName("finalize").getLength();i++){
			String finalize = d.getElementsByTagName("finalize").item(i).getFirstChild().getNodeValue();
			if(finalize.contains(move.getName())){
				if(d.getElementsByTagName("finalize").item(i).getFirstChild().getNodeValue()!=null){
					if(finalize.substring(0, 2).compareTo("n_")!=0){
						d.getElementsByTagName("finalize").item(i).getFirstChild().setNodeValue("n_"+finalize);
					}
				}
			}
		}
		m.setDoc(d);
		
		return m;
		 
	}
	
	
	public void doGA(){				 
		
		if(aMoves.size() == 1){
			
			try {
				logger.info("Init population");
				if(algType.isGeneticAlgorithm()&&algType.isGeneticPrograming()){
					initBoth();
				}else if(algType.isGeneticAlgorithm()){
					initPopulation();
				}else if(algType.isGeneticPrograming()){
					initEqutation();
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
						
		try {
			for(int i=0; i<paramGA.getGeneration()-1;i++){
				System.out.println("generacia >>>>>>>>" + i +" velkost "+paramGA.getGeneration() + "\n");
				logger.info("Generation " + (i+1));
				if(algType.isGeneticAlgorithm()&&algType.isGeneticPrograming()){
					doNextPopBoth();
				}else if(algType.isGeneticAlgorithm()){
					doNextPop();
				}else if(algType.isGeneticPrograming()){
					doNextPopEqutation();
				}
				
			}			
									
		} catch (Exception e) {
			
			logger.info("err>" + e.getMessage());
			
			e.printStackTrace();
		}
		AgentMoveWriter write = new AgentMoveWriter();
		AgentMove bestMove = getFinalMove();
		try {			
			write.write(bestMove, paramGA.getSource());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Genetic algorithm finished with fitness>" + bestMove.getFitness());
	}
	
	public void addToNewPop(AgentMove move){
		aNewPopulation.add(move);
	}
	
	private void initEqutation(){
		int count = paramGA.getPopulation();
		MOVECACHE.delete();	
		while(count > 0){
			logger.info("equation" +count);		
							
			Representation infix = gp.generateEquation(MAX_DEPTH);
			
			doTest(aMoves.get(0),infix);
			gp.addNewEquation(infix.getEquatioin(),infix.getFitness());
//			gp.addToNewRepresentation(infix);			
			writeToFile(infix);			
			count--;

		}
	}
	private void initBoth() throws Exception{
		int count = paramGA.getPopulation();
		
		while(count > 0){
			logger.info("move" +count);
			AgentMove move = null;
			if(aNewPopulation.size()!=0){
				AgentMove cpMove=copyMove(aMoves.get(0));
				//AgentMove cpMove=cpMove(inMove);

				move = mutation.mutateB(cpMove,paramGA);
				cpMove=null;
			}
			else{
				move=copyMove(aMoves.get(0));
			}
			MOVECACHE.delete();
			move.setName("n_"+inMove.getName());						
			Representation infix = gp.generateEquation(MAX_DEPTH);
			
			doTest(move,infix);
			gp.addNewEquation(infix.getEquatioin(),infix.getFitness());
			aNewPopulation.add(move);
//			gp.addToNewRepresentation(infix);
			
			writeToFile(infix);
			
			aMoves.add(move);
			count--;
		}
	}
	
	private void initPopulation() throws Exception{
		int count = paramGA.getPopulation();
		
		while(count > 0){
			logger.info("move" +count);
			AgentMove move = null;
			if(aNewPopulation.size()!=0){
				AgentMove cpMove=copyMove(aMoves.get(0));
				//AgentMove cpMove=cpMove(inMove);

				move = mutation.mutateB(cpMove,paramGA);
				cpMove=null;
			}
			else{
				move=copyMove(aMoves.get(0));
			}
			MOVECACHE.delete();
			move.setName("n_"+inMove.getName());						
//			Representation infix = gp.generateEquation(MAX_DEPTH);
//			gp.addNewEquation(infix.getEquatioin());
			doTest(move,null);													
			aNewPopulation.add(move);
//			gp.addToNewRepresentation(infix);
			
//			writeToFile(infix);
			
			aMoves.add(move);
			count--;
			/*int pos = paramGA.getSource().lastIndexOf("\\");
			String dest=paramGA.getSource().substring(0,pos+1) + "n" + paramGA.getSource().substring(pos+1);
			File m = new File(dest);
			m.delete();*/
		}
		
		
	}

	
	private void doTest(AgentMove move, Representation eq){
		logger.info("do test");
		if(paramTT.getMoveType().compareTo("walk") == 0){
			walkTest(move,eq);
		}
		if(paramTT.getMoveType().compareTo("kick")==0){
			kickTest(move,eq);
		}
		if(paramTT.getMoveType().compareTo("automatic")==0){
			String name=paramGA.getMoveName();
			if(name.contains("kick")){
				kickTest(move,eq);
			}
			if(name.contains("walk")){
				walkTest(move,eq);
			}
		}
			
	}
	private void walkTest(AgentMove move, Representation eq){
//		funkcne
		WalkMove tw = new WalkMove(agent,move,eq,envSetting,paramGA,paramTT,algType);		
		tw.run();				
		
		while(!tw.isFinished()){
			try {												
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }

	}
	private void kickTest(AgentMove move,Representation eq){
		KickMove tm = new KickMove(agent, move, envSetting, paramGA, paramTT);
		tm.run();
		while(!tm.isFinished()){
			try {					
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	//hodnoti x2 pohyby
	private void doNextPop() throws Exception{
		int to = paramGA.getPopulation();//aNewPopulation.size()-1;
		int percentMutation = paramGA.getParameterMutation().getPercentOfMutation();
		double once=percentMutation/100.0;
		int numberMutation = (int) ((once)*((to*2)-2));
		for(int i=0; i < to-1; i++){
			logger.info("move>>>>>>>>"+i);
			AgentMove m1 = copyMove(aNewPopulation.get(i));
			AgentMove m2 = copyMove(aNewPopulation.get(i+1));
			m1.setName(aNewPopulation.get(i).getName());
			m2.setName(aNewPopulation.get(i+1).getName());
			
//			Representation eq1 = gp.getNewPostFixRepresentation().get(i);
//			Representation eq2 = gp.getNewPostFixRepresentation().get(i+1);
			
			MOVECACHE.delete();
			
			int mutate = mutation.randomGenerator(10, 1);
			
			if(numberMutation>0 && mutate>5){
				logger.info("mutujem");
				mutation.mutateB(m1,paramGA);
				mutation.mutateB(m2,paramGA);
				numberMutation--;
			}
			else{
				logger.info("krizim");
				if(paramGA.getParamCross().getType()==CrossoverType.TwoPoint){//crossover.onePointCrossover(m1, m2);					
					crossover.twoPointCrossover(m1, m2);
				}else if(paramGA.getParamCross().getType()==CrossoverType.OnePoint){//crossover.onePointCrossover(m1, m2);
					crossover.onePointCrossover(m1, m2);
				}else{
					crossover.morePointCrossover(m1, m2, paramGA.getParamCross().getNumberOfPointsForCrossover());
				}
				
			}
//			gp.crossover(eq1, eq2);
			doTest(m1,null);
			doTest(m2,null);
//			writeToFile(eq1);
//			writeToFile(eq2);
			aNewPopulation.add(m1);
			aNewPopulation.add(m2);
//			gp.addToNewPostFixRepresentation(eq1.getEquatioin());
//			gp.addToNewPostFixRepresentation(eq2.getEquatioin());
			System.out.println("fitnes m1 " + m1.getFitness()+ "fitnes m2 " + m2.getFitness());
			
			
		}
		if(paramGA.getParamSelection().getType() == SelectionType.Tournament){
			//aMoves = selection.doSelectionB(aMoves,aNewPopulation);
			logger.info("selection in " + aMoves.size() + " newPop " + aNewPopulation.size());
			ArrayList<AgentMove> pomaMove = new ArrayList<>();
			pomaMove.addAll(aMoves);
			ArrayList<AgentMove> pomaNewMove = new ArrayList<>();
			pomaNewMove.addAll(aNewPopulation);
			aMoves = selection.turnament(pomaMove,pomaNewMove, to+1);
			logger.info("selection out " + aMoves.size() + " newPop " + aNewPopulation.size());
//			gp.doSelectioin(SelectionType.Tournament, to+1);
		}else{
			aMoves = selection.rulete(aMoves, aNewPopulation, to+1);
//			gp.doSelectioin(SelectionType.Rulette, to+1);
		}
//		aNewPopulation = aMoves;
				aNewPopulation.clear();
		for (int i=0; i<aMoves.size();i++){
			aNewPopulation.add(aMoves.get(i));
		}				
			
	}
	
	private void doNextPopEqutation(){
		
		int to = paramGA.getPopulation();//aNewPopulation.size()-1;
		int percentMutation = paramGA.getParameterMutation().getPercentOfMutation();
		double once=percentMutation/100.0;
		for(int i=0; i < to-1; i++){
			logger.info("eq>>>>>>>>"+i);			
			
			Representation eq1 = new Representation();
			eq1.setEquatioin(gp.getNewPostFixRepresentation().get(i).getEquatioin());
//			Representation eq1 = gp.getNewPostFixRepresentation().get(i);
			
			Representation eq2 = new Representation();
			eq2.setEquatioin(gp.getNewPostFixRepresentation().get(i+1).getEquatioin());
			
			MOVECACHE.delete();
			
			
			gp.crossover(eq1, eq2);
			doTest(aMoves.get(0), eq1);
			doTest(aMoves.get(0), eq2);
			writeToFile(eq1);
			writeToFile(eq2);
		
			gp.addToNewPostFixRepresentation(eq1.getEquatioin(),eq1.getFitness());
			gp.addToNewPostFixRepresentation(eq2.getEquatioin(),eq2.getFitness());
			
			
		}
		if(paramGA.getParamSelection().getType() == SelectionType.Tournament){
			gp.doSelectioin(SelectionType.Tournament, to+1);
		}else{
			gp.doSelectioin(SelectionType.Rulette, to+1);
		}
		
	}
	private void doNextPopBoth() throws Exception{
		
		int to = paramGA.getPopulation();//aNewPopulation.size()-1;
		int percentMutation = paramGA.getParameterMutation().getPercentOfMutation();
		double once=percentMutation/100.0;
		int numberMutation = (int) ((once)*((to*2)-2));
		for(int i=0; i < to-1; i++){
			logger.info("move>>>>>>>>"+i);
			AgentMove m1 = copyMove(aNewPopulation.get(i));
			AgentMove m2 = copyMove(aNewPopulation.get(i+1));
			m1.setName(aNewPopulation.get(i).getName());
			m2.setName(aNewPopulation.get(i+1).getName());
			
			Representation eq1 = gp.getNewPostFixRepresentation().get(i);
			Representation eq2 = gp.getNewPostFixRepresentation().get(i+1);
			
			MOVECACHE.delete();
			
			int mutate = mutation.randomGenerator(10, 1);
			
			if(numberMutation>0 && mutate>5){
				logger.info("mutujem");
				mutation.mutateB(m1,paramGA);
				mutation.mutateB(m2,paramGA);
				numberMutation--;
			}
			else{
				logger.info("krizim");
				if(paramGA.getParamCross().getType()==CrossoverType.TwoPoint){//crossover.onePointCrossover(m1, m2);					
					crossover.twoPointCrossover(m1, m2);
				}else if(paramGA.getParamCross().getType()==CrossoverType.OnePoint){//crossover.onePointCrossover(m1, m2);
					crossover.onePointCrossover(m1, m2);
				}else{
					crossover.morePointCrossover(m1, m2, paramGA.getParamCross().getNumberOfPointsForCrossover());
				}
				
			}
			gp.crossover(eq1, eq2);
			doTest(m1,eq1);
			doTest(m2,eq2);
			writeToFile(eq1);
			writeToFile(eq2);
			aNewPopulation.add(m1);
			aNewPopulation.add(m2);
			gp.addToNewPostFixRepresentation(eq1.getEquatioin(),eq1.getFitness());
			gp.addToNewPostFixRepresentation(eq2.getEquatioin(),eq2.getFitness());
			System.out.println("fitnes m1 " + m1.getFitness()+ "fitnes m2 " + m2.getFitness());
			
			
		}
		if(paramGA.getParamSelection().getType() == SelectionType.Tournament){
			logger.info("selection in " + aMoves.size() + " newPop " + aNewPopulation.size());
			ArrayList<AgentMove> pomaMove = new ArrayList<>();
			pomaMove.addAll(aMoves);
			ArrayList<AgentMove> pomaNewMove = new ArrayList<>();
			pomaNewMove.addAll(aNewPopulation);
			aMoves = selection.turnament(pomaMove,pomaNewMove, to+1);
			logger.info("selection out " + aMoves.size() + " newPop " + aNewPopulation.size());
			gp.doSelectioin(SelectionType.Tournament, to+1);
		}else{
			aMoves = selection.rulete(aMoves, aNewPopulation, to+1);
			gp.doSelectioin(SelectionType.Rulette, to+1);
		}
		aNewPopulation.clear();
		for (int i=0; i<aMoves.size();i++){
			aNewPopulation.add(aMoves.get(i));
		}
		
	}
	
	/**
	 * pohyb s najlepsou fitnes po celom GA
	 * @return
	 */
	private AgentMove getFinalMove(){
		double fitness = 1000.0;
		AgentMove outMove=null;
		for(AgentMove move : aMoves){
			if(move.getFitness()!=null && move.getFitness()<fitness && move.getFitness()>0.0){
				outMove=move;
				fitness=move.getFitness();
			}
		}
		return outMove;
	}
	
	public ArrayList<AgentMove> getNewMove(){
		return aNewPopulation;
	}
	
	private void writeParam(){
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(paramGA.getDestination(),"paramAlgorithm.txt"),true));
		    writer.append("Size of population>"+paramGA.getPopulation());writer.append("\r\n");		    
		    writer.append("Number of population>"+paramGA.getPopulation());writer.append("\r\n");
		    writer.append("Percent of mutation>"+paramGA.getParameterMutation().getPercentOfMutation());writer.append("\r\n");
		    writer.append("Mutation joint about>"+paramGA.getParameterMutation().getRangeMutation());writer.append("\r\n");
		    writer.append("Penalize>"+paramTT.getPenalize());writer.append("\r\n");
		    writer.append("Minimum kick distance>"+paramTT.getMinKickDistance());writer.append("\r\n");
		    writer.append("Max of angle diversioin>"+paramTT.getMaxAngleDiversion());writer.append("\r\n");
		    writer.append("Lenght of walking>"+paramTT.getLenghtWalk());writer.append("\r\n");
		    writer.append("Max time on move>"+paramTT.getMaxTimeOnMove());writer.append("\r\n");
		    writer.append("Number of test on move>"+paramTT.getNumberTestOnMove());writer.append("\r\n");
		    
		   
		   
		} catch (IOException ex) {
		} finally {
		   try {
			   writer.close();
		   } catch (Exception ex) {
			   
		   }
		}
	}
	
	private void writeToFile(Representation infix) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(SAVEEQUTATIONS,child),true));
		    writer.append(infix.getEquatioin().toString());
		    writer.append(";");
		    if(infix.getFitness() == null){
		    	writer.append("0.0");
		    }else{
		    	writer.append(infix.getFitness().toString()+";"+infix.getFall()+";"+infix.getDiversion());
		    	
		    }
		    
		    writer.append(";");
		    writer.append("\r\n");
		} catch (IOException ex) {
		} finally {
		   try {
			   writer.close();
		   } catch (Exception ex) {
			   
		   }
		}
		
	}

}
