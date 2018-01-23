package Repository;

import Model.ProgramState;

public class Repository implements IRepository{
    private ProgramState programState;

    public Repository() {
        this.programState = new ProgramState();
    }

    public Repository(ProgramState programState) {
        this.programState = programState;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.programState;
    }

    @Override
    public void setCurrentProgram(ProgramState programState) {
        this.programState = programState;
    }
}
