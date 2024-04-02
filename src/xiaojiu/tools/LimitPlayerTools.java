package xiaojiu.tools;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LimitPlayerTools {
    public static HashMap<UUID,String> hashMap = new HashMap<>();
    public static String add(String message,Player player){
        if (hashMap.containsKey(player.getUniqueId())){
            return "限制列表中已经有这位玩家了";
        }
        hashMap.put(player.getUniqueId(),message);
        return "限制列表添加玩家"+player.getName()+"成功";
    }
    public static String remove(Player player){
        if (hashMap.containsKey(player.getUniqueId())){
            hashMap.remove(player.getUniqueId());
            return "限制列表移除玩家"+player.getName()+"成功";
        }
        return "限制列表无玩家"+player.getName();

    }
    public static boolean isPlayerIn(Player player){
        return hashMap.containsKey(player.getUniqueId());
    }
    public static boolean isPlayerIn(UUID uuid){
        return hashMap.containsKey(uuid);
    }
    public static String GetKickMessage(Player player){
        return hashMap.get(player.getUniqueId());
    }
}
