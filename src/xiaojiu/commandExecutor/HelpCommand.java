package xiaojiu.commandExecutor;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand {
    public static Map<String,Map<String,HelpMap>> helpMap = new HashMap<>();
    public static void HelpMapInit(){
        PlayerCommand.InitMap();
        helpMap.put("pl",PlayerCommand.PlayerCommandMap);
        PlayerTimeCommand.InitMap();
        helpMap.put("pt",PlayerTimeCommand.PlayerTimeMap);
        ReloadTaskCommand.InitMap();
        helpMap.put("sug",ReloadTaskCommand.ReloadTaskMap);
        RestartServerCommand.InitMap();
        helpMap.put("rest",RestartServerCommand.RestartMap);
        SafeGuardCommand.InitMap();
        helpMap.put("sg",SafeGuardCommand.SafeGuardMap);
    }

}
