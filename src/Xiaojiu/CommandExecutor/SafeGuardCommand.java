package Xiaojiu.CommandExecutor;

import Xiaojiu.StartPlugins;
import Xiaojiu.tools.RestartTools;
import Xiaojiu.tools.SafeGuardHelper;
import Xiaojiu.tools.Until;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SafeGuardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            return true;
        }
        Player player=(Player) commandSender;
        if (!player.hasPermission("xiaojiu.safeguard.use")){
            player.sendMessage(ChatColor.RED+"错误:你没有发起服务器维护的权限");
            return true;
        }
        if (strings.length==0){
            player.sendMessage(ChatColor.RED+"错误:请输入正确的命令参数");
            return true;
        }
        if (strings[0].equalsIgnoreCase("now")||strings[0].equalsIgnoreCase("现在")){
            SafeGuardHelper.SafeGuard();
            Until.KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers(),"服务器维护,请等待服务器维护结束");
            return true;
        }
        if (RestartTools.isNumber(strings[0])){
            SafeGuardHelper.SafeGuard(Integer.parseInt(strings[0]));
            return true;
        }
        return false;
    }
}
