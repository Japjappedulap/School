package Model.Expression;

import Model.DataStructure.IDictionary;
import Model.Exceptions.VariableNotDeclared;

public class ReadHeapExpression extends Expression {
    private String variable_name;

    public ReadHeapExpression(String variable_name) {
        this.variable_name = variable_name;
    }

    public String getVariable_name() {
        return variable_name;
    }

    public void setVariable_name(String variable_name) {
        this.variable_name = variable_name;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) throws VariableNotDeclared {
        if (!symbolTable.containsKey(this.variable_name))
            throw new VariableNotDeclared(this.variable_name);
        Integer address = symbolTable.get(this.variable_name);
        if (!heapTable.containsKey(address))
            throw new VariableNotDeclared(this.variable_name);
        return heapTable.get(address);
    }

    @Override
    public String toString() {
        return "readHeap(" + this.variable_name + ")";
    }
}
