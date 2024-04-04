package xiaojiu.task;

import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;

import java.util.ArrayList;

public class TaskLoader {
    public static void Start(JavaPlugin plugin){
        TpsTask.RunTask(plugin);
    }

}
