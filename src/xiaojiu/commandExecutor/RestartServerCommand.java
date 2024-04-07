package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;
import xiaojiu.tools.RestartTools;

import static xiaojiu.tools.RestartTools.ProcessingTime;
import static xiaojiu.tools.RestartTools.isNumber;

public class RestartServerCommand implements CommandExecutor {
    public static String CommonNode = "restart";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入完整参数"));
            return true;
        } else if (strings[0].equalsIgnoreCase("cancel")) {
            if (PermissionHelper.isHasPermission(commandSender, CommonNode, "cancel")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器计划重启任务已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "取消"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "没有正在进行的计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (strings[0].equalsIgnoreCase("now")) {
            if (PermissionHelper.isHasPermission(commandSender, CommonNode, "now")) {
                if (RestartTools.isRestart) {
                    RestartTools.Done();
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前没有正在进行的重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (strings[0].equalsIgnoreCase("reset")) {
            if (PermissionHelper.isHasPermission(commandSender, CommonNode, "reset")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    RestartTools.Restart(Integer.parseInt(strings[1]));
                    MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器重启时间已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "重设为" + ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE + "秒"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前没有正在进行的重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (isNumber(strings[0])) {
            int num = Integer.parseInt(strings[0]);
            if (PermissionHelper.isHasPermission(commandSender, CommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    RestartTools.Restart(num);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "计划重启任务创建成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前已经有了计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有执行这个指令的权限"));
            }
        } else if (strings.length == 2) {
            int num = ProcessingTime(strings[0], Integer.parseInt(strings[1]));
            if (PermissionHelper.isHasPermission(commandSender, CommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    if (num == -1) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "参数错误"));
                        return true;
                    }
                    RestartTools.Restart(num);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "计划重启任务创建成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前已经有了计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有执行这个指令的权限"));
            }
        }
        return true;

//        return false;
    }

}
