package me.levansj01.verus.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CheckInfo {
    ServerVersion unsupportedServerAtleast() default ServerVersion.NONE;

    ServerVersion[] unsupportedServers() default {};

    boolean butterfly() default false;

    boolean heavy() default false;

    String friendlyName() default "";

    boolean blocks() default false;

    ClientVersion unsupportedAtleast() default ClientVersion.NONE;

    int maxViolations() default Integer.MAX_VALUE;

    String subType();

    CheckVersion version() default CheckVersion.RELEASE;

    ClientVersion[] unsupportedVersions() default {};

    CheckType type();

    int priority() default 1;

    boolean schem() default false;

    boolean logData() default false;

    double minViolations() default 0.0;
}
