package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MealyMachine {
	
	private String firstState;
	private ArrayList<State> allStates;
	
	private ArrayList<String> inputAlphabet;//falta llenarlo
	private ArrayList<String> outputAlphabet;//falta llenarlo
	
	public MealyMachine(String first) {
		firstState = first;
		allStates = new ArrayList<State>();
	}
	
	/**
	 * <h2>addState
	 * <p>This method adds a new state to the state machine.
	 * @param name : is the name or identifier to be assigned to the state.
	 * 
	 */
	public void addState(String name) {
		State newState = new State(name);
		allStates.add(newState);
	}
	/**
	 * <h2>addTransition
	 * <p> this method adds a new transition between two states.
	 * @param name : is a symbol of the alphabet included in this Mealy machine.
	 * @param source : is the source state in the transition.
	 * @param target :  is the target state in the transition.
	 * @param output : is the output of the transition.
	 */
	public void addTransition(String source,String name, String target, String output) {
		State state = findState(source);
		Transition transition = new Transition(name,target,output);
		state.listTransition.add(transition);
	}
	/**
	 *<h2>generateConnectedGraph
	 *<p>this method converts the automaton into an equivalent related automaton, 
	 *deleting all the states that are not accessible from the initial state.
	 */
	private void generateConnectedGraph() {
		Queue<State> queue = new LinkedList<State>();
		ArrayList<State> visited = new ArrayList<>();
		State firstState =  findState(this.firstState);
		queue.add(firstState);
		visited.add(firstState);
		while(!queue.isEmpty()) {
			State currentState = queue.poll();
			ArrayList<Transition> transitions = currentState.listTransition;
			for(Transition transition : transitions) {
				State transitionState = findState(transition.name);
				if(!visited.contains(transitionState)) {
					queue.add(transitionState);
					visited.add(transitionState);
				}
			}
		}
		
		for(State state : allStates) {
			if(!visited.contains(state)) {
				allStates.remove(state);
			}
		}
	}
	
	/**
	 * <h2>genereteFirstPartition
	 * <p>This method generates an initial partition of all states. 
	 * Grouping the states that produce identical outputs for each input symbol.
	 * @return return a  matrix where each row is a group of states's name.
	 */
	private  ArrayList<ArrayList<State>> generateFirstPartition(){
		ArrayList<ArrayList<State>> firstPartition = new ArrayList<ArrayList<State>>();
		ArrayList<State> visited = new ArrayList<>();
		for(String inputSymbol : inputAlphabet) {
			ArrayList<State> group = (ArrayList<State>) allStates.clone();
			
		}
		return firstPartition;
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
	private ArrayList<ArrayList<State>> generateNextPartition(ArrayList<ArrayList<State>> currentPartition){
		ArrayList<ArrayList<State>> nextPartition = new ArrayList<ArrayList<State>>();
		ArrayList<State> visited = new ArrayList<>();
		for(ArrayList<State> currentGroup : currentPartition) {
			for(int i = 0;i<currentGroup.size();i++) {
				State first = currentGroup.get(i);
				if(!visited.contains(first)) {
					ArrayList<State> newGroup = new ArrayList<>();
					for(int j = i;j<currentGroup.size();j++) {
						State second = currentGroup.get(i);
						boolean equals = true;
						if(!visited.contains(second)) {
							for(String inputSymbol : inputAlphabet) {
								State nextFirst = findNextState(first, inputSymbol);
								State nextSecond = findNextState(second, inputSymbol);
								if(!nextFirst.equals(nextSecond)) {
									equals = false;
									break;
								}
							}
							if(equals) {
								visited.add(second);
								newGroup.add(second);
							}
						}
					}
					nextPartition.add(visited);
				}
			}
		}
		boolean contains = true;
		for(int i = 0; i<currentPartition.size() && contains;i++) {
			if(!nextPartition.get(i).containsAll(currentPartition.get(i))) {
				contains = false;
			}
		}
		if(!contains) {
			return generateNextPartition(nextPartition);
		}
		return nextPartition;
	}
	
	/**
	 * <h2>renamePartition
	 * <p>using a partition generated, this method assigns a new name to each group of the partition.
	 * and create a Mealy machine using the names o states generated and with the transitions of the original states of any state of the group.
	 * @param lastPartition : is the minimum partition generated.
	 * @return this method return a Mealy machine with the new states and being the minimum automata equivalent.
	 */
	private MealyMachine renamePartition(ArrayList<ArrayList<State>> lastPartition) {
		MealyMachine minMealyMachine = null;
		for(int i = 0;i<lastPartition.size();i++) {
			String nameCurrentGroup = "Q"+i;
			if(i==0) {
				minMealyMachine = new MealyMachine(nameCurrentGroup);
			}
			State currentState = lastPartition.get(i).get(0);
			minMealyMachine.addState(nameCurrentGroup);
			ArrayList<Transition> listTransition = currentState.listTransition;
			for(Transition transition : listTransition) {
				String nameTargetGroup = null;
				String output = transition.output;
				String name = transition.name;
				for(int j = 0;j<lastPartition.size() && nameTargetGroup==null;j++) {
					if(lastPartition.get(j).contains(findState(transition.target))) {
						nameTargetGroup = "Q"+j;
					}
				}
				minMealyMachine.addTransition(nameCurrentGroup,name,nameTargetGroup,output);
			}
		}
		return minMealyMachine;
	}
	
	/**
	 * <h2>generateAutomateEquivalent
	 * <p> this method generate the minimum equivalent automata using the others methods
	 * @return return a Mealy machine with the new states and being the minimum automata equivalent.
	 */
	public MealyMachine generateAutomataEquivalent() {
		generateConnectedGraph();
		ArrayList<ArrayList<State>> partition = generateFirstPartition();
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
	private State findNextState(State source, String symbolTransition) {
		State result = null;
		for(Transition transition : source.listTransition) {
			if(transition.name.equals(symbolTransition)) {
				result = findState(transition.target);
			}
		}
		return result;
	}
	
	/**
	 * <h2>valueOfTransition
	 * <p>this method find the output of the a transition using the source state and the symbol of the transition
	 * @param source : is the source state
	 * @param Transition : is the symbol of the transition
	 * @return this method return the output of a transition.
	 */
	private String valueOfTransition(State source, String symbolTransition) {
		String result = null;
		for(Transition transition : source.listTransition) {
			if(transition.name.equals(symbolTransition)) {
				result = transition.output;
			}
		}
		return result;
	}
	
	/**
	 * <h2>delateState
	 * <p> this method find and delete a state.
	 * @param state : is the name of the transition to be deleted.
	 */
	private void delateState(String nameState) {
		State state = findState(nameState);
		allStates.remove(state);
	}
	
	/**
	 * <h2>findState
	 * <p>this method is use to find the state using his name.
	 * @param nameState : is the name of the state.
	 * @return the state sought.
	 */
	private State findState(String nameState) {
		State returnState = null;
		for(State state : allStates) {
			if(state.name.equals(nameState)) {
				returnState = state;
				break;
			}
		}
		return returnState;
	}
	
 	class State{
		String name;
		ArrayList<Transition> listTransition;
		public State(String name) {
			this.name = name;
			listTransition = new ArrayList<>();
		}
	}
 	
	class Transition{
		String name;
		String target;
		String output;
		public Transition(String name,String target, String output) {
			this.name = name;
			this.target = target;
			this.output = output;
		}
	}
}
