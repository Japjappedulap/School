package Model;

import Model.DataStructure.*;
import Model.Exceptions.DataStructureEmpty;
import Model.File.FilePair;
import Model.DataStructure.HeapAddressBuilder;
import Model.Statement.IStatement;

public class ProgramState {
    private IStatement initialProgram;
    private IStack<IStatement> executionStack;
    private IDictionary<String, Integer> symbolTable;
    private IList<Integer> outputList;
    private IDictionary<Integer, FilePair> fileTable;
    private IDictionary<Integer, Integer> heapTable;
    private HeapAddressBuilder heapAddressBuilder = new HeapAddressBuilder();

    public ProgramState(IStatement initialProgram) {
        this.initialProgram = initialProgram;
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.outputList = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heapTable = new MyDictionary<>();

        this.executionStack.push(this.initialProgram);
    }

    public ProgramState(IStatement initialProgram,
                        IStack<IStatement> executionStack,
                        IDictionary<String, Integer> symbolTable,
                        IList<Integer> outputList,
                        IDictionary<Integer, FilePair> fileTable,
                        IDictionary<Integer, Integer> heapTable) {
        this.initialProgram = initialProgram;
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heapTable = heapTable;

        this.executionStack.push(this.initialProgram);
    }

    public IDictionary<String, Integer> getSymbolTable() {
        return this.symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Integer> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IStatement getInitialProgram() {
        return initialProgram;
    }

    public void setInitialProgram(IStatement initialProgram) {
        this.initialProgram = initialProgram;
    }

    public IStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public boolean finished() {
        return this.executionStack.empty();
    }

    public IList<Integer> getOutputList() {
        return this.outputList;
    }

    public void setOutputList(IList<Integer> outputList) {
        this.outputList = outputList;
    }

    public void addOut(Integer number) {
        this.outputList.add(number);
    }

    public IDictionary<Integer, FilePair> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<Integer, FilePair> fileTable) {
        this.fileTable = fileTable;
    }

    public IDictionary<Integer, Integer> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(IDictionary<Integer, Integer> heapTable) {
        this.heapTable = heapTable;
    }

    public HeapAddressBuilder getHeapAddressBuilder() {
        return heapAddressBuilder;
    }

    public void setHeapAddressBuilder(HeapAddressBuilder heapAddressBuilder) {
        this.heapAddressBuilder = heapAddressBuilder;
    }

    public Integer getNewAddress() throws DataStructureEmpty {
        return this.heapAddressBuilder.getFreeAddress();
    }

    public String toString() {
        return
                "**   ExecutionStack  = {" + this.executionStack.toString() + "}\n" +
                "**   SymbolTable     = {" + this.symbolTable.toString() + "}\n" +
                "**   OutputList      = {" + this.outputList.toString() + "}\n" +
                "**   FileTable       = {" + this.fileTable.toString() + "}\n" +
                "**   HeapTable       = {" + this.heapTable.toString() + "}\n" +
                "----------------------------------------------------\n";
    }
}
