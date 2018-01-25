package Controller;

import Model.DataStructure.IDictionary;
import Model.Exceptions.ToyLanguageException;
import Model.Expression.VariableExpression;
import Model.File.CloseReadFileStatement;
import Model.ProgramState;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        executor = Executors.newFixedThreadPool(8);
        removeCompletedPrograms(repository.getProgramStateList());
        List<ProgramState> programStates = repository.getProgramStateList();
        if(programStates.size() > 0)
        {
            try {
                executeOneStepForAllPrograms(repository.getProgramStateList());
            } catch (ToyLanguageException | InterruptedException e) {
                System.out.println();
            }
            programStates.forEach(i -> {
                try {
                    repository.LogProgramStateExecution(i);
                } catch (IOException e) {
                    System.out.println();
                }
            });
            removeCompletedPrograms(repository.getProgramStateList());
            executor.shutdownNow();
        }
    }

    @SuppressWarnings("all")
    private void executeOneStepForAllPrograms(List<ProgramState> current_program_states) throws ToyLanguageException, InterruptedException {
        this.repository.LogProgramStatesExecution(current_program_states);
//        for (ProgramState state : current_program_states) {
//            System.out.println(state.toString());
//        }

        List<Callable<ProgramState>> callList = current_program_states.stream().filter(p -> !p.getExecutionStack().empty())
                .map((ProgramState program_state) ->

                                (Callable<ProgramState>) (() -> {
                                    try {
                                        return program_state.oneStep();
                                    } catch (ToyLanguageException e) {
                                        System.err.println(e.getMessage());
//                                e.printStackTrace();
                                        return null;
                                    }
                                })
                ).collect(Collectors.toList());


        List<ProgramState> program_states = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("End of program");
                    }
                    return null;
                }).filter(p -> (p != null)).collect(Collectors.toList());

        current_program_states.addAll(program_states);
        current_program_states.forEach(state -> {
            state.getHeapTable().setContent(conservativeGarbageCollector(
                    state.getSymbolTable().values(), state.getHeapTable()));
        });

        this.repository.setProgramStateList(current_program_states);
    }

    public void executeAllSteps() {
        List<ProgramState> states = this.repository.getProgramStateList();
        if (states.isEmpty()) {
            System.out.println("Program already finished");
            return;
        }
        this.executor = Executors.newFixedThreadPool(1);

        states = removeCompletedPrograms(states);
        try {
            while (states.size() > 0) {
                executeOneStepForAllPrograms(states);
                states = removeCompletedPrograms(states);
            }
        } catch (ToyLanguageException | InterruptedException e) {
            System.err.println(e.getMessage());
            return;
        }
        this.repository.LogProgramStatesExecution(states);
        executor.shutdownNow();
        repository.setProgramStateList(states);
//
//        List<ProgramState> states = this.repository.getProgramStateList();
//        if (states.isEmpty()) {
//            System.out.println("Program already finished");
//            return;
//        }
//        try {
//            while (!(states.size() > 0)) {
//                executeOneStepForAllPrograms(states);
//                states = removeCompletedPrograms();
//            }
//            System.out.println("Terminated successfully");
//        } catch (DataStructureEmpty | IOException | ToyLanguageException e) {
//            System.err.println(e.getMessage());
//        }
//
//        for (ProgramState i : this.repository.getProgramStateList()) {
//            try {
//                closeAllFiles(i.getFileTable().keySet(), i.getSymbolTable(), i);
//            } catch (ToyLanguageException e) {
//                System.err.println(e.getMessage());
//            }
//        }
////        closeAllFiles(repository.getCurrentProgram().getFileTable().keySet(), repository.getCurrentProgram().getSymbolTable(), repository.getCurrentProgram());
//        for (ProgramState i : this.repository.getProgramStateList()) {
//            try {
//                this.repository.LogProgramStateExecution(i);
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//        }
////        repository.logPrgStateExec(repository.getCurrentProgram());
//
////        executor.shutdownNow();
//        repository.setProgramStateList(states);
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }
}
