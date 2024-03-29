package xiaojiu.commandExecutor;

import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.RestartTools;
import xiaojiu.tools.SafeGuardHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SafeGuardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ((commandSender instanceof Player)){
            Player player=(Player) commandSender;
            if (!player.hasPermission("xiaojiu.safeguard.use")){
                player.sendMessage(ChatColor.DARK_RED +"错误:你没有使用此命令的权限");
                return true;
            }
            if (strings.length==0){
                player.sendMessage(ChatColor.RED+"错误:请输入正确的命令参数");
//                return true;
            }
            if (strings[0].equalsIgnoreCase("now")||strings[0].equalsIgnoreCase("现在")){
                SafeGuardHelper.done();
//                return true;
            }
            if (strings[0].equalsIgnoreCase("cancel")||strings[0].equalsIgnoreCase("取消")){
                SafeGuardHelper.cancel();
//                player.sendMessage(ChatColor.LIGHT_PURPLE+"服务器计划维护任务取消成功");
                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器计划维护任务已取消");
            }
            if (RestartTools.isNumber(strings[0])){
                if (SafeGuardHelper.startSafeGuard(Integer.parseInt(strings[0]))){
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"服务器计划维护任务创建成功");
                }else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"服务器已经在维护状态了");
                }
//                return true;
            }
            if (strings[0].equalsIgnoreCase("end")||strings[0].equalsIgnoreCase("结束")){
                SafeGuardHelper.SafeGuardEnd();
            }
            if (strings.length==2){
                int num = RestartTools.ProcessingTime(strings[0],Integer.parseInt(strings[1]));
                if (num==-1) return true;
//                if (player.hasPermission("xiaojiu.safeguard."))
                if (SafeGuardHelper.startSafeGuard(num)) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "服务器计划维护任务创建成功");
                }else{
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"服务器已经在维护状态了");
                }
            }
            return true;
        }

        return false;
    }
}
