package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.Restart.ReloadTask;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReloadTaskCommand implements XiaojiuCommandExecutor {
    public static final Map<String, HelpMap> ReloadTaskMap = new HashMap<>();
    public static final String PermissionNode = "ReloadTask";
    public static final String CommonNode = "suggest";

    @Override
    public void InitMap() {
        ReloadTaskMap.put("start", new HelpMapHandler(CommonNode, "/sug start", "xiaojiu.normal.ReloadTask.start", "通过这个指令发起一次投票重启"));
        ReloadTaskMap.put("revoke", new HelpMapHandler(CommonNode, "/sug revoke", "xiaojiu.normal.ReloadTask.revoke", "通过这个指令撤销你的投票"));
        ReloadTaskMap.put("agree", new HelpMapHandler(CommonNode, "/sug agree", "xiaojiu.normal.ReloadTask.agree", "通过这个指令同意当前的投票任务"));
        ReloadTaskMap.put("refuse", new HelpMapHandler(CommonNode, "/sug refuse", "xiaojiu.normal.ReloadTask.refuse", "通过这个指令拒绝当前的投票任务"));
        ReloadTaskMap.put("cancel", new HelpMapHandler(CommonNode, "/sug cancel", "xiaojiu.op.ReloadTask.cancel", "通过这个指令取消当前的投票任务"));
        ReloadTaskMap.put("down", new HelpMapHandler(CommonNode, "/sug down", "xiaojiu.op.ReloadTask.down", "通过这个指令立刻结束当前的投票任务，并进行结算"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return ReloadTaskMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommonNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length != 0) {
            if (strings[0].equalsIgnoreCase("start")) {
                if (PermissionHelper.isHasPermission(commandSender, false, PermissionNode, "start")) {
                    if (ReloadTask.isSuggesting) {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.start.have")));
                    } else {
                        ReloadTask.RunTask(Xiaojiu.getInstance(), ((Player) commandSender).getUniqueId());
                    }
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.noPermission")));
                }
            } else if (ReloadTask.isSuggesting) {
                if (strings[0].equalsIgnoreCase("revoke") || strings[0].equalsIgnoreCase("撤销")) {
                    if (PermissionHelper.isHasPermission(commandSender, false, PermissionNode, "revoke")) {
                        if (!ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.noVote")));
                        } else {
                            ReloadTask.suggestHelper.delSuggest(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.revoke.success")));
                        }
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.noPermission")));
                    }
                } else if (strings[0].equalsIgnoreCase("agree") || strings[0].equalsIgnoreCase("同意")) {
                    if (PermissionHelper.isHasPermission(commandSender, false, PermissionNode, "agree")) {
                        if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.voted")));
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.revoke.remind")));
                        } else {
                            ReloadTask.suggestHelper.Approve.add(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.agree.success")));
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.noPermission"));
                    }
                } else if (strings[0].equalsIgnoreCase("refuse") || strings[0].equalsIgnoreCase("拒绝")) {
                    if (PermissionHelper.isHasPermission(commandSender, false, PermissionNode, "refuse")) {
                        if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.voted")));
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.revoke.remind")));
                        } else {
                            ReloadTask.suggestHelper.Refuse.add(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("ReloadTask.suggest.refuse.success")));
                        }
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.noPermission")));
                    }
                } else if (strings[0].equalsIgnoreCase("cancel") || strings[0].equalsIgnoreCase("取消")) {
                    if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "cancel") || ReloadTask.suggestHelper.sponsor.equals(((Player) commandSender).getUniqueId())) {
                        ReloadTask.cancel();
                        PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("ReloadTask.cancel.success"), ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE)));
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.cancel.noPermission")));
                    }
                } else if (strings[0].equalsIgnoreCase("down")) {
                    if (PermissionHelper.isHasPermission(commandSender, true, "ReloadTask", "down")) {
                        ReloadTask.down();
                        PostHelper.SendMessageAllPlayer(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("ReloadTask.down.success"), ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE)));
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.down.noPermission")));
                    }
                }
            } else {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("ReloadTask.noSuggest")));
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            for (Map.Entry<String, HelpMap> entry : ReloadTaskMap.entrySet()) {
                if (commandSender.hasPermission(entry.getValue().getPermissionNode()) && entry.getKey().startsWith(strings[0].toLowerCase()))
                    list.add(entry.getKey());
            }
        }
        return list;
    }
}
