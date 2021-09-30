package model;

import java.util.ArrayList;

public class MealyMachine {
	
	private String firstState;
	private ArrayList<State> allStates;
	
	public MealyMachine(String first) {
		firstState = first;
		allStates = new ArrayList<State>();
	}
	
	public void addTransation(String name, String target, String output) {
		
	}
	
	private void generateConnectedGraph() {
		
	}
	
	private  String[][] generateFirstPartition(){
		return null;
	}
	
	private String[][] generateNextPartition(String[][] currentPartition){
		return null;
	}
	
	private MealyMachine renamePartition(String[][] lastPartition) {
		return null;
	}
	
	public MealyMachine generateAutomataEquivalent() {
		generateConnectedGraph();
		String[][] partition = generateFirstPartition();
		partition = generateNextPartition(partition);
		return renamePartition(partition);
	}
	
	private State findNextState(State current, String transation) {
		return null;
	}
	
	private String valueOfTransation(State current, String transation) {
		return null;
	}
	
 	class State{
		String name;
		ArrayList<Transation> listTransation;
	}
	class Transation{
		String name;
		String target;
		String output;
	}
}
