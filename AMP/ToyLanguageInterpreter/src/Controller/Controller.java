package Controller;

import Model.DataStructure.IDictionary;
import Model.DataStructure.IStack;
import Model.Exceptions.DataStructureEmpty;
import Model.Exceptions.ToyLanguageException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    private Set conservativeGarbageCollector(Collection<Integer> symbolTableValues, IDictionary<Integer, Integer> heapTable){
        return heapTable.
                entrySet().
                stream().
                filter(e->symbolTableValues.contains(e.getKey())).collect(Collectors.toSet());
    }

    public IRepository getRepository() {
        return repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState executeOneStep(ProgramState current_program_state) throws DataStructureEmpty, ToyLanguageException {
        IStack<IStatement> stack = current_program_state.getExecutionStack();
        if (stack.empty()) throw new DataStructureEmpty("f");
        IStatement current_statement = stack.pop();
        return current_statement.execute(current_program_state);
    }

    public void executeAllSteps() {
        ProgramState state = this.repository.getCurrentProgram();
        if (state.finished()) {
            System.out.println("Program already finished");
            return;
        }
        try {
            while (!state.getExecutionStack().empty()) {
                this.repository.LogProgramStateExecution(state);
                executeOneStep(state);
                state.getHeapTable().setContent(conservativeGarbageCollector(state.getSymbolTable().values(), state.getHeapTable()));
            }
            System.out.println("Terminated successfully");
        } catch (DataStructureEmpty | IOException | ToyLanguageException e) {
            System.err.println(e.getMessage());
        }

        try {
            this.repository.LogProgramStateExecution(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
