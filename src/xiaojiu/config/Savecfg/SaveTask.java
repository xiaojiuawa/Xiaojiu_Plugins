package xiaojiu.config.Savecfg;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;

import java.util.*;

public class SaveTask implements ConfigurationSerializable {
    private final String name;
    private final int timer;
    private final int delay;
    private final UUID playerUUID;
    private final String[] args;
    private final OfflinePlayer player;
    private boolean Asynchronously=false;
    public SaveTask(String name, int timer, int delay, UUID playerUUID,boolean isAsynchronously,String[] args) {
        this.name=name;
        this.timer=timer;
        this.delay=delay;
        this.playerUUID=playerUUID;
        this.args=args;
        this.Asynchronously=isAsynchronously;
        this.player= StartPlugins.getInstance().getServer().getOfflinePlayer(playerUUID);
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public boolean isAsynchronously() {
        return Asynchronously;
    }

    public int getDelay() {
        return delay;
    }

    public int getTimer() {
        return timer;
    }

    public String getName() {
        return name;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("delay",delay);
        map.put("timer",timer);
        map.put("leastSigBits", playerUUID.getLeastSignificantBits());
        map.put("mostSigBits", playerUUID.getMostSignificantBits());
//        map.put("UUID",playerUUID);
        map.put("Asynchronously",Asynchronously);
        map.put("args",args);
        return map;
    }

//    public static SaveTask deserialization(Map<String, Object> map) {
//        return new SaveTask((String) map.get("name"),(int)map.get("timer"),(int)map.get("delay"),new UUID((long)map.get("mostSigBits"),(long)map.get("leastSigBits")),(boolean) map.get("Asynchronously"),(String[]) map.get("args"));
//    }
    public static SaveTask deserialize(Map<String,Object> map){
        List<String> list =(List<String>) map.get("args");
        String[] args;
        if (list==null){
            args=new String[0];
        }else{
            args = new String[list.size()+1];
            for (int i = 0; i < list.size(); i++) {
                args[i]=list.get(i);
            }
        }


//        if (args[0]==null|| args[0].isEmpty()) args=null;

        return new SaveTask((String) map.get("name"),(int)map.get("timer"),(int)map.get("delay"),new UUID((long)map.get("mostSigBits"),(long)map.get("leastSigBits")),(boolean) map.get("Asynchronously"),args);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveTask saveTask = (SaveTask) o;
        return timer == saveTask.timer && delay == saveTask.delay && Objects.equals(name, saveTask.name) && Objects.equals(playerUUID, saveTask.playerUUID) && Arrays.equals(args, saveTask.args) && Objects.equals(player, saveTask.player);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, timer, delay, playerUUID, player);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
