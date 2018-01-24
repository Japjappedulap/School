package Model.Expression;

import Model.DataStructure.IDictionary;
import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;

public class BooleanExpression extends Expression{
    private Expression left, right;
    private String operator;

    public BooleanExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) throws DivideByZero, InvalidOperator, VariableNotDeclared {
        int left_value = this.left.evaluate(symbolTable, heapTable);
        int right_value = this.right.evaluate(symbolTable, heapTable);

        switch (this.operator) {
            case "<": return left_value < right_value ? 1 : 0;
            case "<=": return left_value <= right_value ? 1 : 0;
            case "==": return left_value == right_value ? 1 : 0;
            case "!=": return left_value != right_value ? 1 : 0;
            case ">=": return left_value >= right_value ? 1 : 0;
            case ">": return left_value > right_value ? 1 : 0;
        }
        throw new InvalidOperator(this.toString());
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + ' ' + this.operator + ' ' + this.right.toString() + ")";
    }
}
