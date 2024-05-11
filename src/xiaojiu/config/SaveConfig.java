package xiaojiu.config;

import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.api.XiaojiuConfig;
import xiaojiu.config.Saver.LimitPlayerConfig;
import xiaojiu.config.Saver.PlayerTimeConfig;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class SaveConfig {
    public static JavaPlugin Instance;
    public static Map<String, XiaojiuConfig> configMap=new HashMap<>();

    @Deprecated
    public static void OnEnable(JavaPlugin plugin) {
        Instance = plugin;
        plugin.saveDefaultConfig();
        configMap.put("LimitPlayer",new LimitPlayerConfig());
        configMap.put("PlayerTime",new PlayerTimeConfig());
    }
    @Deprecated
    public static void Save() {
        configMap.forEach((string, xiaojiuConfig) ->{
            xiaojiuConfig.Save();
        });
    }


}
