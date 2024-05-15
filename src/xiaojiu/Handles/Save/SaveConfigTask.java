package xiaojiu.Handles.Save;


import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.config.ConfigManager;
import xiaojiu.config.SaveConfig;

import java.util.TimerTask;

public class SaveConfigTask extends BasicSaveHandles{

    public SaveConfigTask(int taskid, JavaPlugin plugin, OfflinePlayer player, String... args) {
        super(taskid, plugin,player,"config", args);
//        this.name="config";
        this.canAsynchronously=true;

    }

    @Override
    public void run() {
        if (!StartPlugins.isIsInitEd()) {
            if (isAsynchronouslyTask()) return;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (String arg : args) {
            if (ConfigManager.getConfigMap().containsKey(arg)) {
                ConfigManager.getConfigMap().get(arg).Save();
            } else {
                ConfigManager.getConfigMap().forEach((string, xiaojiuConfig) -> {
                    xiaojiuConfig.Save();
                });
            }
        }


//        SaveConfig.Save();
    }

}
