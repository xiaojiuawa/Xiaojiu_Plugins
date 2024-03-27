package Xiaojiu.CommandExecutor;

import Xiaojiu.RestartServer.RestartHelper;
import Xiaojiu.RestartServer.RestartServerUseTimer;
import Xiaojiu.RestartServer.WaitTimeToRestart;
import Xiaojiu.tools.MessageHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static Xiaojiu.tools.RestartTools.ProcessingTime;
import static Xiaojiu.tools.RestartTools.isNumber;

public class RestartServerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission("xiaojiu.restartServer.use")){
            player.sendMessage(ChatColor.DARK_RED +"错误:你没有使用此命令的权限");
            return true;
        }
        if (strings.length==0){
            player.sendMessage(ChatColor.DARK_RED +"错误:未输入重启时间");
            return true;
        }
        if(strings[0].equalsIgnoreCase("cancel")||strings[0].equalsIgnoreCase("取消")){
            if(RestartHelper.cancel()){
                player.sendMessage(ChatColor.DARK_PURPLE+"计划重启任务取消成功");
                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器重启任务已取消");
                return true;
            }else{
                player.sendMessage(ChatColor.RED+"错误:计划重启任务取消失败,无计划重启任务");
                return true;
            }
        }
        if (strings[0].equalsIgnoreCase("now")||strings[0].equals("现在")){
            if (!player.hasPermission("xiaojiu.restartServer.now")){
                player.sendMessage(ChatColor.DARK_RED +"错误:你没有立刻重启的权限");
            }else {
                RestartHelper.Done();
            }
            return true;
        }
        if (strings[0].equalsIgnoreCase("reset") || strings[0].equalsIgnoreCase("重设")){

            if (isNumber(strings[1])){
                RestartHelper.cancel();
                RestartServerUseTimer.Restart(Integer.parseInt(strings[1]));
                player.sendMessage(ChatColor.LIGHT_PURPLE+"服务器重启时间重设成功");
                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器重启时间已被重新设置为"+strings[1]+"秒");
                return true;
            }else {
                player.sendMessage(ChatColor.RED+"请输入正确的时间");
                return true;
            }

        }
        if(isNumber(strings[0])){
            int num = Integer.parseInt(strings[0]);
            if(!RestartHelper.Restart(num)){
                player.sendMessage(ChatColor.DARK_RED+"错误:已有计划重启任务");
                return true;
            }
            player.sendMessage(ChatColor.DARK_PURPLE +"计划重启任务创建成功");
            return true;
        } else if (strings.length==2) {
            int num = ProcessingTime(strings[0],Integer.parseInt(strings[1]));
            if(!RestartHelper.Restart(num)){
                player.sendMessage(ChatColor.DARK_RED+"错误:已有计划重启任务");
                return true;
            }
            player.sendMessage(ChatColor.DARK_PURPLE +"计划重启任务创建成功");
            return true;
        }


        return false;
    }

}
