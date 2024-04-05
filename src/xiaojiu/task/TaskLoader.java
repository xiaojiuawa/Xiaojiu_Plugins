package xiaojiu.task;

import org.bukkit.plugin.java.JavaPlugin;

public class TaskLoader {
    public static void Start(JavaPlugin plugin) {
        TpsTask.RunTask(plugin);
    }

}
