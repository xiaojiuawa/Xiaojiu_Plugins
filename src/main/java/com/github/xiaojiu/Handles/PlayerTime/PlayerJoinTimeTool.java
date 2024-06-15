package com.github.xiaojiu.Handles.PlayerTime;

import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.config.Savecfg.JTPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class PlayerJoinTimeTool {
    public static final HashMap<UUID, JTPlayer> map = new HashMap<>();
    public static final File file = new File(Xiaojiu.getInstance().getDataFolder(), "PlayerJoinTime.yml");
    public static final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public static void UpdatePlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if (map.containsKey(uuid)) {
            JTPlayer player2 = map.get(uuid);
            player2.LastJoinTime = new Date();
            map.replace(uuid, player2);
        } else {
            JTPlayer player2 = new JTPlayer(new Date(), uuid, player.getName());
            map.put(player.getUniqueId(), player2);
        }
    }

    public static Date GetPlayerLastJoinTime(UUID uuid) {
        if (!map.containsKey(uuid)) {
            return null;
        }
        return map.get(uuid).LastJoinTime;
    }

    @Deprecated
    public static void ReadTime() {
        for (String key : configuration.getKeys(false)) {
//            System.out.println("1234");
            JTPlayer player = (JTPlayer) configuration.get(key);
            map.put(player.uuid, player);
//            System.out.println(player.PlayerName+"1");
        }
    }

    public static List<String> getRecordedPlayers(String exclude) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<UUID, JTPlayer> entry : map.entrySet()) {
            if (entry.getValue().PlayerName.toLowerCase().startsWith(exclude)) {
                list.add(entry.getValue().PlayerName);
            }
        }
        return list;
    }
}
