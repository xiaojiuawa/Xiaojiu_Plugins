package xiaojiu.commandExecutor;

import xiaojiu.StartPlugins;

public class CommonExecutorLoader {
    public static void Load(StartPlugins Instance) {
        Instance.getCommand("restartserver").setExecutor(new RestartServerCommand());
        Instance.getCommand("safeguard").setExecutor(new SafeGuardCommand());
        Instance.getCommand("Limit").setExecutor(new PlayerCommand());
        Instance.getCommand("suggest").setExecutor(new SuggestCommand());
        Instance.getCommand("playerTime").setExecutor(new PlayerTimeCommand());
    }
}
