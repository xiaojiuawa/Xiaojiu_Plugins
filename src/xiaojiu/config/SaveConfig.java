package xiaojiu.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.tools.LimitPlayerTools;

import java.io.File;
import java.io.IOException;

public class SaveConfig {
    public static JavaPlugin Instance;

    public static void OnEnable(JavaPlugin plugin) {
        Instance = plugin;
        plugin.saveDefaultConfig();
        plugin.saveResource("PlayerLimit.yml", false);
    }

    public static void Save() throws IOException {
        SaveLimitPlayer();
    }

    public static void SaveLimitPlayer() throws IOException {
        File file = new File(Instance.getDataFolder(), "PlayerLimit.yml");
        FileConfiguration LimitPlayer = YamlConfiguration.loadConfiguration(file);
        for (LimitPlayer player : LimitPlayerTools.list) {
            LimitPlayer.set(player.Name, player);
        }
        LimitPlayer.save(file);
    }

}
