package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.Restart.RestartTools;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.xiaojiu.Handles.Restart.RestartTools.ProcessingTime;
import static com.github.xiaojiu.tools.Utils.isNumber;

public class RestartServerCommand implements XiaojiuCommandExecutor {
    public static final String PermissionCommonNode = "restart";
    public static final String CommonNode = "restartserver";
    public static final Map<String, HelpMap> RestartMap = new HashMap<>();

    @Override
    public void InitMap() {
        RestartMap.put("cancel", new HelpMapHandler(CommonNode, "/rest cancel", "xiaojiu.op.restart.cancel", "通过这个指令取消当前的计划重启任务"));
        RestartMap.put("now", new HelpMapHandler(CommonNode, "/rest now", "xiaojiu.op.restart.now", "通过这个指令立刻执行重启"));
        RestartMap.put("reset", new HelpMapHandler(CommonNode, "/rest reset [时间]", "xiaojiu.op.restart.reset", "通过这个指令重新设置重启时间"));
        RestartMap.put("", new HelpMapHandler(CommonNode, "/rest [时间]", "xiaojiu.op.restart.start", "通过这个指令发起一个重启任务"));
        RestartMap.put("m", new HelpMapHandler(CommonNode, "/rest m/h/d [时间]", "xiaojiu.op.restart.start", "通过这个指令发起一个重启任务(使用重设时间单位)，其中m表示天,h表示小时,m表示分钟"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return RestartMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionCommonNode;
    }

    @Override
    public String GetCommandNode() {
        return CommonNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings[0].equalsIgnoreCase("cancel")) {
            if (PermissionHelper.isHasPermission(commandSender, true, PermissionCommonNode, "cancel")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("ReloadServer.cancel.success"), ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE)));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.noTask")));
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
            }
        } else if (strings[0].equalsIgnoreCase("now")) {
            if (PermissionHelper.isHasPermission(commandSender, true, PermissionCommonNode, "now")) {
                if (RestartTools.isRestart) {
                    RestartTools.Done();
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.noTask")));
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
            }
        } else if (strings[0].equalsIgnoreCase("reset")) {
            if (PermissionHelper.isHasPermission(commandSender, true, PermissionCommonNode, "reset")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    RestartTools.Restart(Integer.parseInt(strings[1]));
                    PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("ReloadServer.reset.success"), ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE, ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE)));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.noTask")));
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
            }
        } else if (isNumber(strings[0])) {
            int num = Integer.parseInt(strings[0]);
            if (PermissionHelper.isHasPermission(commandSender, true, PermissionCommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    RestartTools.Restart(num);
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.start.success")));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.start.have")));
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
            }
        } else if (strings.length == 2) {
            int num = ProcessingTime(strings[0], Integer.parseInt(strings[1]));
            if (PermissionHelper.isHasPermission(commandSender, true, PermissionCommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    if (num == -1) {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("Command.paramWrong")));
                        return true;
                    }
                    RestartTools.Restart(num);
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.start.success")));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadServer.start.have")));
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
            }
        }
        return true;

//        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            for (Map.Entry<String, HelpMap> entry : RestartMap.entrySet()) {
                if (commandSender.hasPermission(entry.getValue().getPermissionNode()) && entry.getKey().startsWith(strings[0].toLowerCase())) {
                    list.add(entry.getKey());
                }
            }

        }
        return list;
    }
}
