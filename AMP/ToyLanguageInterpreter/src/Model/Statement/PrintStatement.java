package Model.Statement;

import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;
import Model.DataStructure.IList;
import Model.Expression.Expression;
import Model.ProgramState;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState current_state) {
        IList<Integer> out = current_state.getOutputList();
        try {
            out.add(expression.evaluate(current_state.getSymbolTable(), current_state.getHeapTable()));
        } catch (DivideByZero | VariableNotDeclared | InvalidOperator e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
