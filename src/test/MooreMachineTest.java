package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.MooreMachine;

class MooreMachineTest {
	private MooreMachine mooreMachine;

	@Test
	void test1() {
		setUp();
		setUp2();
		MooreMachine otherMachine = mooreMachine.generateAutomataEquivalent();
//		ArrayList<State> allStates = otherMachine.getAllStates();
//		for(State state : allStates) {
//			System.out.println(state.getName());
//		}
		System.out.println(mooreMachine.toString());
		System.out.println("----------------------------------------------\n");
		System.out.println(otherMachine.toString());
	}
	void setUp() {
		mooreMachine = new MooreMachine("A");
		mooreMachine.addState("A", "0");
		mooreMachine.addState("B", "1");
		mooreMachine.addState("C", "0");
		mooreMachine.addState("D","0");
		mooreMachine.addState("E","0");
		mooreMachine.addState("F","0");
	}
	
	void setUp2() {
		mooreMachine.addTransition("A", "B", "0");
		mooreMachine.addTransition("A", "D", "1");
		
		mooreMachine.addTransition("B", "C", "0");
		mooreMachine.addTransition("B", "E", "1");
		
		mooreMachine.addTransition("C", "B", "0");
		mooreMachine.addTransition("C", "F", "1");
		
		mooreMachine.addTransition("D", "E", "0");
		mooreMachine.addTransition("D", "A", "1");
		
		mooreMachine.addTransition("E", "F", "0");
		mooreMachine.addTransition("E", "B", "1");
		
		mooreMachine.addTransition("F", "E", "0");
		mooreMachine.addTransition("F", "C", "1");
	}
}
