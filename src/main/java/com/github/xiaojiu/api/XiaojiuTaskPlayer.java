package com.github.xiaojiu.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface XiaojiuTaskPlayer {
    UUID getPlayerUUID();

    OfflinePlayer getPlayer();

    void setPlayer(Player player);

    void setPlayer(String playerName);

    void setPlayer(UUID uuid);

//    List<PlayerHome> getHomeList();
}
