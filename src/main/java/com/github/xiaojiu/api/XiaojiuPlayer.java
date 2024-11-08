package com.github.xiaojiu.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface XiaojiuPlayer {
    List<PlayerHome> getHomeList();

    Player getPlayer();

    List<XiaojiuPlayer> getPlayerList();
}
