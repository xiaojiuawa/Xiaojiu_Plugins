package com.github.xiaojiu.xiaojiuMain.config.Saver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.github.xiaojiu.xiaojiuMain.Handles.LimitPlayer.LimitPlayerTools;
import com.github.xiaojiu.xiaojiuMain.Xiaojiu;
import com.github.xiaojiu.xiaojiuMain.api.XiaojiuConfig;
import com.github.xiaojiu.xiaojiuMain.config.Savecfg.LimitPlayer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LimitPlayerConfig implements XiaojiuConfig {

    @Override
    public void Save() {
        File file = new File(Xiaojiu.getInstance().getDataFolder(), "PlayerLimit.yml");
        FileConfiguration LimitPlayer = YamlConfiguration.loadConfiguration(file);
        for (com.github.xiaojiu.xiaojiuMain.config.Savecfg.LimitPlayer player : LimitPlayerTools.list) {
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
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(Xiaojiu.getInstance().getDataFolder(), "PlayerLimit.yml"));
        configuration.getKeys(false).forEach(key -> {
            LimitPlayer player = (LimitPlayer) configuration.get(key);
            UUID uuid = new UUID(player.mostSigBits, player.leastSigBits);
            LimitPlayerTools.hashMap.put(uuid, player);

        });
    }
}
