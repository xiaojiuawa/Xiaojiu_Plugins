package com.github.xiaojiu.tools;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.github.xiaojiu.Xiaojiu;

public class MessageHelper {
    //    public
    public static int SendMessageAllPlayer(String message) {
        return Xiaojiu.getInstance().getServer().broadcastMessage(message);
    }

    public static void SendNoPermissionMessage(CommandSender commandSender) {
        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限使用这个指令"));
    }

    public static String InitMessage(String message) {
        return ChatColor.WHITE + "[" + ChatColor.AQUA + "系统提醒" + ChatColor.WHITE + "]" + message;
    }
}
