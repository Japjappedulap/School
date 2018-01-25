package Model.Exceptions;

public class VariableNotDeclared extends ToyLanguageException {
    public VariableNotDeclared(String s) {
        super("Variable not declared at: " + s);
    }
}
