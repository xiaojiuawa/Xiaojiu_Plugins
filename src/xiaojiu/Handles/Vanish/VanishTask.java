package xiaojiu.Handles.Vanish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Utils;

import java.util.*;

public class VanishTask {

    public static Timer timer = new Timer();

    public static void AddPlayerVanishTime(JavaPlugin Instance, Player player, int Time) {
        UUID uuid = player.getUniqueId();
        Utils.VanishMap.forEach((integer, string) -> {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, Time - integer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (Vanish.VanishPlayers.contains(uuid)) return;
                    Player player1 = StartPlugins.getInstance().getServer().getPlayer(uuid);
                    if (integer == 0) {
                        Bukkit.getScheduler().runTask(Instance, new Runnable() {
                            @Override
                            public void run() {
                                player1.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你的隐身时间已到，隐身状态已切换为关闭"));
                                Vanish.VanishPlayer(player1, false);
                            }
                        });
                    } else {
                        player1.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(string, "隐身")));
                    }
                }
            }, calendar.getTime());
        });
    }
}
