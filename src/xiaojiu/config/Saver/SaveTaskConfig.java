package xiaojiu.config.Saver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xiaojiu.Handles.Save.SaveTaskManager;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuConfig;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.config.Savecfg.SaveTask;
import xiaojiu.task.PlayerJoinTimeTask;

import java.io.File;
import java.io.IOException;

public class SaveTaskConfig implements XiaojiuConfig {
    @Override
    public void Save() {
        File file = new File(StartPlugins.getInstance().getDataFolder(), "SaveTask.yml");
        FileConfiguration SaveTaskFile = YamlConfiguration.loadConfiguration(file);
        int i=0;
        for (SaveTask task : SaveTaskManager.getInstance().getSaveList()) {
            SaveTaskFile.set(task.getPlayer().getName()+ i,task);
            i++;
        }
        try {
            SaveTaskFile.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ReLoad() {

    }

    @Override
    public void Load() {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(StartPlugins.getInstance().getDataFolder(), "SaveTask.yml"));
        configuration.getKeys(false).forEach(key -> {
            SaveTask task = (SaveTask) configuration.get(key);
            //todo
        });
    }
}
