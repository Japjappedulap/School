package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    void LogProgramStateExecution(ProgramState programState) throws IOException;

    void LogProgramStatesExecution(ArrayList<ProgramState> programStateList);

    ArrayList<ProgramState> getProgramStateList();

    void setProgramStateList(ArrayList<ProgramState> programStateList);

    ProgramState getProgramStateWithId(int currentId);

    public void addProgramState(ProgramState programState);
}
