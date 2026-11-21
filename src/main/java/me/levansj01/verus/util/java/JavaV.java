package me.levansj01.verus.util.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import me.levansj01.launcher.VerusLauncher;

public class JavaV {
    public static <T> boolean allMatch(Predicate<T> predicate, Iterable<T> iterable) {
        for (T obj : iterable) {
            if (!predicate.test(obj)) {
                return false;
            }
        }

        return true;
    }

    public static <T> boolean anyMatch(Predicate<T> predicate, Iterable<T> iterable) {
        for (T obj : iterable) {
            if (predicate.test(obj)) {
                return true;
            }
        }

        return false;
    }

    public static void shutdownAndAwaitTermination(ExecutorService executorService, long value, TimeUnit timeUnit) {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(value, timeUnit)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(value, timeUnit)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException exception) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static <T> boolean anyMatch(Predicate<T> predicate, T... ts) {
        for (T obj : ts) {
            if (predicate.test(obj)) {
                return true;
            }
        }

        return false;
    }

    public static void executeSafely(Queue<Runnable> runnables, Supplier<String> supplier) {
        Runnable runnable;
        while ((runnable = (Runnable)runnables.poll()) != null) {
            try {
                runnable.run();
            } catch (Throwable throwable) {
                VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to run " + runnable.getClass().getSimpleName() + (String)supplier.get(), throwable);
            }
        }
    }

    public static <T> List<T> findAll(Predicate<T> predicate, T... ts) {
        LinkedList list = new LinkedList();

        for (T obj : ts) {
            if (predicate.test(obj)) {
                list.add(obj);
            }
        }

        return list;
    }

    public static <T> Stream<T> stream(T... ts) {
        return Arrays.stream((T[])ts);
    }

    public static <T> T firstNonNull(@Nullable T t, @Nullable T t2) {
        return (T)(t != null ? t : t2);
    }

    public static <T> T findFirst(Predicate<T> predicate, Iterable<T> iterable) {
        for (T obj : iterable) {
            if (predicate.test(obj)) {
                return (T)obj;
            }
        }

        return null;
    }

    public static <T> T findFirst(Predicate<T> predicate, T... ts) {
        for (T obj : ts) {
            if (predicate.test(obj)) {
                return (T)obj;
            }
        }

        return null;
    }

    public static <T> Queue<T> trim(Queue<T> ts, int value) {
        for (int i = ts.size(); i > value; i--) {
            ts.poll();
        }

        return ts;
    }
}
