package com.github.xiaojiu.tools;

import com.github.xiaojiu.Xiaojiu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Xiaojiu
 * @version 1.0.0
 *
 */
public class PostHelper {
    //    public

    /**
     * 向全服玩家推送一个消息
     * @param message 需要推送的消息
     * @return 成功推送的玩家数量
     */
    public static int SendMessageAllPlayer(String message) {
        final int[] ret = {0};
        Xiaojiu.getInstance().getServer().getScheduler().runTask(Xiaojiu.getInstance(), () -> ret[0] = Xiaojiu.getInstance().getServer().broadcastMessage(message));
        return ret[0];
    }

    /**
     * 向指定玩家推送无权限提醒
     * @param commandSender 需要推送的命令执行人
     * @deprecated
     */
    @Deprecated
    public static void SendNoPermissionMessage(CommandSender commandSender) {
        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + "你没有权限使用这个指令"));
    }

    /**
     * 初始化提醒消息
     * @param message 需要被初始化的消息
     * @return 初始化完毕的消息
     */
    public static String InitMessage(String message) {
        return getInitMessage() + message;
    }

    /**
     * 获取初始化消息的字符串
     * @return 初始化消息头
     */
    public static String getInitMessage(){
        return ChatColor.WHITE + "[" + ChatColor.AQUA + "系统提醒" + ChatColor.WHITE + "]";
    }
}
