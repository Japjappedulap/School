package Model.Expression;

import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.DataStructure.IDictionary;

public class ConstantExpression extends Expression {
    private int constant;

    public ConstantExpression(int constant) {
        this.constant = constant;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) throws DivideByZero, InvalidOperator {
        return this.constant;
    }

    @Override
    public String toString() {
        return String.valueOf(this.constant);
    }
}
