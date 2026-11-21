package me.levansj01.verus.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;

public class CheckLocalQueue<T> {
    private final Supplier<Queue<T>> queueSupplier;
    private final Map<Check, Queue<T>> checkData;

    public static <T> CheckLocalQueue<T> async() {
        return new CheckLocalQueue(new ConcurrentHashMap<>(), ConcurrentLinkedQueue::new);
    }

    public void add(T obj) {
        this.apply(queue -> queue.add(obj));
    }

    public static <T> CheckLocalQueue<T> sync() {
        return new CheckLocalQueue(new HashMap<>(), LinkedList::new);
    }

    public Queue<T> get(Check check) {
        return (Queue<T>)this.checkData.computeIfAbsent(check, key -> (Queue)this.queueSupplier.get());
    }

    public CheckLocalQueue(Map<Check, Queue<T>> checkData, Supplier<Queue<T>> queueSupplier) {
        this.checkData = checkData;
        this.queueSupplier = queueSupplier;
    }

    public void clear() {
        this.apply(Collection::clear);
    }

    public void apply(Consumer<Queue<T>> consumer) {
        this.checkData.values().forEach(consumer);
    }
}
