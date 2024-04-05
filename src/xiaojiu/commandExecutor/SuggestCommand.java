package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;
import xiaojiu.task.ReloadTask;
import xiaojiu.tools.MessageHelper;

public class SuggestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length != 0) {
            if (strings[0].equalsIgnoreCase("start")) {
                if (ReloadTask.isSuggesting) {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已有投票任务存在,如果要取消,请使用/sug cancel 命令取消"));
                } else {
                    ReloadTask.RunTask(StartPlugins.getInstance(), ((Player) commandSender).getUniqueId());
                }
            } else if (ReloadTask.isSuggesting) {
                if (strings[0].equalsIgnoreCase("revoke") || strings[0].equalsIgnoreCase("撤销")) {
                    if (!ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你还没有投票"));
                    } else {
                        ReloadTask.suggestHelper.delSuggest(((Player) commandSender).getUniqueId());
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "撤销投票成功"));
                    }
                } else if (strings[0].equalsIgnoreCase("agree") || strings[0].equalsIgnoreCase("同意")) {
                    if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你已经投票"));
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请使用/sug revoke 或 /sug 撤销 撤销您的投票"));
                    } else {
                        ReloadTask.suggestHelper.Approve.add(((Player) commandSender).getUniqueId());
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已同意投票"));
                    }
                } else if (strings[0].equalsIgnoreCase("refuse") || strings[0].equalsIgnoreCase("拒绝")) {
                    if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你已经投票"));
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请使用/sug revoke 或 /sug 撤销 撤销您的投票"));
                    } else {
                        ReloadTask.suggestHelper.Refuse.add(((Player) commandSender).getUniqueId());
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "已拒绝投票"));
                    }
                } else if (strings[0].equalsIgnoreCase("cancel") || strings[0].equalsIgnoreCase("取消")) {
                    if (commandSender.hasPermission("xiaojiu.ReloadTask.cancel") || ReloadTask.suggestHelper.sponsor.equals(((Player) commandSender).getUniqueId())) {
                        ReloadTask.cancel();
                        MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前投票任务已被玩家" + commandSender.getName() + "取消"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有取消当前投票任务的权限"));
                    }
                } else if (strings[0].equalsIgnoreCase("down")) {
                    if (commandSender.hasPermission("xiaojiu.ReloadTask.down")) {
                        ReloadTask.down();
                        MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前投票任务已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "立刻结束"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有立刻结束当前投票的权限"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前无投票任务进行"));
                }
            }
            return true;
        }
        return false;
    }
}
