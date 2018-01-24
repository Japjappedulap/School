package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.DataStructure.IStack;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.Expression;
import Model.ProgramState;

public class WhileStatement implements IStatement {
    private Expression condition;
    private IStatement statement;

    public WhileStatement(Expression condition, IStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState current_state) throws ToyLanguageException, DataStructureEmpty {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        Integer condition_value = this.condition.evaluate(symbolTable, heapTable);

        if (!condition_value.equals(0)) {
            IStack<IStatement> executionStack = current_state.getExecutionStack();
            executionStack.push(this);
            current_state.setExecutionStack(executionStack);
            this.statement.execute(current_state);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while (" + this.condition.toString() + ") { " + this.statement.toString() + " }";
    }
}
