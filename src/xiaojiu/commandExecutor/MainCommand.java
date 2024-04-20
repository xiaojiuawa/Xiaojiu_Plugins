package xiaojiu.commandExecutor;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import scala.Array;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;
import xiaojiu.tools.Until;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements TabExecutor {
    public static String CommonNode = "xiaojiu";

    public static Map<String,Map<String,HelpMap>> helpMap = new HashMap<>();
    public static void InitMap(){
        Map<String,HelpMap> temp = new HashMap<>();
        // help命令
        temp.put("help",new HelpMap(CommonNode,"/xj help [命令大节点] [页面(默认为1)]","xiaojiu.main.help.use","通过这个指令查看命令帮助 当你输入这个指令后，会有一个总列表出现，这时再输入总列表当作页码即可"));
    }
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
            }else if(strings[0].equalsIgnoreCase("v")||strings[0].equalsIgnoreCase("隐身")){
                if (PermissionHelper.isHasPermission(commandSender,""));
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
