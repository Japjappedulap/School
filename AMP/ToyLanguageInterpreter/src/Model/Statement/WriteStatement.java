package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.Exceptions.ToyLanguageException;
import Model.Exceptions.VariableNotDeclared;
import Model.Expression.Expression;
import Model.ProgramState;

public class WriteStatement implements IStatement {
    private String variable_name;
    private Expression expression;

    public WriteStatement(String variable_name, Expression expression) {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws ToyLanguageException {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();

        if (!symbolTable.containsKey(this.variable_name))
            throw new VariableNotDeclared(this.toString());
        Integer address = symbolTable.get(this.variable_name);

        if (!heapTable.containsKey(address))
            throw new VariableNotDeclared(this.toString());

        heapTable.put(address, expression.evaluate(symbolTable, heapTable));
        current_state.setHeapTable(heapTable);
        return null;
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.variable_name + ", " + this.expression + ")";
    }
}
