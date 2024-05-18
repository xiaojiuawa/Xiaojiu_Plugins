package xiaojiu.Handles.Help;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xiaojiu.api.HelpMap;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.tools.MessageHelper;

import java.util.HashMap;
import java.util.Map;

public class HelpCommandHandle {
    public static final Map<String, Map<String, HelpMap>> helpMap = new HashMap<>();

    public static void HelpMapInit() {
        CommonExecutorLoader.GetCommandMap().forEach((string, xiaojiuCommandExecutor) -> {
            xiaojiuCommandExecutor.InitMap();
            helpMap.put(xiaojiuCommandExecutor.GetCommandNode(), xiaojiuCommandExecutor.GetHelpMap());
        });
//        helpMap.putAll(MainCommand.helpMap);
////        CommonExecutorLoader.playerCommand.InitMap();
//        helpMap.put("玩家限制", PlayerCommand.PlayerCommandMap);
////        PlayerTimeCommand.InitMap();
//        helpMap.put("玩家上线时间", PlayerTimeCommand.PlayerTimeMap);
////        ReloadTaskCommand.InitMap();
//        helpMap.put("投票重启", ReloadTaskCommand.ReloadTaskMap);
////        RestartServerCommand.InitMap();
//        helpMap.put("重启主模块", RestartServerCommand.RestartMap);
////        SafeGuardCommand.InitMap();
//        helpMap.put("服务器维护命令", SafeGuardCommand.SafeGuardMap);
////        VanishCommand.InitMap();
//        helpMap.put("隐身", VanishCommand.vanishMap);
    }

    public static void SendHelps(CommandSender commandSender, String command, int page) {
        commandSender.sendMessage(ChatColor.GOLD + "xiaojiu 命令帮助");
        if (page == 0 && command.equalsIgnoreCase("")) {
            commandSender.sendMessage(ChatColor.GOLD + "帮助总列表");
            helpMap.forEach((string, helpmap) -> commandSender.sendMessage(ChatColor.GOLD + string));
        } else {
            if (helpMap.containsKey(command)) {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入正确的命令节点"));
                return;
            }

            Map<String, HelpMap> getMap = helpMap.get(command);
            Map<String, HelpMap> map = new HashMap<>();
//            if (page==0) page=1;
            if (commandSender.hasPermission("xiaojiu.op.help.high")) {
                map = getMap;
            } else {
                for (Map.Entry<String, HelpMap> entry : getMap.entrySet()) {
                    if (commandSender.hasPermission(entry.getValue().getPermissionNode())) {
                        map.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            page--;
            int i = 0;
            for (Map.Entry<String, HelpMap> entry : map.entrySet()) {
//                commandSender.sendMessage(ChatColor.GOLD+String.valueOf(page*5)+" "+ i);
                if (i >= page * 5 && i < (page + 1) * 5) {
                    HelpMap map1 = entry.getValue();
                    commandSender.sendMessage(ChatColor.GOLD + "命令名: " + entry.getKey());
                    commandSender.sendMessage(ChatColor.GOLD + "使用方法: " + map1.getCommand());
                    commandSender.sendMessage(ChatColor.GOLD + "解释: " + map1.getIntroduce());
                    if (commandSender.hasPermission("xiaojiu.op.help.high")) {
                        commandSender.sendMessage(ChatColor.GOLD + "需要的权限节点" + map1.getPermissionNode());
                        commandSender.sendMessage(ChatColor.GOLD + "我是否有权限:" + (commandSender.hasPermission(map1.getPermissionNode()) || commandSender.isOp() ? "有" : "无"));
                    }
                    commandSender.sendMessage(ChatColor.GOLD + "=====================================");
                }
                i++;
            }
//            commandSender.sendMessage(ChatColor.GOLD+"=========================");
            commandSender.sendMessage(ChatColor.GOLD + "第" + (page + 1) + "页" + " " + "共" + ((map.size() / 5) + 1) + "页");
        }
    }
}
