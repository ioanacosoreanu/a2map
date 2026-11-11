package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    // map este acum final
    private final HashMap<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key " + key + " is not defined.");
        }
        return this.map.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key " + key + " is not defined.");
        }
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key " + key + " is not defined.");
        }
        this.map.remove(key);
    }

    @Override
    public Map<K, V> toMap() {
        return Map.copyOf(this.map);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}