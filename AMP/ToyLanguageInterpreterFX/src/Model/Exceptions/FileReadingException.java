package Model.Exceptions;

public class FileReadingException extends ToyLanguageException {
    public FileReadingException(String s) {
        super("Cannot read from file at: " + s);
    }
}
