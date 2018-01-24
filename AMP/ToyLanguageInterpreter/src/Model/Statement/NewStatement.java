package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;

public class NewStatement implements IStatement{
    private String variable_name;
    private Expression expression;

    public NewStatement(String variable_name, Expression expression) {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws ToyLanguageException, DataStructureEmpty {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();

        Integer heapAddress = current_state.getNewAddress();

        heapTable.put(heapAddress, expression.evaluate(symbolTable, heapTable));

        symbolTable.put(this.variable_name, heapAddress);
        current_state.setSymbolTable(symbolTable);
        current_state.setHeapTable(heapTable);
        return current_state;
    }

    @Override
    public String toString() {
        return "new(" + this.variable_name + ", " + this.expression + ")";
    }
}
