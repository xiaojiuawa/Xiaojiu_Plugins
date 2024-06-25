package com.github.xiaojiu.Handles.TpsTask;

import com.github.xiaojiu.tools.MessageHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class TpsTask implements Runnable {

    JavaPlugin plugin;
    public TpsTask(JavaPlugin plugin){
        this.plugin=plugin;
    }
    @Override
    public void run() {
        double[] tps = plugin.getServer().getTPS();
        if (plugin.getServer().getOnlinePlayers().size()<2) return;
        if (tps[0]<5){
            MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.RED+ String.format(Utils.getMessageCompletion("Task.TPS.warning"),5)));
        } else if (tps[0]<10) {
            MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.RED+ String.format(Utils.getMessageCompletion("Task.TPS.warning"),10)));
        } else if (tps[0]<15) {
            MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.RED+ String.format(Utils.getMessageCompletion("Task.TPS.warning"),15)));
        }else {
            MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.GREEN + Utils.getMessageCompletion("Task.TPS.normal")));
        }
    }
}
