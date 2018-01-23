package Model.DataStructure;

import java.util.Collection;

public interface IDictionary<K, V> {
    void put(K key, V value);
    V get(K key);
    Collection<V> values();
    boolean containsKey(K key);
    V remove(K key);
    String toString();
}
