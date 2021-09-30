package model;

import java.util.ArrayList;

public class MooreMachine {
	
	private String firstState;
	private ArrayList<State> allStates;
	
	public MooreMachine(String first) {
		firstState = first;
	}
	
	public void addState(String name, String output) {
		
	}
	
	public void addTransation(String current, String target, String value) {
		
	}
	
	private void generateConnectedGraph() {
		
	}
	
	private  String[][] generateFirstPartition(){
		return null;
	}
	
	private String[][] generateNextPartition(String[][] currentPartition){
		return null;
	}
	
	private MooreMachine renamePartition(String[][] lastPartition) {
		return null;
	}
	
	public MooreMachine generateAutomataEquivalent() {
		generateConnectedGraph();
		String[][] partition = generateFirstPartition();
		partition = generateNextPartition(partition);
		return renamePartition(partition);
	} 
	public class State{
		String name;
		String output;
		public State(String name_,String output_) {
			name = name_;
			output = output_;
		}
	}
	
	public class Transatation{
		String name;
		String target;
		public Transatation(String name_,String target_) {
			name = name_;
			target = target_;
		}
	}
}
