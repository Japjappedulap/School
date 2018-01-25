package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;
import Model.Expression.Expression;
import Model.ProgramState;

public class AssignmentStatement implements IStatement {
    private String variable;
    private Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState current_state) {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        try {
            symbolTable.put(variable, expression.evaluate(symbolTable, heapTable));
        } catch (DivideByZero | VariableNotDeclared | InvalidOperator divideByZero) {
            divideByZero.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return variable + " = " + expression.toString();
    }
}
