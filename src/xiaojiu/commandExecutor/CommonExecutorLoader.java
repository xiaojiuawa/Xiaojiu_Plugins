package xiaojiu.commandExecutor;

import xiaojiu.StartPlugins;

public class CommonExecutorLoader {
    public static PlayerCommand playerCommand = new PlayerCommand();
    public static RestartServerCommand restartServerCommand = new RestartServerCommand();
    public static SafeGuardCommand safeGuardCommand = new SafeGuardCommand();
    public static ReloadTaskCommand reloadTaskCommand = new ReloadTaskCommand();
    public static PlayerTimeCommand playerTimeCommand = new PlayerTimeCommand();

    public static void Load(StartPlugins Instance) {
        Instance.getCommand("restartserver").setExecutor(restartServerCommand);
        Instance.getCommand("safeguard").setExecutor(safeGuardCommand);
        Instance.getCommand("Limit").setExecutor(playerCommand);
        Instance.getCommand("Limit").setTabCompleter(playerCommand);
        Instance.getCommand("suggest").setExecutor(reloadTaskCommand);
        Instance.getCommand("playerTime").setExecutor(playerTimeCommand);
    }
}
