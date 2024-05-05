package xiaojiu.commandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xiaojiu.Handles.Help.HelpMapHandler;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;

import java.util.*;

public class MainCommand implements XiaojiuCommandExecutor {
    public static String CommonNode = "xiaojiu";

    public static String PermissionNode = "xiaojiu";
    public static Map<String, Map<String, HelpMap>> helpMap = new HashMap<>();

    @Override
    public void InitMap() {
        Map<String, HelpMap> MainMap = new HashMap<>();
        // help命令
        MainMap.put("help", new HelpMapHandler(CommonNode, "/xj help [命令大节点] [页面(默认为1)]", "xiaojiu.main.help.use", "通过这个指令查看命令帮助 当你输入这个指令后，会有一个总列表出现，这时再输入总列表当作页码即可"));
//        MainMap.put("")
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return null;
    }

    @Override
    public String GetPermissionNode() {
        return null;
    }

    @Override
    public String GetCommandNode() {
        return CommonNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(strings);
        if (strings.length >= 1) {
            if (strings[0].equalsIgnoreCase("help")) {
                //帮助指令开始
                CommonExecutorLoader.GetCommandMap().get("help").onCommand(commandSender, command, s, Arrays.copyOfRange(strings,1,strings.length));
                }
                //帮助指令结束
            } else if (strings[0].equalsIgnoreCase("v") || strings[0].equalsIgnoreCase("隐身") || strings[0].equalsIgnoreCase("vanish")) {
                //隐身指令开始
//                for (String string : strings) {
//                    commandSender.sendMessage(strings);
//                }
//                commandSender.sendMessage(Arrays.copyOfRange(strings,1,strings.length));
                CommonExecutorLoader.GetCommandMap().get("vanish").onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            }else{
            CommonExecutorLoader.GetCommandMap().get("save").onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list=new ArrayList<>();
        if (strings.length==1){
            CommonExecutorLoader.GetCommandMap().forEach((string, executor) -> {
                if (strings[0].toLowerCase().startsWith(string)){
                    list.add(string);
                }
            });
        } else if (strings.length==2) {
            XiaojiuCommandExecutor executor = CommonExecutorLoader.GetCommandMap().get(strings[0]);
            list.addAll(executor.onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings,0,strings.length)));
//            list.addAll(CommonExecutorLoader.GetCommandMap().get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings,0,strings.length)));
        }
        return list;
    }
}
