package com.github.xiaojiu.xiaojiuMain;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import com.github.xiaojiu.xiaojiuMain.Handles.LimitPlayer.LimitPlayerTools;
import com.github.xiaojiu.xiaojiuMain.Handles.SafeGuard.SafeGuardHelper;
import com.github.xiaojiu.xiaojiuMain.Handles.Vanish.Vanish;
import com.github.xiaojiu.xiaojiuMain.Handles.PlayerTime.PlayerJoinTimeTool;

public class EventLoader implements Listener {
    public EventLoader() {

    }

    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent event) {
        if (SafeGuardHelper.isSafeGuard) {
            if (event.getPlayer().hasPermission("xiaojiu.safeguard.in")) return;
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.LIGHT_PURPLE + "服务器维护,请等待服务器维护再登录");
            event.setKickMessage(ChatColor.LIGHT_PURPLE + "服务器维护,请等待服务器维护再登录");
        }
        if (LimitPlayerTools.isPlayerIn(event.getPlayer().getUniqueId())) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.LIGHT_PURPLE + LimitPlayerTools.GetKickMessage(event.getPlayer()));
            event.setKickMessage(ChatColor.LIGHT_PURPLE + LimitPlayerTools.GetKickMessage(event.getPlayer()));
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        PlayerJoinTimeTool.UpdatePlayer(event.getPlayer());
        Vanish.hideNewPlayer(event.getPlayer());
    }
}
