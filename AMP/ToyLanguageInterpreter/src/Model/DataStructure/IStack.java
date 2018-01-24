package Model.DataStructure;

import Model.Exceptions.DataStructureEmpty;

public interface IStack<T> {
    T pop() throws DataStructureEmpty;

    T peek();

    void push(T to_push);

    boolean empty();

    String toString();
}
