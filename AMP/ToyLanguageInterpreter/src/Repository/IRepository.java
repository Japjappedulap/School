package Repository;

import Model.ProgramState;

import java.io.IOException;

public interface IRepository {
    ProgramState getCurrentProgram();
    void LogProgramStateExecution(ProgramState programState) throws IOException;
    void setCurrentProgram(ProgramState programState);
}
