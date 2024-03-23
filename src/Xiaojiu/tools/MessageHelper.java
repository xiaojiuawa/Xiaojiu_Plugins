package Xiaojiu.tools;

import Xiaojiu.StartPlugins;
import org.bukkit.ChatColor;

public class MessageHelper {
    public static int SendMessageAllPlayer(String message,String title){
        return StartPlugins.getInstance().getServer().broadcastMessage(InitMessage(message,title));
    }

    public static String InitMessage(String message,String title){
        return ChatColor.DARK_PURPLE+"服务器通知 "+ChatColor.LIGHT_PURPLE+title+' '+message;
    }
    public static int SendMessageAllPlayer(String message){
        return StartPlugins.getInstance().getServer().broadcastMessage(message);
    }
}
