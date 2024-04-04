package xiaojiu.tools;

import xiaojiu.StartPlugins;

public class MessageHelper {
//    public
    public static int SendMessageAllPlayer(String message){
        return StartPlugins.getInstance().getServer().broadcastMessage(message);
    }
}
