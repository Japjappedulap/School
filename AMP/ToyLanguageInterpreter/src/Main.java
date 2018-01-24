import Controller.Controller;
import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;
import Model.DataStructure.MyDictionary;
import Model.DataStructure.MyList;
import Model.DataStructure.MyStack;
import Model.Expression.*;
import Model.File.CloseReadFileStatement;
import Model.File.OpenReadFileStatement;
import Model.File.ReadFileStatement;
import Model.Statement.NewStatement;
import Model.ProgramState;
import Model.Statement.*;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void clearFiles() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("log1.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log2.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log3.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log4.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log5.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log6.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log7.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log8.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log9.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log10.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log11.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log12.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log13.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log14.txt");
            writer.print("");
            writer.close();
            writer = new PrintWriter("log15.txt");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }

    public static void main(String args[]) {
        TestDataStructures();
        TestExpressions();



        clearFiles();

        IStatement exercise1 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(2)),
                new PrintStatement(new VariableExpression("v")));
        List<ProgramState> programState1 = new ArrayList<>();
        programState1.add(new ProgramState(exercise1));
        IRepository repository1 = new Repository(programState1, "log1.txt");
        Controller controller1 = new Controller(repository1);

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


        List<ProgramState> programState2 = new ArrayList<>();
        programState2.add(new ProgramState(exercise2));
        IRepository repository2 = new Repository(programState2, "log2.txt");
        Controller controller2 = new Controller(repository2);

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

        List<ProgramState> programState3 = new ArrayList<>();
        programState3.add(new ProgramState(exercise3));
        IRepository repository3 = new Repository(programState3, "log3.txt");
        Controller controller3 = new Controller(repository3);

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
        List<ProgramState> programState4 = new ArrayList<>();
        programState4.add(new ProgramState(exercise4));
        IRepository repository4 = new Repository(programState4, "log4.txt");
        Controller controller4 = new Controller(repository4);


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

        List<ProgramState> programState5 = new ArrayList<>();
        programState5.add(new ProgramState(exercise5));
        IRepository repository5 = new Repository(programState5, "log5.txt");
        Controller controller5 = new Controller(repository5);

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
        List<ProgramState> programState6 = new ArrayList<>();
        programState6.add(new ProgramState(exercise6));
        IRepository repository6 = new Repository(programState6, "log6.txt");
        Controller controller6 = new Controller(repository6);

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
        List<ProgramState> programState7 = new ArrayList<>();
        programState7.add(new ProgramState(exercise7));
        IRepository repository7 = new Repository(programState7, "log7.txt");
        Controller controller7 = new Controller(repository7);

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
        List<ProgramState> programState8 = new ArrayList<>();
        programState8.add(new ProgramState(exercise8));
        IRepository repository8 = new Repository(programState8, "log8.txt");
        Controller controller8 = new Controller(repository8);

        IStatement exercise9 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstantExpression(20)),
                                new CompoundStatement(new WriteStatement("a", new ConstantExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression("a")),
                                                        new AssignmentStatement("a", new ConstantExpression(0))))))));
        List<ProgramState> programState9 = new ArrayList<>();
        programState9.add(new ProgramState(exercise9));
        IRepository repository9 = new Repository(programState9, "log9.txt");
        Controller controller9 = new Controller(repository9);

        IStatement exercise10 =
                new PrintStatement(
                        new ArithmeticExpression(
                                new ConstantExpression(10),
                                "+",
                                new BooleanExpression(
                                        new ConstantExpression(2),
                                        "<",
                                        new ConstantExpression(6))));
        List<ProgramState> programState10 = new ArrayList<>();
        programState10.add(new ProgramState(exercise10));
        IRepository repository10 = new Repository(programState10, "log10.txt");
        Controller controller10 = new Controller(repository10);


        IStatement exercise11 =
                new PrintStatement(
                        new BooleanExpression(
                                new ArithmeticExpression(
                                        new ConstantExpression(10),
                                        "+",
                                        new ConstantExpression(2)),
                                "<",
                                new ConstantExpression(6)));
        List<ProgramState> programState11 = new ArrayList<>();
        programState11.add(new ProgramState(exercise11));
        IRepository repository11 = new Repository(programState11, "log11.txt");
        Controller controller11 = new Controller(repository11);

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
        List<ProgramState> programState12 = new ArrayList<>();
        programState12.add(new ProgramState(exercise12));
        IRepository repository12 = new Repository(programState12, "log12.txt");
        Controller controller12 = new Controller(repository12);

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
        List<ProgramState> programState13 = new ArrayList<>();
        programState13.add(new ProgramState(exercise13));
        IRepository repository13 = new Repository(programState13, "log13.txt");
        Controller controller13 = new Controller(repository13);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", exercise1.toString(), controller1));
        menu.addCommand(new RunCommand("2", exercise2.toString(), controller2));
        menu.addCommand(new RunCommand("3", exercise3.toString(), controller3));
        menu.addCommand(new RunCommand("4", exercise4.toString(), controller4));
        menu.addCommand(new RunCommand("5", exercise5.toString(), controller5));
        menu.addCommand(new RunCommand("6", exercise6.toString(), controller6));
        menu.addCommand(new RunCommand("7", exercise7.toString(), controller7));
        menu.addCommand(new RunCommand("8", exercise8.toString(), controller8));
        menu.addCommand(new RunCommand("9", exercise9.toString(), controller9));
        menu.addCommand(new RunCommand("10", exercise10.toString(), controller10));
        menu.addCommand(new RunCommand("11", exercise11.toString(), controller11));
        menu.addCommand(new RunCommand("12", exercise12.toString(), controller12));
        menu.addCommand(new RunCommand("13", exercise13.toString(), controller13));
        menu.show();
    }

    private static void TestExpressions() {
        Expression exp;
        try {
            exp = new ArithmeticExpression(new ConstantExpression(2), "+", new ConstantExpression(3));
            assert (exp.evaluate(new MyDictionary<>(), new MyDictionary<>()) == 5);
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared divideByZero) {
            divideByZero.printStackTrace();
        }

        try {
            exp = new ConstantExpression(7);
            assert (exp.evaluate(new MyDictionary<>(), new MyDictionary<>()) == 7);
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared divideByZero) {
            divideByZero.printStackTrace();
        }

        try {
            exp = new VariableExpression("a");
            MyDictionary<String, Integer> symbolTable = new MyDictionary<>();
            symbolTable.put("b", 123);
            System.out.println(exp.evaluate(symbolTable, new MyDictionary<>()));
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared ignored) {

        }
    }

    private static void TestDataStructures() {
        MyStack<Integer> stk = new MyStack<>();
        stk.push(1);
        stk.push(2);
        stk.push(3);
        stk.push(4);
        assert(stk.peek() == 4);

        MyList<Integer> list = new MyList<>();
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(3);
        list.add(1);
        System.out.println(list);

        MyDictionary<Integer, Integer> dict = new MyDictionary<>();
        dict.put(1, 1);
        dict.put(4, 4);
        dict.put(3, 7);
        dict.put(3, 0);
        dict.put(5, 3);
        System.out.println(dict);
        System.out.println(dict.values());
        System.out.println(dict.containsKey(5));
        dict.remove(2);
        System.out.println(dict);
    }
}
