package Model.Exceptions;

public class FileClosingException extends ToyLanguageException {
    public FileClosingException(String s) {
        super("Cannot close file at: " + s);
    }
}
