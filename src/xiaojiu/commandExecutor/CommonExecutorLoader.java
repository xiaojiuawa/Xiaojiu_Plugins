package xiaojiu.commandExecutor;

import xiaojiu.StartPlugins;

public class CommonExecutorLoader {
    public static PlayerCommand playerCommand = new PlayerCommand();
    public static RestartServerCommand restartServerCommand = new RestartServerCommand();
    public static SafeGuardCommand safeGuardCommand = new SafeGuardCommand();
    public static ReloadTaskCommand reloadTaskCommand = new ReloadTaskCommand();
    public static PlayerTimeCommand playerTimeCommand = new PlayerTimeCommand();
    public static MainCommand mainCommand = new MainCommand();

    public static void Load(StartPlugins Instance) {
        Instance.getCommand("xiaojiu").setExecutor(mainCommand);
        Instance.getCommand("restartserver").setExecutor(restartServerCommand);
        Instance.getCommand("restartserver").setTabCompleter(restartServerCommand);
        Instance.getCommand("safeguard").setExecutor(safeGuardCommand);
        Instance.getCommand("safeguard").setTabCompleter(safeGuardCommand);
        Instance.getCommand("Limit").setExecutor(playerCommand);
        Instance.getCommand("Limit").setTabCompleter(playerCommand);
        Instance.getCommand("suggest").setExecutor(reloadTaskCommand);
        Instance.getCommand("suggest").setTabCompleter(reloadTaskCommand);
        Instance.getCommand("playerTime").setExecutor(playerTimeCommand);
        Instance.getCommand("playerTime").setTabCompleter(playerTimeCommand);
    }
}
