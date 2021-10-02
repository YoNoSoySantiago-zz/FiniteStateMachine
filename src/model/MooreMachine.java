package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import model.MealyMachine.State;
import model.MealyMachine.Transition;

public class MooreMachine {
	
	private String firstState;
	private ArrayList<State> allStates;
	private ArrayList<String> inputAlphabet;//falta llenarlo
	private ArrayList<String> outputAlphabet;//falta llenarlo
	
	public MooreMachine(String first) {
		firstState = first;
		allStates = new ArrayList<>();
		inputAlphabet = new ArrayList<>();
		inputAlphabet.add("0");
		inputAlphabet.add("1");
		outputAlphabet = new ArrayList<>();
		outputAlphabet.add("0");
		outputAlphabet.add("1");
	}
	
	/**
	 * <h2>addState
	 * <p>This method adds a new state to the state machine.
	 * @param name : is the name or identifier to be assigned to the state.
	 * @param output : is the output or result of the state.
	 */
	public void addState(String name, String output) {
		State newState = new State(name,output);
		
		allStates.add(newState);
	}
	
	/**
	 * <h2>addTransition
	 * <p> this method adds a new transition between two states.
	 * @param name : is a symbol of the alphabet included in this Moore machine.
	 * @param source : is the source state in the transition.
	 * @param target :  is the target state in the transition.
	 * @param value : is the symbol of the transition.
	 */
	public void addTransition(String source, String target, String value) {
		State state = findState(source);
		Transition transition = new Transition(value,target);
		state.listTransition.add(transition);
	}
	
	/**
	 *<h2>generateConnectedGraph
	 *<p>this method converts the automaton into an equivalent related automaton, 
	 *deleting all the states that are not accessible from the initial state.
	 */
	private void generateConnectedGraph() {
		//using the bfs algorithm visit all states that are connected with the first state
		Queue<State> queue = new LinkedList<State>();
		ArrayList<State> visited = new ArrayList<>();
		State firstState =  findState(this.firstState);
		queue.add(firstState);
		visited.add(firstState);
		while(!queue.isEmpty()) {
			State currentState = queue.poll();
			ArrayList<Transition> transitions = currentState.listTransition;
			for(Transition transition : transitions) {
				State transitionState = findState(transition.target);
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
		for(String symbol : outputAlphabet) {
			ArrayList<State> group = new ArrayList<>();
			for(State state : allStates) {
				if(state.output.equals(symbol)) {
					group.add(state);
				}
			}
			if(!group.isEmpty()) {
				firstPartition.add(group);
			}
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
						State second = currentGroup.get(j);
						boolean equals = true;
						if(!visited.contains(second)) {
							for(String inputSymbol : inputAlphabet) {
								State nextFirst = findNextState(first, inputSymbol);
								State nextSecond = findNextState(second, inputSymbol);
								ArrayList<State> firstGroup = findSuccesor(currentPartition, nextFirst);
								ArrayList<State> secondGroup = findSuccesor(currentPartition, nextSecond);
								if((nextFirst!=null && nextSecond!=null)&&(firstGroup!=secondGroup)) {
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
					if(!newGroup.isEmpty()) {
						nextPartition.add(newGroup);
					}
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
	
	private ArrayList<State> findSuccesor(ArrayList<ArrayList<State>> partition,  State state){
		ArrayList<State> result = null;
		for(ArrayList<State> group : partition) {
			if(group.contains(state)) {
				result = group;
				break;
			}
		}
		return result;
	}
	
	/**
	 * <h2>renamePartition
	 * <p>using a partition generated, this method assigns a new name to each group of the partition.
	 * and create a Moore machine using the names o states generated and with the transitions of the original states of any state of the group.
	 * @param lastPartition : is the minimum partition generated.
	 * @return this method return a Moore machine with the new states and being the minimum automata equivalent.
	 */
	private MooreMachine renamePartition(ArrayList<ArrayList<State>> lastPartition) {
		MooreMachine minMooreMachine = null;
		for(int i = 0;i<lastPartition.size();i++) {
			String nameCurrentGroup = "Q"+i;
			if(i==0) {
				minMooreMachine = new MooreMachine(nameCurrentGroup);
			}
			State currentState = lastPartition.get(i).get(0);
			minMooreMachine.addState(nameCurrentGroup,currentState.output);
			ArrayList<Transition> listTransition = currentState.listTransition;
			for(Transition transition : listTransition) {
				String nameTargetGroup = null;
				String symbol = transition.name;
				
				for(int j = 0;j<lastPartition.size() && nameTargetGroup==null;j++) {
					if(lastPartition.get(j).contains(findState(transition.target))) {
						nameTargetGroup = "Q"+j;
					}
				}
				minMooreMachine.addTransition(nameCurrentGroup,nameTargetGroup,symbol);
			}
		}
		return minMooreMachine;
	}
	
	/**
	 * <h2>generateAutomateEquivalent
	 * <p> this method generate the minimum equivalent automata using the others methods
	 * @return return a Moore machine with the new states and being the minimum automata equivalent.
	 */
	public MooreMachine generateAutomataEquivalent() {
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
	private State findNextState(State current, String symbol) {
		State nextState = null;
		ArrayList<Transition> transitionStates = current.listTransition;
		for(Transition transition : transitionStates ) {
			if(transition.name.equals(symbol)) {
				nextState = findState(transition.target);
				break;
			}
		}
		return nextState;
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
//	/**
//	 * <h2>valueOfState
//	 * <p>this method find the output of the a state.
//	 * @param source : is the source state.
//	 * @return this method return the output of the state.
//	 */
//	private String valueOfState(String state) {
//		return findState(state).output;
//	}
	@Override
	public String toString() {
		String result = "";
//		for(String input : inputAlphabet) {
//			result+=input+"		";
//		}
//		result+="\n";
		for(State state : allStates) {
			result+=state.name + "	";
			ArrayList<Transition> transitions = state.listTransition;
			for(Transition transition : transitions) {
				result+=transition.name+"/"+transition.target+"	";
			}
			result+="\n";
		}
		return result;
	}
	
	public class State{
		String name;
		String output;
		ArrayList<Transition> listTransition;
		public State(String name_,String output_) {
			name = name_;
			output = output_;
			listTransition = new ArrayList<>();
		}
	}
	
	public class Transition{
		String name;
		String target;
		public Transition(String name_,String target_) {
			name = name_;
			target = target_;
		}
	}
}
