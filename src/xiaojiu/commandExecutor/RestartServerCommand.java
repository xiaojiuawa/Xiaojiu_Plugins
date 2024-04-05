package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.RestartTools;

import static xiaojiu.tools.RestartTools.ProcessingTime;
import static xiaojiu.tools.RestartTools.isNumber;

public class RestartServerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xiaojiu.restartServer.use")) {
            commandSender.sendMessage(ChatColor.DARK_RED + "错误:你没有使用此命令的权限");
            return true;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.DARK_RED + "错误:未输入重启时间");
            return true;
        }
        if (strings[0].equalsIgnoreCase("cancel") || strings[0].equalsIgnoreCase("取消")) {
            if (RestartTools.cancel()) {
                commandSender.sendMessage(ChatColor.DARK_PURPLE + "计划重启任务取消成功");
                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE + "服务器重启任务已取消");
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "错误:计划重启任务取消失败,无计划重启任务");
                return true;
            }
        }
        if (strings[0].equalsIgnoreCase("now") || strings[0].equals("现在")) {
            if (!commandSender.hasPermission("xiaojiu.restartServer.now")) {
                commandSender.sendMessage(ChatColor.DARK_RED + "错误:你没有立刻重启的权限");
            } else {
                RestartTools.Done();
            }
            return true;
        }
        if (strings[0].equalsIgnoreCase("reset") || strings[0].equalsIgnoreCase("重设")) {

            if (isNumber(strings[1])) {
                RestartTools.cancel();
                RestartTools.RestartServerUseTimer.Restart(Integer.parseInt(strings[1]));
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "服务器重启时间重设成功");
                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE + "服务器重启时间已被重新设置为" + strings[1] + "秒");
            } else {
                commandSender.sendMessage(ChatColor.RED + "请输入正确的时间");
            }
            return true;

        }
        if (isNumber(strings[0])) {
            int num = Integer.parseInt(strings[0]);
            if (!RestartTools.Restart(num)) {
                commandSender.sendMessage(ChatColor.DARK_RED + "错误:已有计划重启任务");
                return true;
            }
            commandSender.sendMessage(ChatColor.DARK_PURPLE + "计划重启任务创建成功");
            return true;
        } else if (strings.length == 2) {
            int num = ProcessingTime(strings[0], Integer.parseInt(strings[1]));
            if (num == -1) return true;
            if (!RestartTools.Restart(num)) {
                commandSender.sendMessage(ChatColor.DARK_RED + "错误:已有计划重启任务");
                return true;
            }
            commandSender.sendMessage(ChatColor.DARK_PURPLE + "计划重启任务创建成功");
            return true;
        }


        return false;
    }

}
