package sample;


import Controller.Controller;
import Model.ADT.IDictionary;
import Model.ADT.IExecStack;
import Model.ADT.IHeap;
import Model.ADT.IList;
import Model.File.FileData;
import Model.File.IFileTable;
import Model.Statement.Statement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Model.*;

import java.net.URL;
import java.util.*;

public class PrgStateGUI implements Initializable
{
    public Controller controller;

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
    private TableColumn<Map.Entry<Integer, Integer>, Integer> HTAdress;

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

        HTAdress.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        HTValue.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        FTVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        FTValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));

        STVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        STValue.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        ProgramState ps = this.controller.getRepo().getPrgStates().get(0);
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
            controller.oneStep();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        controller.removeCompletedPrograms(controller.getRepo().getPrgStates());
        int sz = controller.getRepo().getPrgStates().size();
        if ( sz == 0 )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ProgramState ps= controller.getRepo().getPrgStates().get(sz-1);
        updateAll(ps);
    }


    public void update_current_id(ProgramState prg){
        List<Integer> lista = new ArrayList<>();
        lista.add(prg.getId());
        currentId.setItems(FXCollections.observableList(lista));
    }
    public void update_ids(){
        List<Integer> lista=new ArrayList<>();
        List<ProgramState> states = controller.getRepo().getPrgStates();
        for(ProgramState pr : states)
            lista.add(pr.getId());
        IdProgramV.setItems(FXCollections.observableList(lista));
    }
    public void update_Stack(ProgramState current){
        IExecStack<Statement> mystack = current.getExecStack();
        List<String> execution=new ArrayList<>();

        for(Statement s:mystack.getAll()){
            execution.add(s.toString());
        }
        ExeStackV.setItems(FXCollections.observableList(execution));
    }

    public void update_SymTable(ProgramState prg){
        IDictionary<String,Integer> symtbl = prg.getSymbolTable();
        List<Map.Entry<String,Integer>> lista=new ArrayList<>();
        for(Map.Entry<String,Integer> entry: symtbl.getAll().entrySet())
        {
            lista.add(entry);
        }
        ObservableList<Map.Entry<String, Integer>> items=FXCollections.observableList(lista);
        SymTableV.setItems(items);
        SymTableV.refresh();

    }

    public void update_heap(ProgramState prg){
        IHeap<Integer,Integer> heap=prg.getHeap();
        List<Map.Entry<Integer,Integer>> lista=new ArrayList<>();
        for(Map.Entry<Integer,Integer>entry:heap.getAll().entrySet()){
            lista.add(entry);
        }
        HeapTableV.setItems(FXCollections.observableList(lista));
        HeapTableV.refresh();
    }

    public void update_file(ProgramState prg){
        IFileTable<Integer, FileData> fileT = prg.getFileTable();
        Map<Integer,String> lista = new HashMap<>();
        for(Map.Entry<Integer, FileData> entry: fileT.getAll().entrySet())
        {
            lista.put(entry.getKey(),entry.getValue().getFilename());
        }
        List<Map.Entry<Integer,String>> list2 = new ArrayList<>(lista.entrySet());
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