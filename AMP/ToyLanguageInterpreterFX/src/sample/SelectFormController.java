package sample;

import Controller.Controller;
import Model.Expression.*;
import Model.File.CloseReadFileStatement;
import Model.File.OpenReadFileStatement;
import Model.File.ReadFileStatement;
import Model.ProgramState;
import Model.Statement.*;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectFormController implements Initializable {
    private List<IStatement> programStatements;
    private RunFormController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    public void setMainWindowController(RunFormController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    private void buildProgramStatements()
    {
        IStatement exercise1 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(2)),
                new PrintStatement(new VariableExpression("v")));

        IStatement exercise2 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "a",
                                new ArithmeticExpression(
                                        new ConstantExpression(2),
                                        "+",
                                        new ArithmeticExpression(
                                                new ConstantExpression(3),
                                                "*",
                                                new ConstantExpression(5)))),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "b",
                                        new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                "+",
                                                new ConstantExpression(1)
                                        )),
                                new PrintStatement(
                                        new VariableExpression("b"))));

        IStatement exercise3 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "a",
                                new ArithmeticExpression(
                                        new ConstantExpression(2),
                                        "-",
                                        new ConstantExpression(2))),
                        new CompoundStatement(
                                new IfStatement(
                                        new VariableExpression("a"),
                                        new AssignmentStatement(
                                                "v",
                                                new ConstantExpression(2)),
                                        new AssignmentStatement(
                                                "v",
                                                new ConstantExpression(3))),
                                new PrintStatement(new VariableExpression("v"))));

        IStatement exercise4 =
                new CompoundStatement(
                        new OpenReadFileStatement(
                                "var_f",
                                "test.in"),
                        new CompoundStatement(
                                new ReadFileStatement(
                                        new VariableExpression("var_f"),
                                        "var_c"),
                                new CompoundStatement(
                                        new PrintStatement(
                                                new VariableExpression("var_c")),
                                        new CompoundStatement(
                                                new IfStatement(
                                                        new VariableExpression("var_c"),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(
                                                                        new VariableExpression("var_f"),
                                                                        "var_c"),
                                                                new PrintStatement(
                                                                        new VariableExpression("var_c"))),
                                                        new PrintStatement(
                                                                new ConstantExpression(0))),
                                                new CloseReadFileStatement(
                                                        new VariableExpression("var_f"))))));

//        openRFile(var_f,"test.in");
//        readFile(var_f+2,var_c);print(var_c);
//        (if var_c then readFile(var_f,var_c);print(var_c)
//else print(0));
//        closeRFile(var_f)
        IStatement exercis5 =
                new CompoundStatement(
                        new OpenReadFileStatement(
                                "var_f",
                                "test.in"),
                        new CompoundStatement(
                                new ReadFileStatement(
                                        new ArithmeticExpression(
                                                new VariableExpression("var_f"),
                                                "+",
                                                new ConstantExpression(2)),
                                        "var_c"),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("var_c")),
                                        new CompoundStatement(
                                                new IfStatement(
                                                        new VariableExpression("var_c"),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(
                                                                        new VariableExpression("var_f"),
                                                                        "var_c"),
                                                                new PrintStatement(
                                                                        new VariableExpression("var_c"))),
                                                        new PrintStatement(
                                                                new ConstantExpression(0))),
                                                new CloseReadFileStatement(
                                                        new VariableExpression("var_f"))))));
        IStatement exercise5 =
                new CompoundStatement(
                        new OpenReadFileStatement(
                                "var_f",
                                "test.in"),
                        new CompoundStatement(
                                new ReadFileStatement(
                                        new ArithmeticExpression(
                                                new VariableExpression("var_f"),
                                                "+",
                                                new ConstantExpression(2)),
                                        "var_c"),
                                new PrintStatement(new VariableExpression("var_c"))));

        IStatement exercise6 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(10)),
                        new CompoundStatement(
                                new NewStatement(
                                        "v",
                                        new ConstantExpression(20)),
                                new CompoundStatement(
                                        new NewStatement(
                                                "a",
                                                new ConstantExpression(22)),
                                        new PrintStatement(
                                                new VariableExpression("v")))));

        IStatement exercise7 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(10)),
                        new CompoundStatement(
                                new NewStatement(
                                        "v",
                                        new ConstantExpression(20)),
                                new CompoundStatement(
                                        new NewStatement(
                                                "a",
                                                new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ConstantExpression(100),
                                                                "+",
                                                                new ReadHeapExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ConstantExpression(100),
                                                                "+",
                                                                new ReadHeapExpression("a")))))));

        IStatement exercise8 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(10)),
                        new CompoundStatement(
                                new NewStatement(
                                        "v",
                                        new ConstantExpression(20)),
                                new CompoundStatement(
                                        new NewStatement(
                                                "a",
                                                new ConstantExpression(20)),
                                        new CompoundStatement(
                                                new WriteStatement("a", new ConstantExpression(30)),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                        new PrintStatement(new ReadHeapExpression("a")))))));

        IStatement exercise9 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstantExpression(20)),
                                new CompoundStatement(new WriteStatement("a", new ConstantExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression("a")),
                                                        new AssignmentStatement("a", new ConstantExpression(0))))))));

        IStatement exercise10 =
                new PrintStatement(
                        new ArithmeticExpression(
                                new ConstantExpression(10),
                                "+",
                                new BooleanExpression(
                                        new ConstantExpression(2),
                                        "<",
                                        new ConstantExpression(6))));

        IStatement exercise11 =
                new PrintStatement(
                        new BooleanExpression(
                                new ArithmeticExpression(
                                        new ConstantExpression(10),
                                        "+",
                                        new ConstantExpression(2)),
                                "<",
                                new ConstantExpression(6)));

        IStatement exercise12 =
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ConstantExpression(6)),
                        new CompoundStatement(
                                new WhileStatement(
                                        new ArithmeticExpression(
                                                new VariableExpression("v"),
                                                "-",
                                                new ConstantExpression(4)),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new VariableExpression("v")),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                "-",
                                                                new ConstantExpression(1)
                                                        )))),
                                new PrintStatement(
                                        new VariableExpression("v"))));

        IStatement exercise13 =
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

        programStatements = new ArrayList<>(Arrays.asList(exercise1, exercise2, exercise3, exercise4, exercis5,
                exercise6, exercise7, exercise8, exercise9, exercise10, exercise11, exercise12, exercise13));
    }

    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();

            if(index < 0)
                return;

            ProgramState initialProgramState = new ProgramState(programStatements.get(index));
            IRepository repository = new Repository("log" + index + ".txt");
            repository.addProgramState(initialProgramState);
            Controller controller = new Controller(repository);

            mainWindowController.setController(controller);
        });
    }
}
