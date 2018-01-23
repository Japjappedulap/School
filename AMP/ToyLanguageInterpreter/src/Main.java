import Controller.Controller;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;
import Model.DataStructure.MyDictionary;
import Model.DataStructure.MyList;
import Model.DataStructure.MyStack;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ConstantExpression;
import Model.Expression.Expression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;

public class Main {
    public static void main(String args[]) {
        TestDataStructures();
        TestExpressions();


        IStatement exercise1 = new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(2)),
                new PrintStatement(new VariableExpression("v")));
        ProgramState programState1 = new ProgramState(exercise1);
        IRepository repository1 = new Repository(programState1);
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


        ProgramState programState2 = new ProgramState(exercise2);
        IRepository repository2 = new Repository(programState2);
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

        ProgramState programState3 = new ProgramState(exercise3);
        IRepository repository3 = new Repository(programState3);
        Controller controller3 = new Controller(repository3);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", exercise1.toString(), controller1));
        menu.addCommand(new RunCommand("2", exercise2.toString(), controller2));
        menu.addCommand(new RunCommand("3", exercise3.toString(), controller3));

        menu.show();
    }

    private static void TestExpressions() {
        Expression exp;
        try {
            exp = new ArithmeticExpression(new ConstantExpression(2), "+", new ConstantExpression(3));
            System.out.println(exp.evaluate(new MyDictionary<>()));
            System.out.println(exp);
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared divideByZero) {
            divideByZero.printStackTrace();
        }

        try {
            exp = new ConstantExpression(7);
            System.out.println(exp.evaluate(new MyDictionary<>()));
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared divideByZero) {
            divideByZero.printStackTrace();
        }

        try {
            exp = new VariableExpression("a");
            MyDictionary<String, Integer> symbolTable = new MyDictionary<>();
            symbolTable.put("b", 123);
            System.out.println(exp.evaluate(symbolTable));
            System.out.println(exp);
        } catch (DivideByZero | InvalidOperator | VariableNotDeclared ignored) {

        }
    }

    private static void TestDataStructures() {
        MyStack<Integer> stk = new MyStack<>();
        stk.push(1);
        stk.push(2);
        stk.push(3);
        stk.push(4);
        System.out.println(stk.peek());
        System.out.println(stk);
        while (!stk.empty()) {
            try {
                stk.pop();
            } catch (DataStructureEmpty ignored) { }
            System.out.println(stk);
        }

        MyList<Integer> list = new MyList<>();
        System.out.println(list);
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
