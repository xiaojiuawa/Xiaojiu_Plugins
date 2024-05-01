package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xiaojiu.Handles.Help.HelpMap;
import xiaojiu.StartPlugins;
import xiaojiu.task.ReloadTask;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReloadTaskCommand implements TabExecutor {
    public static Map<String, HelpMap> ReloadTaskMap = new HashMap<>();
    public static String PermissionNode = "ReloadTask";
    public static String CommonNode = "sug";

    public static void InitMap() {
        ReloadTaskMap.put("start", new HelpMap(CommonNode, "/sug start", "xiaojiu.normal.ReloadTask.start", "通过这个指令发起一次投票重启"));
        ReloadTaskMap.put("revoke", new HelpMap(CommonNode, "/sug revoke", "xiaojiu.normal.ReloadTask.revoke", "通过这个指令撤销你的投票"));
        ReloadTaskMap.put("agree", new HelpMap(CommonNode, "/sug agree", "xiaojiu.normal.ReloadTask.agree", "通过这个指令同意当前的投票任务"));
        ReloadTaskMap.put("refuse", new HelpMap(CommonNode, "/sug refuse", "xiaojiu.normal.ReloadTask.refuse", "通过这个指令拒绝当前的投票任务"));
        ReloadTaskMap.put("cancel", new HelpMap(CommonNode, "/sug cancel", "xiaojiu.op.ReloadTask.cancel", "通过这个指令取消当前的投票任务"));
        ReloadTaskMap.put("down", new HelpMap(CommonNode, "/sug down", "xiaojiu.op.ReloadTask.down", "通过这个指令立刻结束当前的投票任务，并进行结算"));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length != 0) {
            if (strings[0].equalsIgnoreCase("start")) {
                if (PermissionHelper.isHasPermission(commandSender, "normal", PermissionNode, "start")) {
                    if (ReloadTask.isSuggesting) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已有投票任务存在,如果要取消,请使用/sug cancel 命令取消"));
                    } else {
                        ReloadTask.RunTask(StartPlugins.getInstance(), ((Player) commandSender).getUniqueId());
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限使用这个指令"));
                }
            } else if (ReloadTask.isSuggesting) {
                if (strings[0].equalsIgnoreCase("revoke") || strings[0].equalsIgnoreCase("撤销")) {
                    if (PermissionHelper.isHasPermission(commandSender, "normal", PermissionNode, "revoke")) {
                        if (!ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你还没有投票"));
                        } else {
                            ReloadTask.suggestHelper.delSuggest(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "撤销投票成功"));
                        }
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有使用这个指令的权限"));
                    }
                } else if (strings[0].equalsIgnoreCase("agree") || strings[0].equalsIgnoreCase("同意")) {
                    if (PermissionHelper.isHasPermission(commandSender, "normal", PermissionNode, "agree")) {
                        if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你已经投票"));
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请使用/sug revoke 或 /sug 撤销 撤销您的投票"));
                        } else {
                            ReloadTask.suggestHelper.Approve.add(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已同意投票"));
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "你没有使用这个指令的权限");
                    }
                } else if (strings[0].equalsIgnoreCase("refuse") || strings[0].equalsIgnoreCase("拒绝")) {
                    if (PermissionHelper.isHasPermission(commandSender, "normal", PermissionNode, "refuse")) {
                        if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你已经投票"));
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请使用/sug revoke 或 /sug 撤销 撤销您的投票"));
                        } else {
                            ReloadTask.suggestHelper.Refuse.add(((Player) commandSender).getUniqueId());
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已拒绝投票"));
                        }
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有使用这个指令的权限"));
                    }
                } else if (strings[0].equalsIgnoreCase("cancel") || strings[0].equalsIgnoreCase("取消")) {
                    if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "cancel") || ReloadTask.suggestHelper.sponsor.equals(((Player) commandSender).getUniqueId())) {
                        ReloadTask.cancel();
                        MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前投票任务已被玩家" + commandSender.getName() + "取消"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有取消当前投票任务的权限"));
                    }
                } else if (strings[0].equalsIgnoreCase("down")) {
                    if (PermissionHelper.isHasPermission(commandSender, "op", "ReloadTask", "down")) {
                        ReloadTask.down();
                        MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前投票任务已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "立刻结束"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有立刻结束当前投票的权限"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前无投票任务进行"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "没有正在进行的投票任务"));
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
                if (commandSender.hasPermission(entry.getValue().PermissionNode) && entry.getKey().startsWith(strings[0].toLowerCase()))
                    list.add(entry.getKey());
            }
        }
        return list;
    }
}
