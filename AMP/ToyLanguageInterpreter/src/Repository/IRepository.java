package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void LogProgramStateExecution(ProgramState programState) throws IOException;
    void LogProgramStatesExecution(List<ProgramState> programStateList);
    void setProgramStateList(List<ProgramState> programStateList);
    List<ProgramState> getProgramStateList();
}
