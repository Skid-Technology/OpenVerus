package me.levansj01.verus.util.pastebin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.levansj01.verus.util.pastebin.api.PasteBinApi;
import me.levansj01.verus.util.pastebin.api.PasteBinApiFactory;
import org.jetbrains.annotations.NotNull;

public class PasteBin {
    private final AccountCredentials accountCredentials;
    private Optional<UserInformation> user;
    private PasteBinApi pasteBinApi;

    public PasteBin(@NotNull AccountCredentials accountCredentials, @NotNull PasteBinApi pasteBinApi) {
        Objects.requireNonNull(accountCredentials);
        Objects.requireNonNull(pasteBinApi);
        this.accountCredentials = accountCredentials;
        this.pasteBinApi = pasteBinApi;
    }

    public UserInformation fetchUserInformation() {
        this.user = Optional.ofNullable(this.pasteBinApi.fetchUserInformation(this.accountCredentials));
        return this.user.orElseThrow(() -> new RuntimeException("Couldn't fetch the user information :("));
    }

    public boolean deletePaste(@NotNull Paste paste) {
        return this.pasteBinApi.deletePaste(this.accountCredentials, paste);
    }

    @NotNull
    public List<Paste> listUserPastes(int value) {
        return this.pasteBinApi.listUserPastes(this.accountCredentials, value);
    }

    public Optional<UserInformation> getUserInformation() {
        return this.user;
    }

    public AccountCredentials getAccountCredentials() {
        return this.accountCredentials;
    }

    public List<Paste> listTrendingPastes() {
        return this.pasteBinApi.listTrends(this.accountCredentials);
    }

    public String createPaste(@NotNull Paste paste) {
        return this.pasteBinApi.createPaste(this.accountCredentials, paste);
    }

    @NotNull
    public List<Paste> listUserPastes() {
        return this.pasteBinApi.listUserPastes(this.accountCredentials);
    }

    public String getPasteContent(@NotNull Paste paste) {
        return this.pasteBinApi.getPasteContent(this.accountCredentials, paste);
    }

    public PasteBin(@NotNull AccountCredentials credentials) {
        this(credentials, PasteBinApiFactory.createDefaultImplementation());
    }
}
