package Controller;

import Model.DataStructure.IStack;
import Model.Exceptions.DataStructureEmpty;
import Model.ProgramState;
import Model.Statement.IStatement;
import Repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState executeOneStep(ProgramState current_program_state) throws DataStructureEmpty {
        IStack<IStatement> stack = current_program_state.getExecutionStack();
        if (stack.empty()) throw new DataStructureEmpty("f");
        IStatement current_statement = stack.pop();
        return current_statement.execute(current_program_state);
    }

    public void executeAllSteps() {
        ProgramState state = this.repository.getCurrentProgram();
        while (!state.getExecutionStack().empty()) {
            try {
                System.out.print(state);
                executeOneStep(state);
            } catch (DataStructureEmpty ignore) { }
        }
        System.out.print(state);
    }
}
