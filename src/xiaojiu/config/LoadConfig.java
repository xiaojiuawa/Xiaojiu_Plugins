package xiaojiu.config;

import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.tools.LimitPlayerTools;

public class LoadConfig {
    public static void ReadAllFile() {
        LimitPlayerTools.ReadPlayers();
        PlayerJoinTimeTask.ReadTime();
    }
}
