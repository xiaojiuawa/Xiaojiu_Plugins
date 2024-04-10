package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Until;

import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length>0){
            if (strings[0].equalsIgnoreCase("help")){
                if (strings.length==1){
                    HelpCommand.SendHelps(commandSender,"",0);
                } else if (strings.length>2&& Until.isNumber(strings[2])) {
                    if (strings[1].equalsIgnoreCase("玩家限制")) {
                        HelpCommand.SendHelps(commandSender, strings[1], Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("玩家上线时间")) {
                        HelpCommand.SendHelps(commandSender,strings[1],Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("投票重启")) {
                        HelpCommand.SendHelps(commandSender,strings[1],Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("重启主模块")) {
                        HelpCommand.SendHelps(commandSender,strings[1],Integer.parseInt(strings[2]));
                    }else if (strings[1].equalsIgnoreCase("服务器维护命令")){
                        HelpCommand.SendHelps(commandSender,strings[1],Integer.parseInt(strings[2]));
                    }else{
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED+"请输入正确的命令"));
                    }
                } else if (strings.length==2) {
                    HelpCommand.SendHelps(commandSender,strings[1],1);
                }

            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
