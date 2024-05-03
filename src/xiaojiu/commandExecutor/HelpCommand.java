package xiaojiu.commandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpCommand implements XiaojiuCommandExecutor {

    public static Map<String,HelpMap> helpMap = new HashMap<>();

    public static String PermissionNode = "help";

    public static String CommandNode  ="help";

    @Override
    public void InitMap() {

    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return helpMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommandNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
