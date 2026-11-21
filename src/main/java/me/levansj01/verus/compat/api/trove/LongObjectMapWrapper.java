package me.levansj01.verus.compat.api.trove;

import gnu.trove.map.TLongObjectMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import me.levansj01.verus.util.java.WrappingIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LongObjectMapWrapper<V> implements Map<Long, V> {
    private final TLongObjectMap<V> internal;

    @NotNull
    @Override
    public Collection<V> values() {
        return this.internal.valueCollection();
    }

    public LongObjectMapWrapper(TLongObjectMap<V> internal) {
        this.internal = internal;
    }

    @Nullable
    @Override
    public V putIfAbsent(Long key, V value) {
        return this.internal.putIfAbsent(key, value);
    }

    @Nullable
    @Override
    public V put(Long key, V value) {
        return this.internal.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.internal.remove((Long)key);
    }

    @NotNull
    @Override
    public Set<Long> keySet() {
        return new LongSetWrapper(this.internal.keySet());
    }

    @Override
    public boolean containsKey(Object key) {
        return this.internal.containsKey((Long)key);
    }

    @Override
    public int size() {
        return this.internal.size();
    }

    @Override
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }

    @Override
    public void putAll(@NotNull Map<? extends Long, ? extends V> map) {
        this.internal.putAll(map);
    }

    @Deprecated
    @Override
    public Set<Entry<Long, V>> entrySet() {
        return new LongObjectMapWrapper.EntrySetView();
    }

    @Override
    public void clear() {
        this.internal.clear();
    }

    @Override
    public V get(Object key) {
        return this.internal.get((Long)key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.internal.containsValue(value);
    }

    private class EntrySetView implements Set<Entry<Long, V>> {
        @Override
        public boolean containsAll(@NotNull Collection<?> collection) {
            return collection.stream().allMatch(this::contains);
        }

        @Override
        public void clear() {
            LongObjectMapWrapper.this.internal.clear();
        }

        @NotNull
        @Override
        public Object[] toArray() {
            return this.stream().toArray();
        }

        @Override
        public boolean isEmpty() {
            return LongObjectMapWrapper.this.internal.isEmpty();
        }

        @Override
        public boolean retainAll(@NotNull Collection<?> collection) {
            return this.removeIf(entry -> !collection.contains(entry));
        }

        @NotNull
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(@NotNull T[] array) {
            return (T[])this.stream().toArray();
        }

        @Override
        public boolean add(Entry<Long, V> entry) {
            return !entry.equals(LongObjectMapWrapper.this.put(entry.getKey(), entry.getValue()));
        }

        @NotNull
        @Override
        public Iterator<Entry<Long, V>> iterator() {
            return new WrappingIterator<>(LongObjectMapWrapper.this.keySet().iterator(), key -> new Entry<Long, V>() {
                @Override
                public Long getKey() {
                    return key;
                }

                @Override
                public V setValue(V value) {
                    return LongObjectMapWrapper.this.put(key, value);
                }

                @Override
                public V getValue() {
                    return LongObjectMapWrapper.this.get(key);
                }
            });
        }

        @Override
        public boolean remove(Object obj) {
            if (obj instanceof Entry) {
                Entry<?, ?> entry = (Entry<?, ?>)obj;
                if (entry.getValue().equals(LongObjectMapWrapper.this.get(entry.getKey()))) {
                    this.remove(entry.getKey());
                    return true;
                }
            }

            return false;
        }

        @Override
        public int size() {
            return LongObjectMapWrapper.this.internal.size();
        }

        private EntrySetView() {
        }

        @Override
        public boolean contains(Object obj) {
            if (obj instanceof Entry) {
                Entry<?, ?> entry = (Entry<?, ?>)obj;
                V value = LongObjectMapWrapper.this.internal.get((Long)entry.getKey());
                return value != null ? value.equals(entry.getValue()) : entry.getValue() == null && LongObjectMapWrapper.this.internal.containsValue(entry.getKey());
            } else {
                return false;
            }
        }

        @Override
        public boolean removeAll(@NotNull Collection<?> collection) {
            boolean modified = false;

            for (Object obj : collection) {
                modified |= this.remove(obj);
            }

            return modified;
        }

        @Override
        public boolean addAll(@NotNull Collection<? extends Entry<Long, V>> collection) {
            boolean modified = false;

            for (Entry<Long, V> entry : collection) {
                modified |= this.add(entry);
            }

            return modified;
        }
    }
}
