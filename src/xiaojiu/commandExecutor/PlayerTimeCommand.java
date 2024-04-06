package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import xiaojiu.StartPlugins;
import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.TimeHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerTimeCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) {
            if (strings[0].equalsIgnoreCase("find") || strings[0].equalsIgnoreCase("查询")) {
                if (commandSender.hasPermission("xiaojiu.PlayerTime.find")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    OfflinePlayer offlinePlayer = StartPlugins.getInstance().getServer().getOfflinePlayer(strings[1]);
                    Date date = PlayerJoinTimeTask.GetPlayerLastJoinTime(offlinePlayer.getUniqueId());
                    if (date != null) {
                        String time = format.format(date);
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "玩家" + strings[1] + "上次上线的时间为" + time));
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "距现在有" + TimeHelper.GetTime(date)));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE + "从未进入过服务器"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限查询玩家上线时间"));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("find");
        } else if (strings.length == 2) {
            list.addAll(PlayerJoinTimeTask.getRecordedPlayers(strings[1]));
        }
        return list;
    }
}
