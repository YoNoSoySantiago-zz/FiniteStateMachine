package model;

import java.util.ArrayList;

import model.MealyMachine.State;

public class MooreMachine {
	
	private String firstState;
	private ArrayList<State> allStates;
	
	public MooreMachine(String first) {
		firstState = first;
	}
	
	/**
	 * <h2>addState
	 * <p>This method adds a new state to the state machine.
	 * @param name : is the name or identifier to be assigned to the state.
	 * @param output : is the output or result of the state.
	 */
	public void addState(String name, String output) {
		
	}
	
	/**
	 * <h2>addTransition
	 * <p> this method adds a new transition between two states.
	 * @param name : is a symbol of the alphabet included in this Moore machine.
	 * @param source : is the source state in the transition.
	 * @param target :  is the target state in the transition.
	 * @param value : is the symbol of the transition.
	 */
	public void addTransation(String source, String target, String value) {
		
	}
	
	/**
	 *<h2>generateConnectedGraph
	 *<p>this method converts the automaton into an equivalent related automaton, 
	 *deleting all the states that are not accessible from the initial state.
	 */
	private void generateConnectedGraph() {
		
	}
	
	/**
	 * <h2>genereteFirstPartition
	 * <p>This method generates an initial partition of all states. 
	 * Grouping the states that produce identical outputs for each input symbol.
	 * @return return a  matrix where each row is a group of states's name.
	 */
	private  String[][] generateFirstPartition(){
		return null;
	}
	
	/**
	 * <h2>generateNextPartition
	 * <p>This method generate the next partition using a existing partition,
	 * to generate the new partition this method check the states that are in the same block,
	 * and look for their successors and check if the 's' successor of an state is in the same block of the 's' successor of the other state.
	 * these for all symbol of the Machine, and if is true the states will be in the same group.
	 * repeat this process recursively until the incoming partition is equal to the generated one.
	 * @param currentPartition : is the partition incoming to generated the next partition of this.
	 * @return generate the last and minimum partition possible.
	 */
	private String[][] generateNextPartition(String[][] currentPartition){
		return null;
	}
	
	/**
	 * <h2>renamePartition
	 * <p>using a partition generated, this method assigns a new name to each group of the partition.
	 * and create a Moore machine using the names o states generated and with the transitions of the original states of any state of the group.
	 * @param lastPartition : is the minimum partition generated.
	 * @return this method return a Moore machine with the new states and being the minimum automata equivalent.
	 */
	private MooreMachine renamePartition(String[][] lastPartition) {
		return null;
	}
	
	/**
	 * <h2>generateAutomateEquivalent
	 * <p> this method generate the minimum equivalent automata using the others methods
	 * @return return a Moore machine with the new states and being the minimum automata equivalent.
	 */
	public MooreMachine generateAutomataEquivalent() {
		generateConnectedGraph();
		String[][] partition = generateFirstPartition();
		partition = generateNextPartition(partition);
		return renamePartition(partition);
	}
	
	/**
	 * <h2>findNextState
	 * <p>this method find the destination state of the a transition using the source state and the symbol of the transition
	 * @param source : is the source state
	 * @param Transition : is the symbol of the transition
	 * @return this method return the destination state.
	 */
	private State findNextState(State current, String transation) {
		return null;
	}
	
	/**
	 * <h2>valueOfState
	 * <p>this method find the output of the a state.
	 * @param source : is the source state.
	 * @return this method return the output of the state.
	 */
	private String valueOfState(String state) {
		return null;
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
