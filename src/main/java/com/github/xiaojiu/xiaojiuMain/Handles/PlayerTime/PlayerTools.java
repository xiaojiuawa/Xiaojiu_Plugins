package com.github.xiaojiu.xiaojiuMain.Handles.PlayerTime;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.github.xiaojiu.xiaojiuMain.Xiaojiu;

public class PlayerTools {
    public static Player GetPlayer(String PlayerName) {
        return Xiaojiu.getInstance().getServer().getPlayer(PlayerName);
    }

    public static OfflinePlayer GetPlayerOffer(String PlayerName) {
        return Xiaojiu.getInstance().getServer().getOfflinePlayer(PlayerName);
    }
}
