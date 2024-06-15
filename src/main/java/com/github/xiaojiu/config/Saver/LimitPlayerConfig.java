package com.github.xiaojiu.config.Saver;

import com.github.xiaojiu.Handles.PlayerLimit.PlayerLimitTools;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.XiaojiuConfig;
import com.github.xiaojiu.config.Savecfg.LimitPlayer;
import com.yuhai.util.UtilsBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LimitPlayerConfig implements XiaojiuConfig {

    @Override
    public void Save() {
        File file = new File(Xiaojiu.getInstance().getDataFolder(), "PlayerLimit.yml");
        FileConfiguration LimitPlayer = YamlConfiguration.loadConfiguration(file);
        for (LimitPlayer player : PlayerLimitTools.list) {
            LimitPlayer.set(player.Name, player);
        }
        try {
            LimitPlayer.save(file);
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
        FileConfiguration configuration = UtilsBukkit.getConfig(Xiaojiu.getInstance(), "PlayerLimit.yml");
        configuration.getKeys(false).forEach(key -> {
            LimitPlayer player = (LimitPlayer) configuration.get(key);
            UUID uuid = new UUID(player.mostSigBits, player.leastSigBits);
            PlayerLimitTools.hashMap.put(uuid, player);

        });
    }
}
