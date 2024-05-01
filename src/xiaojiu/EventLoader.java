package xiaojiu;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.Handles.LimitPlayer.LimitPlayerTools;
import xiaojiu.Handles.SafeGuard.SafeGuardHelper;

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
        PlayerJoinTimeTask.UpdatePlayer(event.getPlayer());
    }
}
