package Model.File;

import Model.DataStructure.IDictionary;
import Model.Exceptions.*;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private Expression variable;
    private String variable_name;

    public ReadFileStatement(Expression variable, String variable_name) {
        this.variable = variable;
        this.variable_name = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws VariableNotDeclared, DivideByZero, InvalidOperator, FileKeyNotFound, FileReadingException {
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        IDictionary<Integer, FilePair> fileTable = current_state.getFileTable();
        IDictionary<Integer, Integer> heapTable = current_state.getHeapTable();
        Integer unique = this.variable.evaluate(symbolTable, heapTable);

        if (!fileTable.containsKey(unique)) {
            throw new FileKeyNotFound(this.toString());
        }

        FilePair pair = fileTable.get(unique);
        BufferedReader b = pair.getBufferedReader();
        String line;
        try {
            line = b.readLine();
        } catch (IOException e) {
            throw new FileReadingException("cannot read from this file!!\n");
        }

        int value;
        if (line == null) {
            throw new FileReadingException(this.toString());
        } else
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new FileReadingException(this.toString());
            }
        symbolTable.put(variable_name, value);

        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + this.variable + ", " + this.variable_name + ")";
    }
}
