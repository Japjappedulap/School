package sample;


import Model.Expression.*;
import Model.File.CloseReadFileStatement;
import Model.File.OpenReadFileStatement;
import Model.File.ReadFileStatement;
import Model.ProgramState;
import Model.Statement.*;
import Repository.IRepository;
import Repository.Repository;
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
    private List<IStatement> statements;
    public static Controller.Controller controller;
    @FXML
    private ListView<String> mainListView;
    @FXML
    private Button runButton;

    private void initStatements(){
        IStatement s1 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(10)), new PrintStatement(new VariableExpression("v")));
        IStatement s2 = new CompoundStatement(
                new AssignmentStatement("a", 
                        new ArithmeticExpression( 
                                new ConstantExpression(2),
                                "+",
                                new ArithmeticExpression(
                                new ConstantExpression(3),
                                "*",
                                new ConstantExpression(5)))),
                new CompoundStatement(
                        new AssignmentStatement("b", new ArithmeticExpression(new VariableExpression("a"), "-", new ConstantExpression(1))),
                        new PrintStatement(new VariableExpression("b"))));


        IStatement a1 = new AssignmentStatement("i",new ConstantExpression(0));
        IStatement a2 = new CompoundStatement(
                new AssignmentStatement("i", new ArithmeticExpression(new VariableExpression("i"), "+", new ConstantExpression(1))),
                new PrintStatement(new VariableExpression("i")));
        IStatement s3 = new CompoundStatement(a1, new WhileStatement(new BooleanExpression(new VariableExpression("i"), "<=", new ConstantExpression(10)), a2));


        IStatement open_file1 = new OpenReadFileStatement("file1", "file1.txt");
        IStatement open_file2 = new OpenReadFileStatement("file2", "file2.txt");
        IStatement read_var1 = new ReadFileStatement(new VariableExpression("file1"), "var1");
        IStatement read_var2 = new ReadFileStatement(new VariableExpression("file1"), "var2");
        IStatement read_var3 = new ReadFileStatement(new VariableExpression("file2"), "var3");
        IStatement close_file1 = new CloseReadFileStatement(new VariableExpression("file1"));
        IStatement close_file2 = new CloseReadFileStatement(new VariableExpression("file2"));

        IStatement s4 = new CompoundStatement(open_file1, new CompoundStatement(open_file2, new CompoundStatement(read_var1, new CompoundStatement(read_var2, new CompoundStatement(read_var3, new CompoundStatement(close_file1, close_file2))))));


        IStatement new_statement = new NewStatement("v", new ConstantExpression(20)); //new(v,20)
        IStatement printStm = new PrintStatement(new VariableExpression("v")); //print(v)
        IStatement printStm2 = new PrintStatement(new ReadHeapExpression("v")); //;print(rH(v));
        IStatement write_heap = new WriteStatement("v", new ConstantExpression(30));//wH(v,30);
        //Statement printStm3 = new PrintStatement(new ReadHeap("a")); // print(rH(a))
        //Statement printStm4 = new PrintStatement(new VariableExpression("a")); //print(a);
        IStatement s5 = new CompoundStatement(new_statement, new CompoundStatement(printStm, new CompoundStatement(printStm2, write_heap)));

        IStatement s6 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(100)),
                        new CompoundStatement(
                                new PrintStatement(
                                        new VariableExpression("v")),

                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new AssignmentStatement(
                                                                "v",
                                                                new ConstantExpression(7)),
                                                        new PrintStatement(
                                                                new VariableExpression("v")))),
                                new CompoundStatement(
                                        new PrintStatement(new ConstantExpression(3)),
                                        new PrintStatement(new ConstantExpression(3))))));


        IStatement s7 = new PrintStatement(new ArithmeticExpression(new ConstantExpression(5), "/", new ConstantExpression(0)));


        IStatement s8 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(10)),
                        new CompoundStatement(
                                new NewStatement(
                                        "a",
                                        new ConstantExpression(22)),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new CompoundStatement(
                                                                new WriteStatement(
                                                                        "a",
                                                                        new ConstantExpression(30)),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement(
                                                                                "v",
                                                                                new ConstantExpression(32)),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableExpression("v")),
                                                                                new PrintStatement(
                                                                                        new ReadHeapExpression(
                                                                                                "a"))))),
                                                        new PrintStatement(
                                                                new ArithmeticExpression(
                                                                        new ConstantExpression(23),
                                                                        "+",
                                                                        new ConstantExpression(0))))),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new VariableExpression("v")),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                "a"))))));
//        a=1;b=2;
//        c=a?100:200;
//        print(c);
//        c= (b-2)?100:200
        IStatement s9 = new CompoundStatement(
                new AssignmentStatement(
                        "a",
                        new ConstantExpression(1)),
                new CompoundStatement(
                        new AssignmentStatement(
                                "b",
                                new ConstantExpression(2)),
                        new CompoundStatement(
                                new ConditionalAssignmentStatement(
                                        "c",
                                        new VariableExpression("a"),
                                        new ConstantExpression(100),
                                        new ConstantExpression(200)),
                                new CompoundStatement(
                                        new PrintStatement(
                                                new VariableExpression("c")),
                                        new CompoundStatement(
                                                new ConditionalAssignmentStatement(
                                                        "c",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("b"),
                                                                "-",
                                                                new ConstantExpression(2)),
                                                        new ConstantExpression(100),
                                                        new ConstantExpression(200)),
                                                new PrintStatement(new VariableExpression("c")))))));

        IStatement x = new ConditionalAssignmentStatement("c",
                new ArithmeticExpression(new ConstantExpression(5), "-", new ConstantExpression(3)),
                new ConstantExpression(1), new ConstantExpression(2));
        System.out.println(x);
        this.statements = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, x));

    }

    private List<String> getStringRepresentations(){
        return this.statements
                .stream()
                .map(IStatement::toString)
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
            ProgramState initialProgramState =
                    new ProgramState(statements.get(index));
            IRepository repository = new Repository("log" + index + ".txt");
            repository.addProgramState(initialProgramState);
            controller = new Controller.Controller(repository);

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
