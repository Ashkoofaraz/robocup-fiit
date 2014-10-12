package sk.fiit.testframework.ga.parameters;

public class ParameterGA {
	
	private int population;
	private int generation;
	private String source;
	private String nameMove;
	private String destination;	
	private ParameterMutation paramMut;
	private ParameterCrossover paramCross;
	private ParameterSelection paramSelection;
	
	
	public int getPopulation(){
		return population;
	}
	public int getGeneration(){
		return generation;
	}
	public String getSource(){
		return source;
	}
	public String getMoveName(){
		return nameMove;
	}
	public String getDestination(){
		return destination;
	}	
	
	public ParameterMutation getParameterMutation(){
		return paramMut;
	}
	
	public ParameterCrossover getParamCross() {
		return paramCross;
	}
	public ParameterSelection getParamSelection(){
		return paramSelection;
	}
	
	public void setPopulation(int population){
		this.population = population;
	}
	public void setGeneration(int generation){
		this.generation = generation;
	}
	public void setSource(String src){
		this.source = src;
	}
	public void setMoveName(String moveName){
		this.nameMove = moveName;
	}
	public void setDestination(String dest){
		this.destination = dest;
	}
	
	public void setParamMut(ParameterMutation paramMut){
		this.paramMut = paramMut;
	}
	public void setParamCross(ParameterCrossover paramCross) {
		this.paramCross = paramCross;
	}
	public void setParamSel(ParameterSelection paramSelection) {
		this.paramSelection = paramSelection;
		
	}
}
