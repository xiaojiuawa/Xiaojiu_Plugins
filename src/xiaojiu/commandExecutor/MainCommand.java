package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import xiaojiu.Handles.Help.HelpCommand;
import xiaojiu.Handles.Help.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Utils;

import java.util.*;

public class MainCommand implements XiaojiuCommandExecutor {
    public static String CommonNode = "xiaojiu";

    public static Map<String, Map<String, HelpMap>> helpMap = new HashMap<>();

    @Override
    public void InitMap() {
        Map<String, HelpMap> MainHelpMap = new HashMap<>();
        // help命令
        MainHelpMap.put("help", new HelpMap(CommonNode, "/xj help [命令大节点] [页面(默认为1)]", "xiaojiu.main.help.use", "通过这个指令查看命令帮助 当你输入这个指令后，会有一个总列表出现，这时再输入总列表当作页码即可"));
//        MainHelpMap.put("")
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
        if (strings.length > 0) {
            if (strings[0].equalsIgnoreCase("help")) {
                //帮助指令开始
                if (strings.length == 1) {
                    //主帮助信息
                    HelpCommand.SendHelps(commandSender, "", 0);
                } else if (strings.length > 2 && Utils.isNumber(strings[2])) {
                    if (strings[1].equalsIgnoreCase("玩家限制")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("玩家上线时间")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("投票重启")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("重启主模块")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("服务器维护命令")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "请输入正确的命令"));
                    }
                } else if (strings.length == 2) {
                    HelpCommand.SendHelps(commandSender, strings[1], 1);
                }
                //帮助指令结束
            } else if (strings[0].equalsIgnoreCase("v") || strings[0].equalsIgnoreCase("隐身") || strings[0].equalsIgnoreCase("vanish")) {
                //隐身指令开始
//                for (String string : strings) {
//                    commandSender.sendMessage(strings);
//                }
//                commandSender.sendMessage(Arrays.copyOfRange(strings,1,strings.length));
                CommonExecutorLoader.GetCommandMap().get("vanish").onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            HelpCommand.helpMap.forEach((string, helpMapMap) -> {
                if (string.startsWith(strings[0].toLowerCase())) list.add(string);
            });
        } else if (strings[0].equalsIgnoreCase("v")) {
            CommonExecutorLoader.GetCommandMap().get("vanish").onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
//            list.addAll(VanishCommand.onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length)));
        }
        return list;
    }
}
