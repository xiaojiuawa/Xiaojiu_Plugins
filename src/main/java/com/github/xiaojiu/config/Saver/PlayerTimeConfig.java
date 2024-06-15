package com.github.xiaojiu.config.Saver;

import com.github.xiaojiu.Handles.PlayerTime.PlayerJoinTimeTool;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.XiaojiuConfig;
import com.github.xiaojiu.config.Savecfg.JTPlayer;
import com.yuhai.util.UtilsBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class PlayerTimeConfig implements XiaojiuConfig {
    @Override
    public void Save() {
        File file = new File(Xiaojiu.getInstance().getDataFolder(), "PlayerJoinTime.yml");
        FileConfiguration PlayerTime = YamlConfiguration.loadConfiguration(file);
        for (Map.Entry<UUID, JTPlayer> entry : PlayerJoinTimeTool.map.entrySet()) {
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
        FileConfiguration configuration = UtilsBukkit.getConfig(Xiaojiu.getInstance(), "PlayerJoinTime.yml");
//        System.out.println(Arrays.toString(configuration.getKeys(false).toArray()));
        configuration.getKeys(false).forEach(key -> {
            JTPlayer player = (JTPlayer) configuration.get(key);
            PlayerJoinTimeTool.map.put(player.uuid, player);
        });
    }
}
