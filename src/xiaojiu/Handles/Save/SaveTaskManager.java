package xiaojiu.Handles.Save;

import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.api.XiaojiuTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveTaskManager{
    public static SaveTaskManager manager = new SaveTaskManager();
    public static SaveTaskManager getInstance(){
        return manager;
    }
    protected final ArrayList<BasicSaveHandles> taskList = new ArrayList<>();

    public int addTask(BasicSaveHandles task){
        taskList.add(task);
        return taskList.size();
    }

    public BasicSaveHandles GetTask(int taskid){
        if (taskid>taskList.size()) return null;
        return taskList.get(taskid);
    }

    public String GetTaskName(String name){
        return "";
    }

    public BasicSaveHandles NewTask(String taskName, JavaPlugin plugin, String... args){
        try{
            return (BasicSaveHandles) Class.forName("xiaojiu.Handles.Save."+taskName).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }

    }
}
