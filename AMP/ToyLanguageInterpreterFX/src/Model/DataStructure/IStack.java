package Model.DataStructure;

import Model.Exceptions.DataStructureEmpty;

import java.util.List;

public interface IStack<T> {
    T pop() throws DataStructureEmpty;

    T peek();

    void push(T to_push);

    boolean empty();

    String toString();

    List<T> getAll();
}
