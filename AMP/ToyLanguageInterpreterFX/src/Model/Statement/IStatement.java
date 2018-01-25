package Model.Statement;

import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.ProgramState;

public interface IStatement {
    String toString();

    ProgramState execute(ProgramState current_state) throws ToyLanguageException, DataStructureEmpty;
}
