package me.levansj01.verus.compat.v1_7_R4;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WrappedHashMap<K, V> extends HashMap<K, V> {
    private final Map<K, V> internal;

    public WrappedHashMap(Map<K, V> internal) {
        this.internal = internal;
    }

    @Override
    public void clear() {
        this.internal.clear();
    }

    @Override
    public V remove(Object key) {
        return this.internal.remove(key);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
        return this.internal.computeIfAbsent(key, function);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        this.internal.forEach(consumer);
    }

    @Override
    public V put(K key, V value) {
        return this.internal.put(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return this.internal.replace(key, oldValue, newValue);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.internal.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.internal.entrySet();
    }

    @Override
    public Set<K> keySet() {
        return this.internal.keySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return this.internal.getOrDefault(key, defaultValue);
    }

    @Override
    public Collection<V> values() {
        return this.internal.values();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return this.internal.putIfAbsent(key, value);
    }

    @Override
    public V replace(K key, V value) {
        return this.internal.replace(key, value);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.internal.replaceAll(function);
    }

    @Override
    public V get(Object key) {
        return this.internal.get(key);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
        return this.internal.computeIfPresent(key, function);
    }

    @Override
    public int size() {
        return this.internal.size();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
        return this.internal.merge(key, value, function);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.internal.putAll(map);
    }

    @Override
    public boolean containsKey(Object key) {
        return this.internal.containsKey(key);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.internal.remove(key, value);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
        return this.internal.compute(key, function);
    }

    @Override
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }
}
