package model;

import java.util.Iterator;

public class Controller {
	
	 
	public String[][] genereteMimimumMooreMachine(String[][] graphEntry){
		MooreMachine mooreMachine = new MooreMachine(graphEntry[1][0]);
		//First add States
		for(int i = 1;i<graphEntry.length;i++) {
			mooreMachine.addState(graphEntry[i][0], graphEntry[i][graphEntry[i].length-1]);
		}
		
		//Second add transition
		for(int i = 1;i<graphEntry.length;i++) {
			for(int j=1;j<graphEntry[i].length-1;j++) {
				String source = graphEntry[i][0];
				String target = graphEntry[i][j];
				String value = graphEntry[0][j];
				mooreMachine.addTransition(source, target, value);
			}
		}
		return mooreMachine.generateAutomataEquivalent().generateMatrix();
	}
	
	public String[][] genereteMimimumMealyMachine(String[][] graphEntry){
		MealyMachine mealyMachine = new MealyMachine(graphEntry[1][0]);
		
		//First add States
		for(int i = 1;i<graphEntry.length;i++) {
			mealyMachine.addState(graphEntry[i][0]);
		}
		for(int i = 0;i<graphEntry.length;i++) {
			for(int j =0;j<graphEntry[i].length;j++) {
				System.out.print(graphEntry[i][j] + "	");
			}
			System.out.println();
		}
		//Second add Transition
		for(int i = 1;i<graphEntry.length;i++) {
			for(int j=1;j<graphEntry[i].length;j++) {
				String source = graphEntry[i][0];
				String target = graphEntry[i][j].split(",")[0].toString();
				String value = graphEntry[0][j];
				String output = graphEntry[i][j].split(",")[1].toString();
				mealyMachine.addTransition(source, value, target, output);
			}
		}
		
		MealyMachine otherMealyMachine = mealyMachine.generateAutomataEquivalent();
		return otherMealyMachine.generateMatrix();
	}
}
