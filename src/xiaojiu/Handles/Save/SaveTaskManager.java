package xiaojiu.Handles.Save;

import xiaojiu.api.XiaojiuTask;

import java.util.HashMap;
import java.util.Map;

public class SaveTaskManager{
    private static final Map<String,XiaojiuTask> taskMap=new HashMap<>();
    public static void InitManager(){
        addTask(new SaveConfigTask());
        addTask(new SaveWorldTask());
        addTask(new SavePlayerTask());
    }
    public static Map<String, XiaojiuTask> GetTaskMap() {
        return taskMap;
    }

    public static XiaojiuTask GetTask(String name) {
        if (taskMap.containsKey(name)){
            return taskMap.get(name);
        }
        return null;
    }

    public static void addTask(String name, XiaojiuTask task) {
        taskMap.put(name, task);
    }
    public static void addTask(XiaojiuTask task){
        taskMap.put(task.GetName(),task);
    }
}
