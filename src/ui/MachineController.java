package ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Controller;

public class MachineController {
	@FXML
    private AnchorPane mainAnchorPane;
	
	@FXML
	private BorderPane mainBorderPane;
	private Stage stage;
	private Character firstState;
	@FXML
	private TableView<String> equevalenceTable;
	@FXML
	private BorderPane basePane;
	@FXML
	private VBox vboxMoore;
	@FXML
    private BorderPane solutionPane;
	private ArrayList<String> states;
	private int cont;
	private String[][] matrixMoore;
	private String[][] matrixMealy;
	private String[][] matrixMooreFinal;
	private String[][] matrixMealyFinal;
	boolean isMealy;
	GridPane matrix = new GridPane();
	private Controller c;
	/**
	 * MachineController
	 * <b>This method initialize the controller of the GUI
	 * @param S : is stage of the controller
	 * 
	 */
	public MachineController(Stage s) throws IOException {
      stage=s;
      firstState = 'A';
      vboxMoore = new VBox();
      states=new ArrayList<String>();
      cont=0;
      states.add(firstState+"");
      c=new Controller();
		
	}
	public void initialize() {
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				System.out.println("Closing the window!");
			}
		});

	}
	/**
	 * loadSelectionPane
	 * <b>This method load the pane of the selection automata machine
	 * 
	 */
	public void loadSelectionPane(){
		FXMLLoader fxmload = new FXMLLoader(getClass().getResource("SelectionPane.fxml"));
		fxmload.setController(this);
		Parent root;
		try {
			root = fxmload.load();
			basePane.getChildren().clear();
			basePane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loadMorePane
	 * <b>This method load the pane of the moore automata machine
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadMoorePane(){
		FXMLLoader fxmload = new FXMLLoader(getClass().getResource("MoorePane.fxml"));
		fxmload.setController(this);
		Parent root;

		try {
			root = fxmload.load();
			basePane.getChildren().clear();
			basePane.setCenter(root);
			((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(1)).getChildren().get(1)).setValue('A');
			((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(1)).getChildren().get(2)).setValue('A');
			((ComboBox<Integer>) ((HBox) vboxMoore.getChildren().get(1)).getChildren().get(3)).setValue(1);
			((ComboBox<Integer>) ((HBox) vboxMoore.getChildren().get(1)).getChildren().get(3)).getItems().addAll(1,0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  selectMoore
	 * <b>This method confirm the automata that was choose
	 * 
	 */
	@FXML
    void selectMoore(ActionEvent event) {
		loadMoorePane();
		
    }
	
	/**
	 * comboBoxesElection
	 * <b>This method update the information about the new states
	 * 
	 */
    @SuppressWarnings("unchecked")
	void comboBoxesElection() {
    	char antecesor0 = ' ';
		char antecesor1 = ' ';
		int output = -1;
		
		for (int s = 1; s < vboxMoore.getChildren().size(); s++) {
			if (((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(1)).getValue() != null) {
				antecesor0 = ((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(1)).getValue();
			}
			if (((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(2)).getValue() != null) {
				antecesor1 = ((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(2)).getValue();
			}
			if (((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(3)).getValue() != null) {
				output = ((ComboBox<Integer>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(3)).getValue();
			}
			((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(1)).getItems().clear();
			((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(2)).getItems().clear();
			for (int m='A';m<= firstState; m++) {
				((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(1)).getItems().add((char)m);
				((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(2)).getItems().add((char)m);
			}
			if ( antecesor0<=firstState && antecesor0 != ' ') {
				((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(1)).setValue(antecesor0);
			}
			if ( antecesor1<=firstState && antecesor1 != ' ') {
				((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(2)).setValue(antecesor1);
			}
			if (output != -1) {
				((ComboBox<Integer>) ((HBox) vboxMoore.getChildren().get(s)).getChildren().get(3)).setValue(output);
			}
			antecesor0 = ' ';
			antecesor1 = ' ';
			output = -1;
		}
    	
    }
	/**
	 * addStateMoore
	 * <b>This method add a new state in the automata moore
	 * 
	 */
    @FXML
    void addStateMoore(ActionEvent event) {
    	if (firstState < 90) {
			HBox newHBox = new HBox(4);
			Label stateLetter = new Label((char) (firstState + 1) + "");
			char letter=(char) ((char)firstState+1);
			states.add(letter+"");
			firstState ++;
			stateLetter.setPrefWidth(35);
			ComboBox<Character> ceroSuccesor = new ComboBox<>(); 
			ComboBox<Character> oneSuccesor = new ComboBox<>();
			ComboBox<Integer> output = new ComboBox<>();
			ceroSuccesor.setValue(firstState);
			oneSuccesor.setValue(firstState);
			output.getItems().addAll(1,0);
			output.setValue(1);
			stateLetter.setStyle("-fx-font: 27 arial;");
			newHBox.getChildren().addAll(stateLetter,ceroSuccesor,oneSuccesor,output);
			newHBox.setSpacing(20);
			newHBox.setAlignment(Pos.CENTER);
			vboxMoore.getChildren().add(newHBox);
			comboBoxesElection() ;
		}
    }
    /**
   	 * completeMachineMoore
   	 * <b>This method is for validate the equivalent moore machine
   	 * 
   	 */
    @FXML
    void completeMachine(ActionEvent event) {
    	createMatrixMoore();
    	matrixMooreFinal=c.genereteMimimumMooreMachine(matrixMoore);
    	loadSolution();
    }
    /**
   	 * loadMealyPane
   	 * <b>This method create a matrix of a moore automata
   	 * 
   	 */
    @SuppressWarnings("unchecked")
    public void createMatrixMoore() {
    	matrixMoore = new String[vboxMoore.getChildren().size()][((HBox) vboxMoore.getChildren().get(0)).getChildren().size()-1];
    	String cadena="";
    	for (int i = 1; i < vboxMoore.getChildren().size() ; i++) {
    		for (int j = 1; j < ((HBox) vboxMoore.getChildren().get(i)).getChildren().size(); j++) {
    			if(i==1 ) {
    				matrixMoore[0][1]="0";
    				matrixMoore[0][2]="1";
    			}
    			if(j==1) {
    				matrixMoore[i][0]=states.get(cont);
    				cont++;
    			}
    			if(j==3) {
    				cadena=((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(i)).getChildren().get(j)).getValue() +"";
    				matrixMoore[i][j]=cadena;
    			}
    			else {
    				cadena = ((ComboBox<Character>) ((HBox) vboxMoore.getChildren().get(i)).getChildren().get(j)).getValue()+"";
    				matrixMoore[i][j]=cadena;
    			}

    		}

    	}
    }
    /**
	 * loadMealyPane
	 * <b>This method load the pane of the mealy automata machine
	 * 
	 */
    @SuppressWarnings("unchecked")
	public void loadMealyPane(){
		FXMLLoader fxmload = new FXMLLoader(getClass().getResource("MealyPane.fxml"));
		fxmload.setController(this);
		Parent root;
		try {
			root = fxmload.load();
			basePane.getChildren().clear();
			basePane.setCenter(root);
			isMealy=true;

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
	 *  selectMealy
	 * <b>This method confirm the automata that was choose
	 * 
	 */
    @FXML
    void selectMealy(ActionEvent event) {
    	loadMealyPane();
    }
    /**
	 * addStateMealy
	 * <b>This method add a new state in the automata moore
	 * 
	 */
    @FXML
    void addStateMealy(ActionEvent event) {
    	if (firstState < 90) {
			HBox newHBox = new HBox(3);
			Label stateLetter = new Label((char) (firstState + 1) + "");
			char letter=(char) ((char)firstState+1);
			states.add(letter+"");
			firstState ++;
			stateLetter.setPrefWidth(35);
			TextArea ceroOutput = new TextArea ();
			TextArea unoOutput = new TextArea ();
			ceroOutput.setPrefHeight(45);   
			ceroOutput.setPrefWidth(80);
			unoOutput.setPrefHeight(45);   
			unoOutput.setPrefWidth(80);
			stateLetter.setStyle("-fx-font: 27 arial;");
			newHBox.getChildren().addAll(stateLetter);
			newHBox.getChildren().addAll(ceroOutput);
			newHBox.getChildren().addAll(unoOutput);
			newHBox.setSpacing(50);
			newHBox.setAlignment(Pos.CENTER);
			vboxMoore.getChildren().add(newHBox);
		}
    }
    /**
   	 * loadMealyPane
   	 * <b>This method create a matrix of a mealy automata
   	 * 
   	 */
    @SuppressWarnings("unchecked")
	public void createMatrixMealy() {
    	String cadena="";

    	matrixMealy = new String[vboxMoore.getChildren().size()][((HBox) vboxMoore.getChildren().get(0)).getChildren().size()-1];
    	for (int i = 1; i < vboxMoore.getChildren().size() ; i++) {
    		for (int j = 1; j < ((HBox) vboxMoore.getChildren().get(i)).getChildren().size(); j++) {
    			cadena = ((TextArea) ((HBox) vboxMoore.getChildren().get(i)).getChildren().get(j)).getText();
				matrixMealy[i][j]=cadena;
    		}
    	}
    	matrixMealy[0][1]="0";
		matrixMealy[0][2]="1";
		for(int s=1;s<matrixMealy.length;s++) {
			matrixMealy[s][0]=states.get(cont);
			cont++;
		} 
    }
    /**
   	 * completeMachineMealy
   	 * <b>This method is for validate the equivalent mealy machine
   	 * 
   	 */
    @FXML
    void completeMachineMealy(ActionEvent event) {
    	createMatrixMealy();
    	matrixMealyFinal=c.genereteMimimumMealyMachine(matrixMealy);
    	for(int i = 0; i < matrixMealyFinal.length; i++){ 
			for(int j = 0; j < matrixMealyFinal[i].length; j++){ 
				System.out.print(matrixMealyFinal[i][j] + " ");	
			} 
			System.out.println();	
		}
    	loadSolution();
    }
    /**
   	 * loadSolution
   	 * <b>This method load the pane of the solution page
   	 * 
   	 */
    public void loadSolution(){
		FXMLLoader fxmload = new FXMLLoader(getClass().getResource("Solution.fxml"));
		fxmload.setController(this);
		Parent root;
		try {
			root = fxmload.load();
			basePane.getChildren().clear();
			basePane.setCenter(root);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    @FXML
    void toc(ActionEvent event) {
    	if(isMealy) {
    		 loadTable(matrixMealyFinal);
    	}
    	else {
    		loadTable(matrixMooreFinal);
    	}
    }
    /**
   	 * loadSolution
   	 * <b>This method load the pane of the solution page
   	 * @param String [][] an array of strings that have the equivalent information machine
   	 */
    public void loadTable(String [][] m) {
    	solutionPane.setCenter(matrix);
    	for (int i = 0;  i< m.length ; i++) {
    		for (int j = 0; j < m[i].length; j++) {
    			Label label = new Label(m[i][j]);
    			matrix.add(label, i, j);
    			matrix.setHgap(10);
    	    	matrix.setVgap(10);
    		}
    	}
    }
}
