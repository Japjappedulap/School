package Model.Expression;

import Model.DataStructure.IDictionary;
import Model.Exceptions.DivideByZero;
import Model.Exceptions.InvalidOperator;
import Model.Exceptions.VariableNotDeclared;

public abstract class Expression {
    public abstract int evaluate(IDictionary<String, Integer> symbolTable, IDictionary<Integer, Integer> heapTable) throws DivideByZero, InvalidOperator, VariableNotDeclared;

    public abstract String toString();
}
