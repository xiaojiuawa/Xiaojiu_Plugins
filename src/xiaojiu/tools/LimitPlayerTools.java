package xiaojiu.tools;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.config.Savecfg.LimitPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LimitPlayerTools {
    public static HashMap<UUID, String> hashMap = new HashMap<>();
    public static ArrayList<LimitPlayer> list = new ArrayList<>();
    public static File file = new File(StartPlugins.getInstance().getDataFolder(), "PlayerLimit.yml");
    public static FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public static void ReadPlayers(JavaPlugin plugin) {
        for (String key : configuration.getKeys(false)) {
            System.out.print("1");
            LimitPlayer player = (LimitPlayer) configuration.get(key);
            UUID uuid = new UUID(player.mostSigBits, player.leastSigBits);
            String message = player.message;
            hashMap.put(uuid, message);
        }
    }

    public static String add(String message, Player player, boolean addToOffer) {
        UUID uuid = player.getUniqueId();
        if (hashMap.containsKey(uuid)) {
            return "限制列表中已经有这位玩家了";
        }
        hashMap.put(uuid, message);
        if (addToOffer) {
            list.add(new LimitPlayer(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits(), player.getName(), message));
        }
        return "限制列表添加玩家" + player.getName() + "成功";
    }

    public static String remove(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();
        if (hashMap.containsKey(uuid)) {
            hashMap.remove(uuid);
            LimitPlayer player1 = new LimitPlayer(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits(), player.getName(), hashMap.get(uuid));
            list.remove(player1);
            configuration.set(player.getName(), null);
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "限制列表移除玩家" + player.getName() + "成功";
        }
        return "限制列表无玩家" + player.getName();

    }

    public static boolean isPlayerIn(Player player) {
        return hashMap.containsKey(player.getUniqueId());
    }

    public static boolean isPlayerIn(UUID uuid) {
        return hashMap.containsKey(uuid);
    }

    public static String GetKickMessage(Player player) {
        return hashMap.get(player.getUniqueId());
    }
}
