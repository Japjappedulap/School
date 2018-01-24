package Model.DataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.max;


public class MyDictionary<K, V> implements IDictionary<K, V> {
    private HashMap<K, V> map;

    // default ctor
    public MyDictionary() {
        this.map = new HashMap<>();
    }

    public MyDictionary(HashMap<K, V> map) {
        this.map = map;
    }

    @Override
    public void put(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public V remove(K key) {
        return this.map.remove(key);
    }

    public String toString() {
        StringBuilder print = new StringBuilder();

        for(HashMap.Entry<K, V> e : map.entrySet())
            print.append(e.getKey().toString()).append(" -> ").append(e.getValue().toString()).append(" ; ");

        print.setLength(max(print.length() - 3, 0));
        return print.toString();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public void remove(int id) {
        this.map.remove(id);
    }

    @Override
    public boolean containsValue(V element) {
        return this.map.containsValue(element);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public void setContent(Set <Map.Entry <K, V> > set) {
        this.map.clear();
        for (Map.Entry<K,V> entry : set) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public K getKey(V value) {
        for(K key : this.map.keySet()) {
            if(this.map.get(key).equals(value))
                return key;
        }
        return null;
    }

    @Override
    public IDictionary<K, V> clone_dict() {
        IDictionary<K, V> clone_map = new MyDictionary<>();
        for(K key : this.keySet())
            clone_map.put(key, this.map.get(key));
        return clone_map;
    }
}
