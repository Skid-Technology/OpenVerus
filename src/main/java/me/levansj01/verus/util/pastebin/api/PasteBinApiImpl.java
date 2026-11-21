package me.levansj01.verus.util.pastebin.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import me.levansj01.verus.util.pastebin.AccountCredentials;
import me.levansj01.verus.util.pastebin.GuestPaste;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.PasteVisibility;
import me.levansj01.verus.util.pastebin.UserInformation;
import org.jetbrains.annotations.NotNull;

public class PasteBinApiImpl implements PasteBinApi {
    private Function<Map<Object, Object>, Optional<String>> postFunc = entry -> WebUtils.post("https://pastebin.com/api/api_post.php", entry);

    @Override
    public List<Paste> listTrends(@NotNull AccountCredentials credentials) {
        return this.doPost(
            credentials,
            PasteBinApiOptions.TRENDS,
            thi -> this.postFunc
                .apply(thi)
                .<String>map(entry -> String.format("<pastes>%s</pastes>", entry))
                .map(entry -> XMLUtils.unMarshal(entry, Pastes.class))
                .map(Pastes::getPastes)
                .map(PasteInfoUpdater::updateDate)
                .orElse(Collections.emptyList())
        );
    }

    @Override
    public String fetchUserKey(@NotNull AccountCredentials credentials) {
        Objects.requireNonNull(credentials);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(PasteBinApiParams.DEV_KEY, credentials.getDevKey());
        map.put(PasteBinApiParams.USER_NAME, credentials.getUserName().orElseThrow(() -> new IllegalArgumentException("Missing username!")));
        map.put(PasteBinApiParams.USER_PASSWORD, credentials.getPassword().orElseThrow(() -> new IllegalArgumentException("Missing password!")));
        return ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_login.php", map))
            .orElseThrow(() -> new RuntimeException("Error while fething user session key."));
    }

    public void updateUserSessionKey(@NotNull AccountCredentials credentials) {
        if (!credentials.getUserSessionKey().isPresent()) {
            credentials.setUserKey(Optional.ofNullable(this.fetchUserKey(credentials)));
        }
    }

    private <R> R doPost(AccountCredentials credentials, PasteBinApiOptions pasteBinApiOptions, Function<Map<Object, Object>, R> function) {
        HashMap map = new HashMap();
        if (credentials != null) {
            map.put(PasteBinApiParams.DEV_KEY, credentials.getDevKey());
        }

        map.put(PasteBinApiParams.OPTION, pasteBinApiOptions);
        return (R)function.apply(map);
    }

    @Override
    public List<Paste> listUserPastes(@NotNull AccountCredentials credentials) {
        return this.listUserPastes(credentials, 50);
    }

    @Override
    public UserInformation fetchUserInformation(@NotNull AccountCredentials credentials) {
        Objects.requireNonNull(credentials);
        this.updateUserSessionKey(credentials);
        return this.doPost(
            credentials,
            PasteBinApiOptions.USER_DETAILS,
            thi -> {
                String str = credentials.getUserSessionKey()
                    .orElseThrow(() -> new RuntimeException("To fetch user information you need an User Key, please, fetch the user key first!"));
                thi.put(PasteBinApiParams.USER_KEY, str);
                Optional optional = ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_post.php", thi))
                    .map(entry -> XMLUtils.unMarshal(entry, UserInformation.class));
                if (optional.isPresent()) {
                    return (UserInformation)optional.get();
                } else {
                    throw new RuntimeException("Could not fetch User information.");
                }
            }
        );
    }

    @Override
    public boolean deletePaste(@NotNull AccountCredentials credentials, @NotNull String str) {
        Objects.requireNonNull(credentials);
        if (StringUtils.isNullOrEmpty(str)) {
            throw new IllegalArgumentException("The paste key can't be null!");
        }

        credentials.getUserSessionKey()
            .orElseThrow(() -> new RuntimeException("To delete a key you need a user session key, please, fetch the user session key first!"));
        return this.doPost(
            credentials,
            PasteBinApiOptions.DELETE,
            thi -> {
                thi.put(PasteBinApiParams.USER_KEY, credentials.getUserSessionKey().get());
                thi.put(PasteBinApiParams.UNIQUE_PASTE_KEY, str);
                return ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_post.php", thi))
                    .filter("Paste Removed"::equalsIgnoreCase)
                    .isPresent();
            }
        );
    }

    @Override
    public String createPaste(@NotNull AccountCredentials credentials, @NotNull Paste paste) {
        Objects.requireNonNull(credentials);
        Objects.requireNonNull(paste, "The paste can't be null, are you kidding?");
        Objects.requireNonNull(paste.getContent(), "The paste need to have some Content");
        boolean flag = paste instanceof GuestPaste;
        if (credentials.getUserName().isPresent() && credentials.getPassword().isPresent() && !flag) {
            this.updateUserSessionKey(credentials);
        }

        HashMap map = new HashMap();
        map.put(PasteBinApiParams.DEV_KEY, credentials.getDevKey());
        map.put(PasteBinApiParams.OPTION, PasteBinApiOptions.PASTE);
        map.put(PasteBinApiParams.PASTE_CODE, paste.getContent());
        if (!flag) {
            credentials.getUserSessionKey().ifPresent(entry -> map.put(PasteBinApiParams.USER_KEY, entry));
        }

        if (StringUtils.isNotNullNorEmpty(paste.getTitle())) {
            map.put(PasteBinApiParams.PASTE_NAME, paste.getTitle());
        }

        if (paste.getHighLight() != null) {
            map.put(PasteBinApiParams.PASTE_FORMAT, paste.getHighLight());
        }

        if (paste.getVisibility() != null) {
            map.put(PasteBinApiParams.PASTE_PRIVATE, paste.getVisibility());
        }

        if (paste.getExpiration() != null) {
            map.put(PasteBinApiParams.PASTE_EXPIRE_DATE, paste.getExpiration());
        }

        String str = String.valueOf(ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_post.php", map)).get());
        paste.setKey(PasteUtil.getPasteKeyFromUrl(str));
        paste.setUrl(str);
        return str;
    }

    @Override
    public List<Paste> listUserPastes(@NotNull AccountCredentials credentials, int value) {
        Objects.requireNonNull(credentials);
        this.updateUserSessionKey(credentials);
        credentials.getUserSessionKey().orElseThrow(() -> new NullPointerException("To list pastes you need an User Key, please, fetch the user key first!"));
        return this.doPost(
            credentials,
            PasteBinApiOptions.LIST,
            thi -> {
                thi.put(PasteBinApiParams.USER_KEY, credentials.getUserSessionKey().get());
                thi.put(PasteBinApiParams.LIST_RESULT_LIMIT, value);
                return ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_post.php", thi))
                    .map(entry -> String.format("<pastes>%s</pastes>", entry))
                    .map(entry -> XMLUtils.unMarshal(entry, Pastes.class))
                    .map(Pastes::getPastes)
                    .map(PasteInfoUpdater::updateDate)
                    .orElse(Collections.emptyList());
            }
        );
    }

    @Override
    public String getPasteContent(@NotNull AccountCredentials credentials, @NotNull Paste paste) {
        Objects.requireNonNull(paste);
        return paste.getVisibility() == PasteVisibility.PRIVATE ? this.doPost(credentials, PasteBinApiOptions.SHOW_PASTE, thi -> {
            thi.put(PasteBinApiParams.USER_KEY, credentials.getUserSessionKey().get());
            thi.put(PasteBinApiParams.UNIQUE_PASTE_KEY, paste.getKey().toUpperCase());
            return ResponseUtils.requiresValidResponse(WebUtils.post("https://pastebin.com/api/api_raw.php", thi)).get();
        }) : this.getPasteContent(paste);
    }

    @Override
    public String getPasteContent(@NotNull Paste paste) {
        Objects.requireNonNull(paste);
        HashMap map = new HashMap();
        map.put("i", paste.getKey());
        return String.valueOf(ResponseUtils.requiresValidResponse(WebUtils.get("https://pastebin.com/raw.php", map, true)).get());
    }

    @Override
    public boolean deletePaste(@NotNull AccountCredentials credentials, @NotNull Paste paste) {
        Objects.requireNonNull(paste);
        Objects.requireNonNull(paste.getKey(), "The key to the paste being deleted can't be null!");
        return this.deletePaste(credentials, paste.getKey());
    }
}
