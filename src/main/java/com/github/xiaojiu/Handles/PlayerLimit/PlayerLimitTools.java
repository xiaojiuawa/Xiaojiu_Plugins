package com.github.xiaojiu.Handles.PlayerLimit;

import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.config.Savecfg.LimitPlayer;
import com.github.xiaojiu.message.MessageHelper;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerLimitTools {
    public static final HashMap<UUID, LimitPlayer> hashMap = new HashMap<>();
    public static final ArrayList<LimitPlayer> list = new ArrayList<>();
    public static final File file = new File(Xiaojiu.getInstance().getDataFolder(), "PlayerLimit.yml");
    public static final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    @Deprecated
    public static void ReadPlayers() {
        for (String key : configuration.getKeys(false)) {
//            System.out.print("1");
            LimitPlayer player = (LimitPlayer) configuration.get(key);
            UUID uuid = new UUID(player.mostSigBits, player.leastSigBits);
//            String message = player.message;
            hashMap.put(uuid, player);
        }
    }

    public static List<String> GetAllPlayerName(String Start) {
        List<String> list1 = new ArrayList<>();
        hashMap.forEach((uuid, limitPlayer) -> {
            if (limitPlayer.Name.toLowerCase().startsWith(Start.toLowerCase())) {
                list1.add(limitPlayer.Name);
            }
        });
        return list1;
    }

    public static String add(String message, Player player, boolean addToOffer) {
        UUID uuid = player.getUniqueId();
        LimitPlayer limitPlayer = new LimitPlayer(uuid, player.getName(), message);
        if (hashMap.containsKey(uuid)) {
            return MessageHelper.getMessageCompletion("PlayerLimit.add.have");
        }
        hashMap.put(uuid, limitPlayer);
        if (addToOffer) {
            list.add(limitPlayer);
        }
        return String.format(MessageHelper.getMessageCompletion("PlayerLimit.add.success"), player.getName());
//        return "限制列表添加玩家" + player.getName() + "成功";
    }

    public static String remove(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();
        if (hashMap.containsKey(uuid)) {
            LimitPlayer player1 = hashMap.get(uuid);
            hashMap.remove(uuid);
            list.remove(player1);
            configuration.set(player.getName(), null);
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return String.format(MessageHelper.getMessageCompletion("PlayerLimit.del.success"), player.getName());
//            return "限制列表移除玩家" + player.getName() + "成功";
        }
        return String.format(MessageHelper.getMessageCompletion("PlayerLimit.del.notFind"), player.getName());
//        return "限制列表无玩家" + player.getName();

    }

    public static boolean isPlayerIn(Player player) {
        return hashMap.containsKey(player.getUniqueId());
    }

    public static boolean isPlayerIn(UUID uuid) {
        return hashMap.containsKey(uuid);
    }

    public static String GetKickMessage(Player player) {
        return hashMap.get(player.getUniqueId()).message;
    }
}
