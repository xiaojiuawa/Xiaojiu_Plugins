package xiaojiu.tools;

import org.bukkit.ChatColor;
import xiaojiu.StartPlugins;

public class MessageHelper {
    //    public
    public static int SendMessageAllPlayer(String message) {
        return StartPlugins.getInstance().getServer().broadcastMessage(message);
    }

    public static String InitMessage(String message) {
        return ChatColor.WHITE + "[" + ChatColor.AQUA + "系统提醒" + ChatColor.WHITE + "]" + message;
    }
}
