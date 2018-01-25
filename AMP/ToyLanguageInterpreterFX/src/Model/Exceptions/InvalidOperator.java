package Model.Exceptions;

public class InvalidOperator extends ToyLanguageException {
    public InvalidOperator(String s) {
        super("Invalid operator at: " + s);
    }
}
