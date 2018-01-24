package Model.File;

import Model.DataStructure.IDictionary;
import Model.Exceptions.FileAlreadyOpened;
import Model.Exceptions.FileNotFound;
import Model.ProgramState;
import Model.Statement.IStatement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

public class OpenReadFileStatement implements IStatement {
    private String var_id;
    private String fileName;
    private static Integer unique = 1;

    public OpenReadFileStatement(String var_id, String fileName) {
        this.var_id = var_id;
        this.fileName = fileName;
    }

    @Override
    public ProgramState execute(ProgramState current_state) throws FileNotFound, FileAlreadyOpened {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(this.fileName));
        } catch (FileNotFoundException e) {
            throw new FileNotFound(this.toString());
        }
        FilePair filePair = new FilePair(this.fileName, bufferedReader);
        IDictionary<Integer, FilePair> fileTable = current_state.getFileTable();
        Set<Integer> allFileTableKeys = fileTable.keySet();
        for(Integer i : allFileTableKeys){
            FilePair f = fileTable.get(i);
            if(fileName.equals(f.getFileName()))
            {
                throw new FileAlreadyOpened(this.toString());
            }
        }
        fileTable.put(unique, filePair);
        IDictionary<String, Integer> symbolTable = current_state.getSymbolTable();
        symbolTable.put(var_id, unique);
        unique += 1;

        return null;
    }

    @Override
    public String toString(){
        return "openRFile(" + this.var_id + ", \"" + this.fileName+ "\")";
    }
}
