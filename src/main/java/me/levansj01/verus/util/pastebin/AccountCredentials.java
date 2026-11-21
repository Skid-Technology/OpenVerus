package me.levansj01.verus.util.pastebin;

import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AccountCredentials {
    private Optional<String> userKey;
    private String devKey;
    private Optional<String> password;
    private Optional<String> userName;

    public AccountCredentials(@NotNull String str) {
        this(str, null, null);
    }

    public AccountCredentials(@NotNull String devKey, @Nullable String str, @Nullable String str2) {
        Objects.requireNonNull(devKey, " The dev key cannot be null!");
        this.devKey = devKey;
        this.password = Optional.ofNullable(str2);
        this.userName = Optional.ofNullable(str);
        this.userKey = Optional.empty();
    }

    public void setUserKey(Optional<String> userKey) {
        this.userKey = userKey;
    }

    public Optional<String> getUserSessionKey() {
        return this.userKey;
    }

    public String getDevKey() {
        return this.devKey;
    }

    public Optional<String> getPassword() {
        return this.password;
    }

    public Optional<String> getUserName() {
        return this.userName;
    }
}
