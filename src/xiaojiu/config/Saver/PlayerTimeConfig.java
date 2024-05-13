package xiaojiu.config.Saver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuConfig;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.task.PlayerJoinTimeTask;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class PlayerTimeConfig implements XiaojiuConfig {
    @Override
    public void Save() {
        File file = new File(StartPlugins.getInstance().getDataFolder(), "PlayerJoinTime.yml");
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

    @Override
    public void ReLoad() {
        this.Save();
        this.Load();
    }

    @Override
    public void Load() {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(StartPlugins.getInstance().getDataFolder(), "PlayerJoinTime.yml"));
//        System.out.println(Arrays.toString(configuration.getKeys(false).toArray()));
        configuration.getKeys(false).forEach(key -> {
            JTPlayer player = (JTPlayer) configuration.get(key);
            PlayerJoinTimeTask.map.put(player.uuid,player);
        });
    }
}
