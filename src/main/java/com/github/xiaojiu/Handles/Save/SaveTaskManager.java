package com.github.xiaojiu.Handles.Save;

import com.github.xiaojiu.config.Savecfg.SaveTask;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveTaskManager {
    private static final SaveTaskManager manager = new SaveTaskManager();
    protected final List<BasicSaveHandles> taskList = new ArrayList<>();
    protected final Map<String, String[]> TaskNameMap = new HashMap<>();
    private final List<SaveTask> saveList = new ArrayList<>();

    public SaveTaskManager() {
        addTaskName("SaveConfigTask", "Config", "config");
        addTaskName("SavePlayerTask", "Player", "player");
        addTaskName("SaveWorldTask", "World", "world");
    }

    public static SaveTaskManager getInstance() {
        return manager;
    }

    public boolean isTasked(BasicSaveHandles task) {
        return taskList.contains(task);
    }

    public List<BasicSaveHandles> getTaskList() {
        return taskList;
    }

    public void addTask(BasicSaveHandles task) {
        taskList.add(task);
    }

    public List<SaveTask> getSaveList() {
        return saveList;
    }

    public void addRecordTask(BasicSaveHandles task) {
        saveList.add(new SaveTask(task.getName(), task.getTimerTime(), task.getDelay(), task.getPlayer().getUniqueId(), task.isAsynchronouslyTask(), task.getArgs(), task.getCreateDate()));
    }

    public void addTaskName(String name, String... names) {
        this.TaskNameMap.put(name, names);
    }

    public BasicSaveHandles getTask(int taskid) {
        if (taskid > taskList.size()) return null;
        return taskList.get(taskid - 1);
    }

    public String getTaskName(String name) {
        for (Map.Entry<String, String[]> entry : TaskNameMap.entrySet()) {
            for (String string : entry.getValue()) {
                if (name.contains(string) || name.equalsIgnoreCase(entry.getKey())) return entry.getKey();
            }
        }
        return null;
    }

    public BasicSaveHandles newTaskInstance(String taskName, JavaPlugin plugin, OfflinePlayer player, String... args) {
        try {
            return (BasicSaveHandles) Class.forName("com.github.xiaojiu.xiaojiuMain.Handles.Save." + taskName).getConstructors()[0].newInstance(this.taskList.size() + 1, plugin, player, args);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            return null;
        }

    }
}
