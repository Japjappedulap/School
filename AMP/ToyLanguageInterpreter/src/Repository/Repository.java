package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository{
    private ProgramState programState;
    private String logFilePath;

    public Repository(ProgramState programState) {
        this.programState = programState;
        this.logFilePath = "";
    }

    public Repository(ProgramState programState, String logFilePath) {
        this.programState = programState;
        this.logFilePath = logFilePath;
    }
    @Override
    public ProgramState getCurrentProgram() {
        return this.programState;
    }

    @Override
    public void LogProgramStateExecution(ProgramState programState) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(programState.toString());
        logFile.close();
    }

    @Override
    public void setCurrentProgram(ProgramState programState) {
        this.programState = programState;
    }
}
