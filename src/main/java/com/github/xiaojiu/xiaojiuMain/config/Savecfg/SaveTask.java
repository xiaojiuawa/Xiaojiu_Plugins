package com.github.xiaojiu.xiaojiuMain.config.Savecfg;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import com.github.xiaojiu.xiaojiuMain.Xiaojiu;

import java.util.*;

public class SaveTask implements ConfigurationSerializable {
    private final String name;
    private final int timer;
    private final int delay;
    private final UUID playerUUID;
    private final String[] args;
    private final OfflinePlayer player;
    private boolean Asynchronously = false;
    private final Date date;

    public SaveTask(String name, int timer, int delay, UUID playerUUID, boolean isAsynchronously, String[] args , Date date) {
        this.name = name;
        this.timer = timer;
        this.delay = delay;
        this.playerUUID = playerUUID;
        this.args=args;
        this.Asynchronously = isAsynchronously;
        this.player = Xiaojiu.getInstance().getServer().getOfflinePlayer(playerUUID);
        this.date=date;
    }

    //    public static SaveTask deserialization(Map<String, Object> map) {
//        return new SaveTask((String) map.get("name"),(int)map.get("timer"),(int)map.get("delay"),new UUID((long)map.get("mostSigBits"),(long)map.get("leastSigBits")),(boolean) map.get("Asynchronously"),(String[]) map.get("args"));
//    }
    public static SaveTask deserialize(Map<String, Object> map) {
        List<String> list = (List<String>) map.get("args");
        String[] args;
        if (list!=null&&!list.isEmpty()){
            args=new String[list.size()+1];
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                if (s==null) continue;
                if (s.isEmpty()) continue;
                args[i]=s;
            }
        }else{
            args=new String[0];
        }


//        if (args[0]==null|| args[0].isEmpty()) args=null;

        return new SaveTask((String) map.get("name"),
                (int) map.get("timer"),
                (int) map.get("delay"),
                new UUID((long) map.get("mostSigBits"), (long) map.get("leastSigBits")),
                (boolean) map.get("Asynchronously"),
                args,
                new Date((long)map.get("date")));
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
    public Date getDate(){
        return date;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("delay", delay);
        map.put("timer", timer);
        map.put("leastSigBits", playerUUID.getLeastSignificantBits());
        map.put("mostSigBits", playerUUID.getMostSignificantBits());
//        map.put("UUID",playerUUID);
        map.put("Asynchronously", Asynchronously);
        map.put("args", args);
        map.put("date",date.getTime());
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveTask task = (SaveTask) o;
        return getTimer() == task.getTimer() && getDelay() == task.getDelay() && isAsynchronously() == task.isAsynchronously() && Objects.equals(getName(), task.getName()) && Objects.equals(getPlayerUUID(), task.getPlayerUUID()) && Arrays.equals(getArgs(), task.getArgs()) && Objects.equals(getPlayer(), task.getPlayer()) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getTimer(), getDelay(), getPlayerUUID(), getPlayer(), isAsynchronously(), date);
        result = 31 * result + Arrays.hashCode(getArgs());
        return result;
    }
}
