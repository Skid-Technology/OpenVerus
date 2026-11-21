package me.levansj01.verus.util.java;

import com.google.common.base.Joiner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.DataWatcher.WatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

public class SafeReflection {
    public static Field access(Class clazz, String... args) {
        for (String arg : args) {
            try {
                Field field = clazz.getDeclaredField(arg);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
            }
        }

        throw new IllegalArgumentException(clazz.getSimpleName() + ":" + Joiner.on(",").join(args));
    }

    public static <T> Constructor<T> constructor(Class<T> clazz, Class<?>... classes) {
        try {
            Constructor declaredConstructor = clazz.getDeclaredConstructor(classes);
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        } catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static <T> T fetchConstructor(Constructor<T> constructor, Object... objs) {
        try {
            return (T)constructor.newInstance(objs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static Field access(Class clazz, String str) {
        try {
            Field field = clazz.getDeclaredField(str);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException exception) {
            throw new IllegalArgumentException(clazz.getSimpleName() + ":" + str, exception);
        }
    }

    public static <T> void set(Field field, Object obj, T t) {
        try {
            field.set(obj, t);
        } catch (IllegalAccessException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static <T> T execute(Method method, Object obj, Object... objs) {
        try {
            return (T)method.invoke(obj, objs);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static SimpleCommandMap getCommandMap() {
        return getLocalField(Bukkit.getServer().getClass(), Bukkit.getServer(), new String[]{"commandMap"});
    }

    public static PacketPlayOutNamedEntitySpawn spawn(
        int value, UUID uuid, int value2, int value3, int value4, byte b, byte b2, int value5, DataWatcher dataWatcher, List<WatchableObject> watchableObjects
    ) {
        throw new UnsupportedOperationException();
    }

    public static <T> T fetch(Field field, Object obj, Class<T> clazz) {
        try {
            return (T)field.get(obj);
        } catch (IllegalAccessException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static <T> void setLocalField(Class clazz, Object obj, String str, T t) {
        set(access(clazz, str), obj, t);
    }

    public static <T> T getLocalField(String[] args, Object obj, String str) {
        ReflectiveOperationException exception = null;

        for (String arg : args) {
            try {
                Class clazz = Class.forName(arg);
                Field field = clazz.getDeclaredField(str);
                field.setAccessible(true);
                return (T)field.get(obj);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException exception2) {
                exception = exception2;
            }
        }

        throw new IllegalArgumentException(String.join(",", args) + ":" + str, exception);
    }

    public static Method access(String[] args, String str, Class<?>... classes) {
        ReflectiveOperationException exception = null;

        for (String arg : args) {
            try {
                Class clazz = Class.forName(arg);
                Method method = clazz.getDeclaredMethod(str, classes);
                method.setAccessible(true);
                return method;
            } catch (ClassNotFoundException | NoSuchMethodException exception2) {
                exception = exception2;
            }
        }

        throw new IllegalArgumentException(String.join(",", args) + ":" + str + "(", exception);
    }

    public static Map<String, Command> getKnownCommands(SimpleCommandMap simpleCommandMap) {
        return getLocalField(SimpleCommandMap.class, simpleCommandMap, new String[]{"knownCommands"});
    }

    public static Method access(Class<?> clazz, String str, Class<?>... classes) {
        try {
            Method method = clazz.getDeclaredMethod(str, classes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException(clazz.getSimpleName() + ":" + str + "(", exception);
        }
    }

    public static <T> T fetch(Field field, Object obj) {
        try {
            return (T)field.get(obj);
        } catch (IllegalAccessException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static <T> T getLocalField(Class<?> clazz, Object obj, String... args) {
        return fetch(access(clazz, args), obj);
    }

    public static Class<?> findClass(String... args) {
        for (String arg : args) {
            try {
                return Class.forName(arg);
            } catch (ClassNotFoundException ignored) {
            }
        }

        throw new IllegalArgumentException("Could not find a class: " + Joiner.on(", ").join(args));
    }

    public static <T> T getLocalField(String str, Object obj, String str2) {
        try {
            return fetch(access(Class.forName(str), str2), obj);
        } catch (ClassNotFoundException exception) {
            throw new IllegalArgumentException(str + ":" + str2, exception);
        }
    }
}
