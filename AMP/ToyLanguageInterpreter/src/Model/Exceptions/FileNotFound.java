package Model.Exceptions;

public class FileNotFound extends ToyLanguageException{
    public FileNotFound(String s) {
        super("File not found at: " + s);
    }
}
