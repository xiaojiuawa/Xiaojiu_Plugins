package Xiaojiu;

import Xiaojiu.tools.SafeGuardHelper;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventLoader implements Listener {
    public EventLoader(){

    }
    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent event){
        if (!SafeGuardHelper.isSafeGuard) return;
        if (event.getPlayer().hasPermission("xiaojiu.safeguard.in")) return;
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.LIGHT_PURPLE+"服务器维护,请等待服务器维护再登录");
        event.setKickMessage(ChatColor.LIGHT_PURPLE+"服务器维护,请等待服务器维护再登录");
    }
}
