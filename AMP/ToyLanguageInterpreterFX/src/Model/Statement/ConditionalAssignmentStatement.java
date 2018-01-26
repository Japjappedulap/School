package Model.Statement;

import Model.DataStructure.IDictionary;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.Expression;
import Model.ProgramState;

public class ConditionalAssignmentStatement implements IStatement {
    private String variable;
    private Expression condition, if_condition_true, if_condition_false;

    public ConditionalAssignmentStatement(String variable, Expression condition, Expression if_condition_true, Expression if_condition_false) {
        this.variable = variable;
        this.condition = condition;
        this.if_condition_true = if_condition_true;
        this.if_condition_false = if_condition_false;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws ToyLanguageException, DataStructureEmpty {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        IStatement new_if_statement = new IfStatement(this.condition,
                new AssignmentStatement(this.variable, this.if_condition_true),
                new AssignmentStatement(this.variable, this.if_condition_false));

        current_state.getExecutionStack().push(new_if_statement);

        return null;
    }

    @Override
    public String toString() {
        return "(" + this.variable + " = (" + this.condition.toString() + ") ? " +
                this.if_condition_true.toString() + " : " + this.if_condition_false + ")";
    }
}
