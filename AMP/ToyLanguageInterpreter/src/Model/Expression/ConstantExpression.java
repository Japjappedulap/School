package Model.Expression;

import Model.DataStructure.IDictionary;

public class ConstantExpression extends Expression {
    private int constant;

    public ConstantExpression(int constant) {
        this.constant = constant;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) {
        return this.constant;
    }

    @Override
    public String toString() {
        return String.valueOf(this.constant);
    }
}
