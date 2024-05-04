package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xiaojiu.Handles.Help.HelpCommandHandle;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Utils;

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
        if (strings.length>=1){
            if (strings.length==1) HelpCommandHandle.SendHelps(commandSender, "", 0);
            if (strings.length>2&& Utils.isNumber(strings[1])){
                HelpCommandHandle.SendHelps(commandSender, strings[0], Integer.parseInt(strings[1]));
            }else if (strings.length==2){
                HelpCommandHandle.SendHelps(commandSender,strings[0],1);
            }
        }else{
            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE+"请输入正确的命令参数"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        return null;
    }
}
