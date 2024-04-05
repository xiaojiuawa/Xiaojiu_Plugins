package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xiaojiu.StartPlugins;
import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.TimeHelper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) {
            if (strings[0].equalsIgnoreCase("find") || strings[0].equalsIgnoreCase("查询")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
                OfflinePlayer offlinePlayer = StartPlugins.getInstance().getServer().getOfflinePlayer(strings[1]);
                Date date = PlayerJoinTimeTask.GetPlayerLastJoinTime(offlinePlayer.getUniqueId());

                if (date != null) {
                    String time = format.format(date);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "玩家" + strings[1] + "上次上线的时间为" + time));
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "距现在有" + TimeHelper.GetTime(date)));
//                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE+"距现在有"+TimeHelper.GetTime(LocalDate.parse(time,formatter))));
//                    commandSender.sendMessage(String.valueOf(PlayerJoinTimeTask.map.entrySet().iterator().next().getValue().uuid));
//                    commandSender.sendMessage(String.valueOf(((Player)commandSender).getUniqueId()));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE + "从未进入过服务器"));
//                    commandSender.sendMessage(String.valueOf(PlayerJoinTimeTask.map.size()));
//                    commandSender.sendMessage(String.valueOf(PlayerJoinTimeTask.map.containsKey(((Player) commandSender).getUniqueId())));
//                    commandSender.sendMessage(String.valueOf(PlayerJoinTimeTask.map.entrySet().iterator().next().getValue().uuid));
//                    commandSender.sendMessage(String.valueOf(((Player)commandSender).getUniqueId()));
                }

            }
            return true;
        }
        return false;
    }

}
