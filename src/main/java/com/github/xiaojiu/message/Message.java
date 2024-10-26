package com.github.xiaojiu.message;

import com.yuhai.util.UtilsBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private final JavaPlugin plugin;
    private final Map<String, String> messageMap;

    public Message(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messageMap = LoadMap();
    }

    private Map<String, String> LoadMap() {
        Map<String, String> map = new HashMap<>();
//        File file = new File(plugin.getDataFolder(),"message.yml");
        FileConfiguration configuration = UtilsBukkit.getConfig(plugin, "message.yml");
        for (String key : configuration.getKeys(true)) {
            map.put(key, configuration.getString(key));
        }
        return map;
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    /**
     * 使用完全语言节点获取语言，如果未找到，则返回语言节点
     * @param key 语言节点
     * @return 语言
     */
    public String getMessage(String key) {
        if (messageMap.get(key) != null) {
            return messageMap.get(key);
        }
//        return messageMap.get(key);
        return key;
    }


}
