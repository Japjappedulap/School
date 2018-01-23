package Model.Expression;

import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;
import Model.DataStructure.IDictionary;

public class ArithmeticExpression extends Expression {
    private Expression left, right;
    private String operator;

    public ArithmeticExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolTable) throws DivideByZero, InvalidOperator, VariableNotDeclared {
        int left_value = this.left.evaluate(symbolTable);
        int right_value = this.right.evaluate(symbolTable);


        switch (operator) {
            case "+":
                return left_value + right_value;
            case "-":
                return left_value - right_value;
            case "*":
                return left_value * right_value;
            case "/": {
                if (right_value == 0) throw new DivideByZero(this.toString());
                return left_value / right_value;
            }
        }
        throw new InvalidOperator(this.toString());
    }

    @Override
    public String toString() {
        return this.left.toString() + ' ' + this.operator + ' ' + this.right.toString();
    }
}
