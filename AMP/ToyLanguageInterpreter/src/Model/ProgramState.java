package Model;

import Model.DataStructure.*;
import Model.Statement.IStatement;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Integer> symbolTable;
    private IList<Integer> outputList;
    private IStatement initialProgram;

    // default ctor
    public ProgramState() {
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.outputList = new MyList<>();
    }

    public ProgramState(IStatement initialProgram) {
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.outputList = new MyList<>();
        this.initialProgram = initialProgram;

        this.executionStack.push(this.initialProgram);
    }
    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Integer> symbolTable, IList<Integer> outputList) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
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

    public IList<Integer> getOutputList() {
        return this.outputList;
    }

    public void setOutputList(IList<Integer> outputList) {
        this.outputList = outputList;
    }

    public void addOut(Integer number) {
        this.outputList.add(number);
    }

    public String toString() {
        return
                "**   ExectutionStack = {" + executionStack.toString() + "}\n" +
                "**   SymbolTable     = {" + symbolTable.toString() + "}\n" +
                "**   OutputList      = {" +outputList.toString() + "}\n" +
                "-------------------------------\n";
    }
}
