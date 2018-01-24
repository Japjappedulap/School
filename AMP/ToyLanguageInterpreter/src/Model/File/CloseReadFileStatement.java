package Model.File;

import Model.DataStructure.IDictionary;
import Model.Exceptions.*;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements IStatement{
    private Expression variable;

    public CloseReadFileStatement(Expression variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws VariableNotDeclared, DivideByZero, InvalidOperator, FileNotFound, FileClosingException {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, FilePair> fileTable = current_state.getFileTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        Integer unique = this.variable.evaluate(symbolTable, heapTable);

        FilePair pair = fileTable.get(unique);
        if (pair == null) {
            throw new FileNotFound(this.toString());
        }

        BufferedReader bufferedReader = pair.getBufferedReader();

        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new FileClosingException(this.toString());
        }

        fileTable.remove(unique);
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.variable + ")";
    }
}
