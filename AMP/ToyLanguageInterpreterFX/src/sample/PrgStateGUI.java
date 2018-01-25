package sample;


import Controller.Controller;
import Model.DataStructure.IDictionary;
import Model.DataStructure.IList;
import Model.DataStructure.IStack;
import Model.File.FilePair;
import Model.ProgramState;
import Model.Statement.IStatement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class PrgStateGUI implements Initializable
{
    private Controller controller;

    @FXML
    private ListView<String> ExeStackV;

    @FXML
    private TableView<Map.Entry<String, Integer>> SymTableV;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> STVariable;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> STValue;

    @FXML
    private TableView<Map.Entry<Integer, String>> FileTableV;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> FTValue;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> FTVariable;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> HeapTableV;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> HTAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> HTValue;

    @FXML
    private ListView<Integer> outV;

    @FXML
    private ListView<Integer> IdProgramV;

    @FXML
    private Button executeButton;

    @FXML
    private ListView<Integer> currentId;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.controller = GUIController.controller;

        HTAddress.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        HTValue.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        FTVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        FTValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));

        STVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        STValue.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        ProgramState ps = this.controller.getRepository().getProgramStateList().get(0);
        updateAll(ps);

    }
    private void updateAll(ProgramState ps) {
        if(ps == null)
            return;
        update_Stack(ps);
        update_SymTable(ps);
        update_file(ps);
        update_heap(ps);
        update_out(ps);
        update_ids();
        update_current_id(ps);
    }
    public void executeOneStep()
    {
        try {
            controller.executeOneStep();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        int sz = controller.getRepository().getProgramStateList().size();
        for (ProgramState ps : controller.getRepository().getProgramStateList()) {
            System.out.println(ps);
        }
        if ( sz == 0 )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ProgramState ps= controller.getRepository().getProgramStateList().get(sz-1);
        updateAll(ps);
    }


    public void update_current_id(ProgramState prg){
        List<Integer> list = new ArrayList<>();
        list.add(prg.getThreadID());
        currentId.setItems(FXCollections.observableList(list));
    }
    public void update_ids(){
        List<Integer> list =new ArrayList<>();
        List<ProgramState> states = controller.getRepository().getProgramStateList();
        for(ProgramState pr : states)
            list.add(pr.getThreadID());
        IdProgramV.setItems(FXCollections.observableList(list));
    }
    public void update_Stack(ProgramState current){
        IStack<IStatement> my_stack = current.getExecutionStack();
        List<String> execution=new ArrayList<>();

        for(IStatement s:my_stack.getAll()){
            execution.add(0,s.toString());
        }
        ExeStackV.setItems(FXCollections.observableList(execution));
    }

    public void update_SymTable(ProgramState prg){
        IDictionary<String, Integer> symbolTable = prg.getSymbolTable();
        List<Map.Entry<String,Integer>> list = new ArrayList<>();
        for(Map.Entry<String,Integer> entry: symbolTable.getAll())
        {
            list.add(entry);
        }
        ObservableList<Map.Entry<String, Integer>> items=FXCollections.observableList(list);
        SymTableV.setItems(items);
        SymTableV.refresh();

    }

    public void update_heap(ProgramState prg){
        IDictionary<Integer,Integer> heapTable = prg.getHeapTable();
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>();
        for(Map.Entry<Integer,Integer>entry:heapTable.getAll()){
            list.add(entry);
        }
        HeapTableV.setItems(FXCollections.observableList(list));
        HeapTableV.refresh();
    }

    public void update_file(ProgramState prg){
        IDictionary<Integer, FilePair> fileT = prg.getFileTable();
        Map<Integer,String> list = new HashMap<>();
        for(Map.Entry<Integer, FilePair> entry: fileT.getAll())
        {
            list.put(entry.getKey(),entry.getValue().getFileName());
        }
        List<Map.Entry<Integer,String>> list2 = new ArrayList<>(list.entrySet());
        FileTableV.setItems(FXCollections.observableList(list2));
        FileTableV.refresh();
    }

    public void update_out(ProgramState prg){
        IList<Integer> out = prg.getOutputList();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(Integer e: out.getAll()) {
            list.add(e);
        }
        outV.setItems(FXCollections.observableList(list));
        outV.refresh();
    }
}