package Xiaojiu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;

public class EventLoader implements Listener {
    public EventLoader(){

    }
    @EventHandler
    public void PlayerBucketFillEvent(PlayerBucketFillEvent event){
        ReLoadHelper.ReLoadServer();
    }

}
