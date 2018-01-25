package Model.DataStructure;

import java.util.List;

public interface IList<T> {
    void add(T to_add);
    List<T> toList();
    String toString();
}
