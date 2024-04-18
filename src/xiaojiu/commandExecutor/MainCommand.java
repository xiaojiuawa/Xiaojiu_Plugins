package xiaojiu.commandExecutor;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import scala.Array;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Until;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length>0){
            if (strings[0].equalsIgnoreCase("help")){
                //帮助指令开始
                if (strings.length==1){
                    //主帮助信息
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
                //帮助指令结束
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length==1){
            HelpCommand.helpMap.forEach((string, helpMapMap) -> {
                if (string.startsWith(strings[0].toLowerCase())) list.add(string);
            });
        }
        return list;
    }
}
