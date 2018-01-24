package Model.Statement;

import Model.DataStructure.IStack;
import Model.ProgramState;

public class CompoundStatement implements IStatement{
    private IStatement first, second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState current_state) {
        IStack<IStatement> stack = current_state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

    public String toString() {
        return first.toString() + "; " + second.toString();
    }
}
