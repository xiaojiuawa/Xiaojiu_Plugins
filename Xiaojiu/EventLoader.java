package Xiaojiu;

import Xiaojiu.RestartServer.RestartHelper;
import Xiaojiu.RestartServer.WaitTimeToRestart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
public class EventLoader implements Listener {
    public EventLoader(){

    }
    @EventHandler
    public void PlayerBucketFillEvent(PlayerBucketFillEvent event){
        WaitTimeToRestart.ReLoadServer(60);
    }

}
