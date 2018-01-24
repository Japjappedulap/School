package Model.DataStructure;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    void put(K key, V value);
    V get(K key);
    Collection<V> values();
    boolean containsKey(K key);
    Set<K> keySet();
    V remove(K key);
    String toString();
    int size();
    void remove(int id);
    boolean containsValue(V element);
    Set<Map.Entry<K, V>> entrySet();
    void setContent(Set<Map.Entry<K, V>> set);
    K getKey(V value);
    IDictionary<K, V> clone_dict();
}
