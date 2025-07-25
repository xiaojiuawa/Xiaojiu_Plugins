package com.github.xiaojiu.config.Saver;

import com.github.xiaojiu.Handles.Save.BasicSaveHandles;
import com.github.xiaojiu.Handles.Save.SaveTaskManager;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.XiaojiuConfig;
import com.github.xiaojiu.config.Savecfg.SaveTask;
import com.yuhai.util.UtilsBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SaveTaskConfig implements XiaojiuConfig {
    @Override
    public void Save() {
        File file = new File(Xiaojiu.getInstance().getDataFolder(), "SaveTask.yml");
        FileConfiguration SaveTaskFile = YamlConfiguration.loadConfiguration(file);
        int i = 0;
        for (SaveTask task : SaveTaskManager.getInstance().getSaveList()) {
            SaveTaskFile.set(task.getPlayer().getName() + i, task);
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
//        StartPlugins.logger.info("123434");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(Xiaojiu.getInstance().getDataFolder(), "SaveTask.yml"));
//        System.out.println(Arrays.toString(configuration.getKeys(false).toArray()));
        for (String key : configuration.getKeys(false)) {
            SaveTask task = (SaveTask) configuration.get(key);
//            StartPlugins.logger.info(key);
            Xiaojiu.logger.info(task.getName());
            BasicSaveHandles task2 = SaveTaskManager.getInstance().newTaskInstance(SaveTaskManager.getInstance().getTaskName(task.getName()), Xiaojiu.getInstance(), task.getPlayer(), task.getArgs());
            if (SaveTaskManager.getInstance().isTasked(task2)) return;
            if (task.isAsynchronously()) {
                task2.RunTaskAsynchronously(task.getTimer(), task.getDelay());
            } else {
                task2.RunTask(task.getTimer(), task.getDelay());
            }
            SaveTaskManager.getInstance().addTask(task2);
            SaveTaskManager.getInstance().addRecordTask(task2);
        }
        //todo
    }

}
