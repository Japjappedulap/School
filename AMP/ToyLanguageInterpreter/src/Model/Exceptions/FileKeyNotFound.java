package Model.Exceptions;

public class FileKeyNotFound extends ToyLanguageException{
    public FileKeyNotFound(String s) {
        super("This file descriptor does not exist at: " + s);
    }
}
