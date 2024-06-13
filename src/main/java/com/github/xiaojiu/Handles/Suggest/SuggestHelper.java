package com.github.xiaojiu.Handles.Suggest;


import java.util.ArrayList;
import java.util.UUID;

public class SuggestHelper {
    public final ArrayList<UUID> Approve;
    public final ArrayList<UUID> Refuse;
    public boolean isSuggesting;
    public UUID sponsor;

    public SuggestHelper() {
        Approve = new ArrayList<>();
        Refuse = new ArrayList<>();
        isSuggesting = false;
    }

    public void SuggestStart() {
        isSuggesting = true;
    }

    public void SuggestEnd() {
        isSuggesting = false;
        Approve.clear();
        Refuse.clear();
    }

    public boolean isSuggested(UUID uuid) {
        return Approve.contains(uuid) || Refuse.contains(uuid);
    }

    public void delSuggest(UUID uuid) {
        Approve.remove(uuid);
        Refuse.remove(uuid);
    }
}
