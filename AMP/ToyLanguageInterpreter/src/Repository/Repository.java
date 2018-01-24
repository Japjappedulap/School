package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStateList;
    private String logFilePath;

    public Repository(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
        this.logFilePath = "";
    }

    public Repository(List<ProgramState> programStateList, String logFilePath) {
        this.programStateList = programStateList;
        this.logFilePath = logFilePath;
    }

    @Override
    public void LogProgramStateExecution(ProgramState programState) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(programState.toString());
        logFile.close();
    }

    @Override
    public void LogProgramStatesExecution(List<ProgramState> programStateList) {
        programStateList.forEach(state -> {
            PrintWriter logFile;
            try {
                logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
                logFile.write(state.toString());
                logFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<ProgramState> getProgramStateList() {
        return this.programStateList;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }
}
