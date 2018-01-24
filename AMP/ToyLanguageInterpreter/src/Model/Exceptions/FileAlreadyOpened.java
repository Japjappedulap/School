package Model.Exceptions;

public class FileAlreadyOpened extends ToyLanguageException{
    public FileAlreadyOpened(String s) {
        super("File already opened at: " + s);
    }
}
