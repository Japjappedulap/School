package sample;

import Controller.Controller;
import Model.DataStructure.IDictionary;
import Model.DataStructure.IStack;
import Model.File.FilePair;
import Model.ProgramState;
import Model.Statement.IStatement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunFormController implements Initializable {
    private Controller controller;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, String>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> fileIdentifierColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, Integer>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> symbolTableValueColumn;

    @FXML
    private ListView<Integer> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    public void setController(Controller controller){
        this.controller = controller;
        populateProgramStateIdentifiers();
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getThreadID).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepository().getProgramStateList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

        numberOfProgramStatesTextField.setText("" + programStates.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        fileIdentifierColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        fileNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));

        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        programStateListView.setOnMouseClicked(mouseEvent -> { changeProgramState(getCurrentProgramState()); });

        executeOneStepButton.setOnAction(actionEvent -> { executeOneStep(); });
    }

    private void executeOneStep() {
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean programStateLeft = getCurrentProgramState().getExecutionStack().empty();
        if(programStateLeft){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        controller.executeOneStep();

        changeProgramState(getCurrentProgramState());
        populateProgramStateIdentifiers();


    }

    private void changeProgramState(ProgramState currentProgramState) {
        if(currentProgramState == null)
            return;
        populateExecutionStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutput(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeapTable(currentProgramState);
    }

    private void populateHeapTable(ProgramState currentProgramState) {
        IDictionary<Integer, Integer> heapTable = currentProgramState.getHeapTable();
        
        List<Map.Entry<Integer, Integer>> heapTableList = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : heapTable.getAll())
            heapTableList.add(entry);
        
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState) {
        IDictionary<Integer, FilePair> fileTable = currentProgramState.getFileTable();
        Map<Integer, String> fileTableMap = new HashMap<>();
        for (Map.Entry<Integer, FilePair> entry : fileTable.getAll())
            fileTableMap.put(entry.getKey(), entry.getValue().getFileName());
        
        List<Map.Entry<Integer, String>> fileTableList = new ArrayList<>(fileTableMap.entrySet());
        fileTableView.setItems(FXCollections.observableList(fileTableList));
        fileTableView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        List<Integer> output = currentProgramState.getOutputList().toList();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState) {
        IDictionary<String, Integer> symbolTable = currentProgramState.getSymbolTable();

        List<Map.Entry<String, Integer>> symbolTableList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : symbolTable.getAll())
            symbolTableList.add(entry);
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }

    private void populateExecutionStack(ProgramState currentProgramState) {
        IStack<IStatement> executionStack = currentProgramState.getExecutionStack();

        List<String> executionStackList = new ArrayList<>();
        for(IStatement s : executionStack.getAll()){
            executionStackList.add(s.toString());
        }

        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }

    private ProgramState getCurrentProgramState(){
        if(programStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int currentId = programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStateWithId(currentId);
    }
}
