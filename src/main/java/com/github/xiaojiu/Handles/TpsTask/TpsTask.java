package com.github.xiaojiu.Handles.TpsTask;

import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
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
            PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.RED+ String.format(MessageHelper.getMessageCompletion("Task.TPS.warning"),5)));
        } else if (tps[0]<10) {
            PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.RED+ String.format(MessageHelper.getMessageCompletion("Task.TPS.warning"),10)));
        } else if (tps[0]<15) {
            PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.RED+ String.format(MessageHelper.getMessageCompletion("Task.TPS.warning"),15)));
        }else {
            PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.GREEN + MessageHelper.getMessageCompletion("Task.TPS.normal")));
        }
    }
}
