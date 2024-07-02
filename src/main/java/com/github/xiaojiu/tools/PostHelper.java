package com.github.xiaojiu.tools;

import com.github.xiaojiu.Xiaojiu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PostHelper {
    //    public
    public static int SendMessageAllPlayer(String message) {
        final int[] ret = {0};
        Xiaojiu.getInstance().getServer().getScheduler().runTask(Xiaojiu.getInstance(), () -> ret[0] = Xiaojiu.getInstance().getServer().broadcastMessage(message));
        return ret[0];
    }

    public static void SendNoPermissionMessage(CommandSender commandSender) {
        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + "你没有权限使用这个指令"));
    }

    public static String InitMessage(String message) {
        return ChatColor.WHITE + "[" + ChatColor.AQUA + "系统提醒" + ChatColor.WHITE + "]" + message;
    }
}
