package Model.Exceptions;

public class DivideByZero extends ToyLanguageException {
    public DivideByZero(String s) {
        super("Divide by zero exception at: " + s);
    }
}
