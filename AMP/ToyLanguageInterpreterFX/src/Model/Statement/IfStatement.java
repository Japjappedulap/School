package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.DataStructure.IStack;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.Expression;
import Model.ProgramState;

public class IfStatement implements IStatement {
    private Expression condition;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression condition, IStatement thenStatement, IStatement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState current_state) {
        IStack<IStatement> stack = current_state.getExecutionStack();

        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        int value = 0;

        try {
            value = condition.evaluate(symbolTable, heapTable);
        } catch (ToyLanguageException exception) {
            exception.printStackTrace();
        }
        if (value != 0) stack.push(this.thenStatement);
        else stack.push(this.elseStatement);
        return null;
    }

    @Override
    public String toString() {
        return "if(" + this.condition.toString() + ") then " +
                thenStatement.toString() + " else " + elseStatement.toString();
    }
}
