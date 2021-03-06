package Model.Expression;

import Model.DataStructure.IDictionary;
import Model.Exceptions.VariableNotDeclared;

public class VariableExpression extends Expression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) throws VariableNotDeclared {
        if (!symbolTable.containsKey(variable))
            throw new VariableNotDeclared(this.toString());
        return symbolTable.get(this.variable);
    }

    @Override
    public String toString() {
        return this.variable;
    }
}
