package xiaojiu.config.Saver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xiaojiu.Handles.Save.BasicSaveHandles;
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
        this.Save();
        this.Load();
    }

    @Override
    public void Load() {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(StartPlugins.getInstance().getDataFolder(), "SaveTask.yml"));
        configuration.getKeys(false).forEach(key -> {
            SaveTask task = (SaveTask) configuration.get(key);
            BasicSaveHandles task2 = SaveTaskManager.getInstance().NewTaskInstance(SaveTaskManager.getInstance().GetTaskName(task.getName()),StartPlugins.getInstance(), task.getPlayer(), task.getArgs() );
            if (SaveTaskManager.getInstance().isTasked(task2)) return;
            if (task.isAsynchronously()){
                task2.RunTaskAsynchronously(task.getTimer(),task.getDelay());
            }else{
                task2.RunTask(task.getTimer(),task.getDelay());
            }
            SaveTaskManager.getInstance().addTask(task2);
            SaveTaskManager.getInstance().addRecordTask(task2);
        });
    }
}
