package Controller;

import Model.DataStructure.IDictionary;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.VariableExpression;
import Model.File.CloseReadFileStatement;
import Model.ProgramState;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    private Set conservativeGarbageCollector(Collection<Integer> symbolTableValues, IDictionary<Integer, Integer> heapTable) {
        return heapTable.
                entrySet().
                stream().
                filter(e -> symbolTableValues.contains(e.getKey())).collect(Collectors.toSet());
    }

    private void closeAllFiles(Collection<Integer> fileTable, IDictionary<String, Integer> symbolTable, ProgramState programState) throws ToyLanguageException {
        System.out.println(fileTable);
        List<Map.Entry<String, Integer>> keys = symbolTable.entrySet().stream().filter(e -> fileTable.contains(e.getValue())).collect(Collectors.toList());

        for (Map.Entry<String, Integer> e : keys) {
            if (programState.getFileTable().containsKey(e.getValue()))
                new CloseReadFileStatement(new VariableExpression(e.getKey())).execute(programState);
        }
    }

    public IRepository getRepository() {
        return repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStateList());

        if(programStates.size() > 0)
        {
            try {
                executeOneStepForAll((ArrayList<ProgramState>)programStates);
            } catch (ToyLanguageException | InterruptedException e) {
                System.out.println();
            }
            executor.shutdownNow();
        }
        this.repository.setProgramStateList((ArrayList<ProgramState>) programStates);
    }


    public void executeOneStepForAll(ArrayList<ProgramState> current_program_states) throws ToyLanguageException, InterruptedException {
        this.repository.LogProgramStatesExecution(current_program_states);

        List<Callable<ProgramState>> callList =
                current_program_states.
                        stream().
                        map((ProgramState state) -> (Callable <ProgramState>)(state::executeOneStep)).
                        collect(Collectors.toList());

        List<ProgramState> new_program_states = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new ToyLanguageException(e.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        current_program_states.addAll(new_program_states);
        this.repository.setProgramStateList(current_program_states);
        this.repository.LogProgramStatesExecution(current_program_states);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> list)
    {
        return list.stream()
                .filter(ProgramState -> ProgramState.isNotCompleted())
                .collect(Collectors.toList());
    }
}
