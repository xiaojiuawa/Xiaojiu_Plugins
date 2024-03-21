package Xiaojiu;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;

public class ReLoadHelper {
    public static void KickAllPlayers(Collection<? extends Player> list){
        Iterator<? extends Player> iterator = list.stream().iterator();
        while (iterator.hasNext()){
            iterator.next().kickPlayer("重启");
        }
    }
    public static void ShutdownServer(){
        StartPlugins.getInstance().getServer().shutdown();
    }
    public static void ReLoadServer(){
        KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers());
        ShutdownServer();
    }
}
