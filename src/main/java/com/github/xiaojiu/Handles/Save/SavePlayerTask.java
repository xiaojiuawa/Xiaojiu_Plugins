package com.github.xiaojiu.Handles.Save;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SavePlayerTask extends BasicSaveHandles {
    public SavePlayerTask(int taskid, JavaPlugin plugin, OfflinePlayer player, String... args) {
        super(taskid, plugin, player, "player", args);
        this.canAsynchronously = true;
//        this.name="Player";
    }

    @Override
    public void run() {
//        MessageHelper.SendMessageAllPlayer("1");
        if (args == null || args.length == 0 || args[0]==null || args[0].isEmpty()) {
            plugin.getServer().getOnlinePlayers().forEach(player1 -> {
                player1.saveData();
//                Xiaojiu.logger.info("玩家" + player1.getName() + "数据保存完毕");
            });

        } else {
            List<String> list = new ArrayList<>();
            for (String arg : args) {
                Player player2 = plugin.getServer().getPlayer(arg);
                if (player2 == null) {
                    list.add(arg);
                } else {
                    player2.saveData();
//                    Xiaojiu.logger.info("玩家" + player2.getName() + "数据保存完毕");
                }
            }
            if (!list.isEmpty()) {
//                this.player.sendMessage(MessageHelper.InitMessage(Arrays.toString(list.toArray())+"未在线或未在本子服"));
            }
        }

    }
}
