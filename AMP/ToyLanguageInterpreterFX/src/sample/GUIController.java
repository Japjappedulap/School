package sample;

import Model.ADT.Dictionary;
import Model.ADT.ExecStack;
import Model.ADT.Heap;
import Model.ADT.MyList;
import Model.Expression.*;
import Model.File.FileData;
import Model.File.FileTable;
import Model.ProgramState;
import Model.Statement.*;
import Controller.Controller;

import Repository.ProgramStateRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GUIController implements Initializable {
    private List<Statement> statements;
    public static Controller controller;
    @FXML
    private ListView<String> mainListView;
    @FXML
    private Button runButton;

    private void initStatements(){
        Statement s1 = new CompStatement(new AssignStatement("v", new ConstExpression(10)), new PrintStatement(new VarExpression("v")));
        Statement s2 = new CompStatement(
                new AssignStatement("a", new ArithmeticExpression('+',
                        new ConstExpression(2),
                        new ArithmeticExpression('*',
                                new ConstExpression(3),
                                new ConstExpression(5)))),
                new CompStatement(
                        new AssignStatement("b", new ArithmeticExpression('-', new VarExpression("a"), new ConstExpression(1))),
                        new PrintStatement(new VarExpression("b"))));


        Statement a1 = new AssignStatement("i",new ConstExpression(0));
        Statement a2 = new CompStatement(
                new AssignStatement("i", new ArithmeticExpression('+', new VarExpression("i"),new ConstExpression(1))),
                new PrintStatement(new VarExpression("i")));
        Statement s3 = new CompStatement(a1, new WhileStatement(new BooleanExpression("<=", new VarExpression("i"), new ConstExpression(10)), a2));


        Statement open_file1 = new OpenFileStatement("file1", "file1.txt");
        Statement open_file2 = new OpenFileStatement("file2", "file2.txt");
        Statement read_var1 = new OpenFileStatement("file1", "var1");
        Statement read_var2 = new OpenFileStatement("file1", "var2");
        Statement read_var3 = new OpenFileStatement("file2", "var3");
        Statement close_file1 = new CloseFileStatement(new VarExpression("file1"));
        Statement close_file2 = new CloseFileStatement(new VarExpression("file2"));

        Statement s4 = new CompStatement(open_file1, new CompStatement(open_file2, new CompStatement(read_var1, new CompStatement(read_var2, new CompStatement(read_var3, new CompStatement(close_file1, close_file2))))));


        Statement new_statement = new NewStatement("v", new ConstExpression(20)); //new(v,20)
        Statement printStm = new PrintStatement(new VarExpression("v")); //print(v)
        Statement printStm2 = new PrintStatement(new ReadHeapExpression("v")); //;print(rH(v));
        Statement write_heap = new WriteHeapStatement("v", new ConstExpression(30));//wH(v,30);
        //Statement printStm3 = new PrintStatement(new ReadHeap("a")); // print(rH(a))
        //Statement printStm4 = new PrintStatement(new VarExpression("a")); //print(a);
        Statement s5 = new CompStatement(new_statement, new CompStatement(printStm, new CompStatement(printStm2, write_heap)));

        Statement s6 = new CompStatement(new AssignStatement("v",new ConstExpression(100)), new CompStatement(new PrintStatement(new VarExpression("v")),
                new ForkStatement(new CompStatement(new AssignStatement("v", new ConstExpression(7)),new PrintStatement(new VarExpression("v"))))));


        Statement s7 = new PrintStatement(new ArithmeticExpression('/', new ConstExpression(5), new ConstExpression(0)));

        this.statements = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, s5, s6, s7));

    }

    private List<String> getStringRepresentations(){
        return this.statements
                .stream()
                .map(Statement::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.initStatements();
        ObservableList<String> items = FXCollections.observableArrayList(getStringRepresentations());

        mainListView.setItems(items);
        runButton.setOnAction(actionEvent -> {
            int index = mainListView.getSelectionModel().getSelectedIndex();
            if (index < 0 ){
                return;
            }
            ExecStack<Statement> stack = new ExecStack<>();
            stack.push(statements.get(index));
            ProgramState initialProgramState = new ProgramState(new Dictionary<>(), stack,  new MyList<>(), new FileTable<Integer, FileData>(),new Heap<Integer, Integer>(), null);
            ProgramStateRepository repository = new ProgramStateRepository("log" + index + ".txt");
            repository.addProgramState(initialProgramState);
            Controller auxController = new Controller(repository);
            this.controller = auxController;

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prgStateGUI.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Program State GUI");
                stage.setScene(new Scene(root));

                stage.show();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        });
    }


}
