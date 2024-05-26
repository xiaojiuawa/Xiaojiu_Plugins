package com.github.xiaojiu.xiaojiuMain.config;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.xiaojiu.xiaojiuMain.api.XiaojiuConfig;
import com.github.xiaojiu.xiaojiuMain.config.Savecfg.JTPlayer;
import com.github.xiaojiu.xiaojiuMain.config.Savecfg.LimitPlayer;
import com.github.xiaojiu.xiaojiuMain.config.Savecfg.SaveTask;
import com.github.xiaojiu.xiaojiuMain.config.Saver.LimitPlayerConfig;
import com.github.xiaojiu.xiaojiuMain.config.Saver.PlayerTimeConfig;
import com.github.xiaojiu.xiaojiuMain.config.Saver.SaveTaskConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static final Map<String, XiaojiuConfig> configMap = new HashMap<>();

    public static void InitManager(JavaPlugin plugin) {
        ConfigurationSerialization.registerClass(LimitPlayer.class);
        ConfigurationSerialization.registerClass(JTPlayer.class);
        ConfigurationSerialization.registerClass(SaveTask.class);
        InitMap();
        LoadConfig();
    }

    public static void InitMap() {
        configMap.put("LimitPlayer", new LimitPlayerConfig());
        configMap.put("PlayerTime", new PlayerTimeConfig());
        configMap.put("SaveTask", new SaveTaskConfig());
    }

    public static void LoadConfig() {
        configMap.forEach((string, xiaojiuConfig) -> {
            xiaojiuConfig.Load();
        });
    }

    public static void SaveConfig() {
        configMap.forEach((string, xiaojiuConfig) -> {
            xiaojiuConfig.Save();
        });
    }

    public static Map<String, XiaojiuConfig> getConfigMap() {
        return configMap;
    }
}
