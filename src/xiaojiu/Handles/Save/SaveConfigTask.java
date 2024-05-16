package xiaojiu.Handles.Save;


import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.config.ConfigManager;

public class SaveConfigTask extends BasicSaveHandles {

    public SaveConfigTask(int taskid, JavaPlugin plugin, OfflinePlayer player, String... args) {
        super(taskid, plugin, player, "config", args);
//        this.name="config";
        this.canAsynchronously = true;

    }

    @Override
    public void run() {
        try {
            if (!StartPlugins.isIsInitEd()) {
                if (isAsynchronouslyTask()) {

                    return;
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (args == null || args.length == 0 || args[0].isEmpty()) {
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
