package Model.DataStructure;

import Model.Exceptions.DataStructureEmpty;

import java.util.Stack;

import static java.lang.Integer.max;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    // default ctor
    public MyStack() {
        this.stack = new Stack<>();
    }
    // extra ctor
    public MyStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public T pop() throws DataStructureEmpty {
        if (stack.empty()) throw new DataStructureEmpty(this.getClass().getSimpleName());
        return this.stack.pop();
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    @Override
    public void push(T to_push) {
        this.stack.push(to_push);
    }

    @Override
    public boolean empty() {
        return this.stack.empty();
    }

    @Override
    public String toString(){
        StringBuilder print = new StringBuilder();
        Stack<T> backup = new Stack<>();

        while (!this.stack.empty()) {
            print.append(this.stack.peek()).append(" | ");
            try {
                backup.push(this.pop());
            } catch (DataStructureEmpty ignored) {}

        }
        while (!backup.empty()) {
            this.stack.push(backup.pop());
        }
        print.setLength(max(print.length() - 3, 0));
        return print.toString();
    }
}
