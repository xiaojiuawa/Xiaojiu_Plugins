package xiaojiu.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.tools.LimitPlayerTools;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class SaveConfig {
    public static JavaPlugin Instance;

    public static void OnEnable(JavaPlugin plugin) {
        Instance = plugin;
        plugin.saveDefaultConfig();
    }

    public static void Save() {
        SaveLimitPlayer();
        SavePlayerTime();
    }

    public static void SaveLimitPlayer() {
        File file = new File(Instance.getDataFolder(), "PlayerLimit.yml");
        FileConfiguration LimitPlayer = YamlConfiguration.loadConfiguration(file);
        for (LimitPlayer player : LimitPlayerTools.list) {
            LimitPlayer.set(player.Name, player);
        }
        try {
            LimitPlayer.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public static void SavePlayerTime() {
        File file = new File(Instance.getDataFolder(), "PlayerJoinTime.yml");
        FileConfiguration PlayerTime = YamlConfiguration.loadConfiguration(file);
        for (Map.Entry<UUID, JTPlayer> entry : PlayerJoinTimeTask.map.entrySet()) {
            PlayerTime.set(entry.getValue().PlayerName, entry.getValue());
        }
        try {
            PlayerTime.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
