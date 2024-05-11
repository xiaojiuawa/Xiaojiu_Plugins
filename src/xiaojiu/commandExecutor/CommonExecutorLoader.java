package xiaojiu.commandExecutor;

import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.api.XiaojiuCommandExecutor;


import java.util.HashMap;
import java.util.Map;

public class CommonExecutorLoader {
    public static Map<String, XiaojiuCommandExecutor> commandExecutorMaps = new HashMap<>();
    public static PlayerCommand playerCommand = new PlayerCommand();
    public static RestartServerCommand restartServerCommand = new RestartServerCommand();
    public static SafeGuardCommand safeGuardCommand = new SafeGuardCommand();
    public static ReloadTaskCommand reloadTaskCommand = new ReloadTaskCommand();
    public static PlayerTimeCommand playerTimeCommand = new PlayerTimeCommand();
    public static MainCommand mainCommand = new MainCommand();

    public static Map<String,XiaojiuCommandExecutor> GetCommandMap(){
        return commandExecutorMaps;
    }
    public static void Load(JavaPlugin Instance) {
        CommonExecutorLoader.commandExecutorMaps.put(mainCommand.GetCommandNode(),mainCommand);
        Instance.getCommand("xiaojiu").setExecutor(mainCommand);
        CommonExecutorLoader.commandExecutorMaps.put(restartServerCommand.GetCommandNode(), restartServerCommand);
        Instance.getCommand("restartserver").setExecutor(restartServerCommand);
        Instance.getCommand("restartserver").setTabCompleter(restartServerCommand);
        commandExecutorMaps.put(safeGuardCommand.GetCommandNode(), safeGuardCommand);
        Instance.getCommand("safeguard").setExecutor(safeGuardCommand);
        Instance.getCommand("safeguard").setTabCompleter(safeGuardCommand);
        commandExecutorMaps.put(playerCommand.GetCommandNode(), playerCommand);
        Instance.getCommand("Limit").setExecutor(playerCommand);
        Instance.getCommand("Limit").setTabCompleter(playerCommand);
        commandExecutorMaps.put(reloadTaskCommand.GetCommandNode(), reloadTaskCommand);
        Instance.getCommand("suggest").setExecutor(reloadTaskCommand);
        Instance.getCommand("suggest").setTabCompleter(reloadTaskCommand);
        commandExecutorMaps.put(playerTimeCommand.GetCommandNode(), playerTimeCommand);
        Instance.getCommand("playerTime").setExecutor(playerTimeCommand);
        Instance.getCommand("playerTime").setTabCompleter(playerTimeCommand);
        commandExecutorMaps.put("vanish",new VanishCommand());
        commandExecutorMaps.put("save",new SaveCommand());
        commandExecutorMaps.put("help",new HelpCommand());
        commandExecutorMaps.put("no",new NoCommandCMD());
    }
    public static XiaojiuCommandExecutor GetExecutor(String name){
        if (!commandExecutorMaps.containsKey(name)) return commandExecutorMaps.get("no");
        return commandExecutorMaps.get(name);
    }
}
