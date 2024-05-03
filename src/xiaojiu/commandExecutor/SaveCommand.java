package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xiaojiu.Handles.Help.HelpMapHandler;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;
import xiaojiu.tools.MessageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveCommand implements XiaojiuCommandExecutor {

    public static Map<String,HelpMap> SaveMap = new HashMap<>();

    public static String PermissionNode = "save";

    public static String CommandNode = "save";
    @Override
    public void InitMap() {
        SaveMap.put("config",new HelpMapHandler(CommandNode,"/xj save config [true/false]{是否异步}","xiaojiu.op.save.config","通过这个来立即保存所有可以保存的配置文件"));
        SaveMap.put("player",new HelpMapHandler(CommandNode,"/xj save player [true/false]{是否异步} (playername)","xiaojiu.op.save.player","通过这个指令来立刻存储所有玩家或某个的文件"));
        SaveMap.put("world",new HelpMapHandler(CommandNode,"/xj save world [true/false]{是否异步} (worldid)","xiaojiu.op.save.world","通过这个指令来保存所有世界或是某个世界"));
        SaveMap.put("task",new HelpMapHandler(CommandNode,"/xj save task [taskType](任务类型) (Commandstrings) [true/false](是否为临时任务) [true/false](是否异步) (时间单位) [时间数值]","xiaojiu.op.save.task","通过这个指令来对某个保存指令创建一个自动任务"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return SaveMap;
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
        if (strings.length<=0){
            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE+"请输入正确的命令格式"));
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
