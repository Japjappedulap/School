package Repository;

import Model.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgram();
    void setCurrentProgram(ProgramState programState);
}
