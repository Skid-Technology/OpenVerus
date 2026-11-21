package me.levansj01.verus.type;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.stream.Stream;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.compat.NMSManager;
import org.bukkit.command.Command;

public class VerusTypeLoader {
    private static final VerusType verusType = VerusType.values()[VerusPlugin.getType()];
    private Class<? extends Check>[] checkClasses;
    private Class<?>[] classes = null;

    public static VerusType getVerusType() {
        return verusType;
    }

    public List<Command> getCommands() {
        List<Command> list = new ArrayList<>();

        for (Class<?> clazz : this.getClassInfos()) {
            if (Command.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    list.add(clazz.asSubclass(Command.class).getDeclaredConstructor().newInstance());
                } catch (Throwable throwable) {
                    if (isDev()) {
                        VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + clazz.getName() + ": ", throwable);
                    }
                }
            }
        }

        return list;
    }

    public List<BaseCommand> getBaseCommands() {
        List<BaseCommand> list = new ArrayList<>();

        for (Class<?> clazz : this.getClassInfos()) {
            if (BaseCommand.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    list.add(clazz.asSubclass(BaseCommand.class).getDeclaredConstructor().newInstance());
                } catch (Throwable throwable) {
                    if (isDev()) {
                        throwable.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

    public void setCheckClasses(Class<? extends Check>[] checkClasses) {
        this.checkClasses = checkClasses;
    }

    public static boolean isCustom() {
        return verusType.afterOrEq(VerusType.CUSTOM);
    }

    public void setClasses(Class<?>[] classes) {
        this.classes = classes;
    }

    public static boolean isDev() {
        return verusType == VerusType.DEV;
    }

    public static boolean isDebug() {
        return false;
    }

    public Check[] loadChecks() {
        Class<? extends Check>[] checkClasses = this.getCheckClasses();
        List<Check> list = new ArrayList<>(checkClasses.length);

        for (Class<? extends Check> clazz : checkClasses) {
            try {
                list.add(clazz.getDeclaredConstructor().newInstance());
            } catch (Throwable throwable) {
                if (isDev()) {
                    throwable.printStackTrace();
                }
            }
        }

        return list.toArray(new Check[0]);
    }

    public VerusTypeLoader() {
        this.checkClasses = null;
    }

    private synchronized Class<?>[] getClassInfos() {
        if (this.classes == null) {
            List<Class<?>> discovered = new ArrayList<>();
            ClassLoader classLoader = this.getClass().getClassLoader();
            try {
                CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
                if (codeSource != null && codeSource.getLocation() != null) {
                    URI location = codeSource.getLocation().toURI();
                    File sourceFile = new File(location);
                    if (sourceFile.isFile()) {
                        try (JarFile jarFile = new JarFile(sourceFile)) {
                            Enumeration<JarEntry> entries = jarFile.entries();
                            while (entries.hasMoreElements()) {
                                String entryName = entries.nextElement().getName();
                                if (isDiscoverableEntry(entryName)) {
                                    loadClassIfPresent(discovered, classLoader, toClassName(entryName));
                                }
                            }
                        }
                    } else if (sourceFile.isDirectory()) {
                        Path root = sourceFile.toPath();
                        try (Stream<Path> stream = Files.walk(root)) {
                            stream.filter(Files::isRegularFile).forEach(path -> {
                                String rel = root.relativize(path).toString().replace(File.separatorChar, '/');
                                if (isDiscoverableEntry(rel)) {
                                    loadClassIfPresent(discovered, classLoader, toClassName(rel));
                                }
                            });
                        }
                    }
                }
            } catch (Throwable throwable) {
                throw new RuntimeException("Failed to scan Verus check and command classes", throwable);
            }

            this.classes = discovered.toArray(new Class<?>[0]);
        }

        return this.classes;
    }

    private static boolean isDiscoverableEntry(String entryName) {
        if (!entryName.endsWith(".class") || entryName.contains("$")) {
            return false;
        }
        return entryName.startsWith("me/levansj01/verus/check/checks/")
            || entryName.startsWith("me/levansj01/verus/command/")
            || entryName.startsWith("me/levansj01/verus/type/premium/checks/")
            || entryName.startsWith("me/levansj01/verus/type/custom/checks/")
            || entryName.startsWith("me/levansj01/verus/type/custom/commands/");
    }

    private static String toClassName(String entryName) {
        return entryName.substring(0, entryName.length() - 6).replace('/', '.');
    }

    private static void loadClassIfPresent(List<Class<?>> discovered, ClassLoader classLoader, String className) {
        try {
            discovered.add(Class.forName(className, false, classLoader));
        } catch (Throwable ignored) {
        }
    }

    public static void loader() {
        try {
            Class.forName("me.levansj01.verus.type." + verusType.name().toLowerCase() + ".Loader")
                .asSubclass(Loader.class)
                .getDeclaredConstructor()
                .newInstance()
                .load();
        } catch (Throwable throwable) {
            if (!(throwable instanceof ClassNotFoundException)) {
                throwable.printStackTrace();
            }
        }
    }

    public static boolean isEnterprise() {
        return verusType.afterOrEq(VerusType.ENTERPRISE);
    }

    @SuppressWarnings("unchecked")
    private synchronized Class<? extends Check>[] getCheckClasses() {
        if (this.checkClasses == null) {
            Class<?>[] classInfos = this.getClassInfos();
            List<Class<? extends Check>> list = new ArrayList<>(classInfos.length);

            for (Class<?> clazz : classInfos) {
                try {
                    if (Check.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                        Class<? extends Check> checkClass = clazz.asSubclass(Check.class);
                        Check instance = checkClass.getDeclaredConstructor().newInstance();
                        if (instance.valid() && !instance.getUnsupportedServers().contains(NMSManager.getInstance().getServerVersion())) {
                            list.add(checkClass);
                        }
                    }
                } catch (Throwable throwable) {
                    if (isDev()) {
                        VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + clazz.getName() + ": ", throwable);
                    }
                }
            }

            this.checkClasses = list.toArray(new Class[0]);
        }

        return this.checkClasses;
    }
}
