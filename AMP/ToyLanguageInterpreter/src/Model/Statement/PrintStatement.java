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
            out.add(expression.evaluate(current_state.getSymbolTable()));
        } catch (DivideByZero | VariableNotDeclared | InvalidOperator divideByZero) {
            divideByZero.printStackTrace();
        }
        return current_state;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
