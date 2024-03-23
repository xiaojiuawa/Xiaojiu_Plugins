package Xiaojiu.RestartServer;

import Xiaojiu.StartPlugins;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;


public class RestartHelper {
    public static boolean isRestart =false;
    public static boolean Restart(int num){
        if(isRestart) return false;
        isRestart=true;
        WaitTimeToRestart.ReLoadServer(num);
        return true;
    }
    public static void KickAllPlayers(Collection<? extends Player> list){
        Iterator<? extends Player> iterator = list.stream().iterator();
        while (iterator.hasNext()){
            iterator.next().kickPlayer(ChatColor.LIGHT_PURPLE+"服务器重启，请耐心等待");
        }
    }
    public static void ShutdownServer(){
        StartPlugins.getInstance().getServer().shutdown();
    }

    public static void Done(){
        KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers());
        ShutdownServer();
    }
}
