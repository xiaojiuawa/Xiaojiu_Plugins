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
        if (commandSender instanceof Player && strings.length!=0){
                if (strings[0].equalsIgnoreCase("revoke")||strings[0].equalsIgnoreCase("撤销")) {
                    if (ReloadTask.isSuggesting) {
                        if (!ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())) {
                            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "您还没有投票");
                        }
                        ReloadTask.suggestHelper.delSuggest(((Player) commandSender).getUniqueId());
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "已撤销投票");
                    }else {
                        commandSender.sendMessage(ChatColor.DARK_GREEN+"当前无投票任务进行");
                    }
                }
            if (strings[0].equalsIgnoreCase("agree")||strings[0].equalsIgnoreCase("同意")){
                if (ReloadTask.isSuggesting){
                    if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())){
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"您已经投票");
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"请使用"+ChatColor.WHITE+"/sug revoke或/sug 撤销"+ChatColor.LIGHT_PURPLE+" 撤销您的投票");
                        return true;
                    }
                    ReloadTask.suggestHelper.Approve.add(((Player) commandSender).getUniqueId());
                    commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"已同意");
                }else {
                    commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"无投票任务进行");
                }
            }
            if (strings[0].equalsIgnoreCase("refuse")||strings[0].equalsIgnoreCase("拒绝")){
                if (ReloadTask.isSuggesting){
                    if (ReloadTask.suggestHelper.isSuggested(((Player) commandSender).getUniqueId())){
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"您已经投票");
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"请使用"+ChatColor.WHITE+"/sug revoke或/sug"+ChatColor.LIGHT_PURPLE+" 撤销 撤销您的投票");
                        return true;
                    }
                    ReloadTask.suggestHelper.Refuse.add(((Player) commandSender).getUniqueId());
                    commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"已拒绝");
                }
            }
            if (strings[0].equalsIgnoreCase("start")||strings[0].equalsIgnoreCase("开始")){
                if (ReloadTask.suggestHelper!=null){
                    if (ReloadTask.isSuggesting){
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"投票任务已存在");
                        return true;
                    }
                }
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"投票任务创建成功");
                ReloadTask.RunTask(StartPlugins.getInstance());

            }
            if (strings[0].equalsIgnoreCase("cancel")||strings[0].equalsIgnoreCase("取消")){
                if (ReloadTask.isSuggesting){
                    if (commandSender.hasPermission("xiaojiu.ReloadTask.cancel")){
                        ReloadTask.cancel();
                        MessageHelper.SendMessageAllPlayer(String.format("当前投票任务已被管理员%s取消", commandSender.getName()));
                    }
                }else {
                    commandSender.sendMessage("当前无投票任务进行");
                }

            }
            if (strings[0].equalsIgnoreCase("down")){
                if (commandSender.hasPermission("xiaojiu.ReloadTask.down")){
                    if (ReloadTask.isSuggesting){
                        ReloadTask.down();
                        ReloadTask.cancel();
                    }
                }
            }
            return true;
        }
        return false;
    }
}
