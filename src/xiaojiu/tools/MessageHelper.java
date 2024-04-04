package xiaojiu.tools;

import xiaojiu.StartPlugins;
import org.bukkit.ChatColor;

public class MessageHelper {
//    public
    public static int SendMessageAllPlayer(String message){
        return StartPlugins.getInstance().getServer().broadcastMessage(message);
    }
}
