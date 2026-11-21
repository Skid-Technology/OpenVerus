package me.levansj01.verus.util.pastebin.api;

import java.util.List;
import me.levansj01.verus.util.pastebin.AccountCredentials;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.UserInformation;
import org.jetbrains.annotations.NotNull;

public interface PasteBinApi {
    boolean deletePaste(@NotNull AccountCredentials credentials, @NotNull String str);

    String getPasteContent(@NotNull AccountCredentials credentials, @NotNull Paste paste);

    UserInformation fetchUserInformation(@NotNull AccountCredentials credentials);

    String getPasteContent(@NotNull Paste paste);

    boolean deletePaste(@NotNull AccountCredentials credentials, @NotNull Paste paste);

    String fetchUserKey(@NotNull AccountCredentials credentials);

    String createPaste(@NotNull AccountCredentials credentials, @NotNull Paste paste);

    List<Paste> listUserPastes(@NotNull AccountCredentials credentials, int value);

    List<Paste> listUserPastes(@NotNull AccountCredentials credentials);

    List<Paste> listTrends(@NotNull AccountCredentials credentials);
}
