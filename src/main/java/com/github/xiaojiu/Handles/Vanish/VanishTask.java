package com.github.xiaojiu.Handles.Vanish;

import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class VanishTask {

    public static final Timer timer = new Timer();

    /**
     * 添加一个玩家的影身任务
     * @param Instance
     * @param player
     * @param Time
     */
    public static void AddPlayerVanishTime(JavaPlugin Instance, Player player, int Time) {
        UUID uuid = player.getUniqueId();
        Vanish.VanishPlayer(player, true);
        Utils.VanishMap.forEach((integer, string) -> {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, Time - integer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!Vanish.VanishPlayers.contains(uuid) || integer > Time) return;
                    Player player1 = Xiaojiu.getInstance().getServer().getPlayer(uuid);
                    if (player1==null) return;
                    // 如果玩家不在线则跳过
                    if (integer == 0) {
                        Bukkit.getScheduler().runTask(Instance, () -> {
                            player1.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你的隐身时间已到，隐身状态已切换为关闭"));
                            Vanish.VanishPlayer(player1, false);
                        });
                    } else {
                        player1.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(string, "隐身")));
                    }
                }
            }, calendar.getTime());
        });
    }
}
