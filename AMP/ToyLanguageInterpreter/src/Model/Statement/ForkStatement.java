package Model.Statement;

import Model.DataStructure.MyStack;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.ProgramState;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws ToyLanguageException, DataStructureEmpty {
        return new ProgramState(
                this.statement,
                new MyStack<IStatement>(),
                current_state.getSymbolTable().clone_dict(),
                current_state.getOutputList(),
                current_state.getFileTable(),
                current_state.getHeapTable(),
                current_state.getThreadID() * 10);
    }

    @Override
    public String toString() {
        return "fork(" + this.statement + ")";
    }

}
