package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.MealyMachine;
import model.MealyMachine.State;

class MealyMachineTest {
	private MealyMachine mealyMachine;
	
	@Test
	void test1() {
		setUp();
		setUp2();
		
		String[][] matrix = mealyMachine.generateMatrix();
		for(int i =0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[i].length;j++) {
				System.out.print(matrix[i][j]+"	");
			}
			System.out.println();
		}
		MealyMachine otherMachine = mealyMachine.generateAutomataEquivalent();
		
		System.out.println("\n----------------------------------------------\n");
		matrix = otherMachine.generateMatrix();
		for(int i =0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[i].length;j++) {
				System.out.print(matrix[i][j]+"	");
			}
			System.out.println();
		}
	}
	
	void setUp() {
		mealyMachine = new MealyMachine("A");
		mealyMachine.addState("A");
		mealyMachine.addState("B");
		mealyMachine.addState("C");
		mealyMachine.addState("D");
		mealyMachine.addState("E");
		mealyMachine.addState("F");
		
		mealyMachine.addInputSymbol('0');
		mealyMachine.addInputSymbol('1');
	}
	
	void setUp2() {
		mealyMachine.addTransition("A", "1", "B", "1");
		mealyMachine.addTransition("A", "0", "A", "1");
		
		mealyMachine.addTransition("B", "1", "A", "1");
		mealyMachine.addTransition("B", "0", "C", "0");
		
		mealyMachine.addTransition("C", "1", "D", "0");
		mealyMachine.addTransition("C", "0", "D", "1");
		
		mealyMachine.addTransition("D", "1", "B", "0");
		mealyMachine.addTransition("D", "0", "C", "1");
		
		mealyMachine.addTransition("E", "1", "E", "1");
		mealyMachine.addTransition("E", "0", "C", "0");
		
		mealyMachine.addTransition("F", "1", "E", "1");
		mealyMachine.addTransition("F", "0", "A", "1");
	}
}
