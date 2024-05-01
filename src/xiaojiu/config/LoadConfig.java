package xiaojiu.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xiaojiu.Handles.LimitPlayer.LimitPlayerTools;
import xiaojiu.Log.mysql.BasicSQL;
import xiaojiu.StartPlugins;
import xiaojiu.task.PlayerJoinTimeTask;

import java.io.File;

public class LoadConfig {
    public static void ReadAllFile() {
        LimitPlayerTools.ReadPlayers();
        PlayerJoinTimeTask.ReadTime();
        ReadSQL();
    }

    public static void ReadSQL() {
        File file = new File(StartPlugins.getInstance().getDataFolder(), "config.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = configuration.getConfigurationSection("SQL");
        BasicSQL.user = section.getString("user");
        BasicSQL.password = section.getString("password");
        BasicSQL.url = section.getString("url");
    }
}
