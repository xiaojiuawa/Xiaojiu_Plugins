package com.github.xiaojiu.Handles.Save;


import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.config.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveConfigTask extends BasicSaveHandles {

    public SaveConfigTask(int taskid, JavaPlugin plugin, OfflinePlayer player, String... args) {
        super(taskid, plugin, player, "config", args);
//        this.name="config";
        this.canAsynchronously = true;

    }

    @Override
    public void run() {
        try {
            if (!Xiaojiu.isIsInitEd()) {
                if (isAsynchronouslyTask()) {

                    return;
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
                ConfigManager.SaveConfig();
            } else {
                for (String arg : args) {
                    if (ConfigManager.getConfigMap().containsKey(arg)) {
                        ConfigManager.getConfigMap().get(arg).Save();
                    } else {
                        ConfigManager.getConfigMap().forEach((string, xiaojiuConfig) -> {
                            xiaojiuConfig.Save();
                        });
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


//        SaveConfig.Save();
    }

}
