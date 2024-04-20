package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;
import xiaojiu.tools.RestartTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xiaojiu.tools.RestartTools.ProcessingTime;
import static xiaojiu.tools.Until.isNumber;

public class RestartServerCommand implements TabExecutor {
    public static String PermissionCommonNode = "restart";
    public static String CommonNode = "rest";
    public static Map<String,HelpMap> RestartMap = new HashMap<>();
    public static void InitMap(){
        RestartMap.put("cancel",new HelpMap(CommonNode,"/rest cancel","xiaojiu.op.restart.cancel","通过这个指令取消当前的计划重启任务"));
        RestartMap.put("now",new HelpMap(CommonNode,"/rest now","xiaojiu.op.restart.now","通过这个指令立刻执行重启"));
        RestartMap.put("reset",new HelpMap(CommonNode,"/rest reset [时间]","xiaojiu.op.restart.reset","通过这个指令重新设置重启时间"));
        RestartMap.put("",new HelpMap(CommonNode,"/rest [时间]","xiaojiu.op.restart.start","通过这个指令发起一个重启任务"));
        RestartMap.put("m",new HelpMap(CommonNode,"/rest m/h/d [时间]","xiaojiu.op.restart.start","通过这个指令发起一个重启任务(使用重设时间单位)，其中m表示天,h表示小时,m表示分钟"));
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入完整参数"));
            return true;
        } else if (strings[0].equalsIgnoreCase("cancel")) {
            if (PermissionHelper.isHasPermission(commandSender, "op",PermissionCommonNode, "cancel")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器计划重启任务已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "取消"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "没有正在进行的计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (strings[0].equalsIgnoreCase("now")) {
            if (PermissionHelper.isHasPermission(commandSender,"op", PermissionCommonNode, "now")) {
                if (RestartTools.isRestart) {
                    RestartTools.Done();
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前没有正在进行的重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (strings[0].equalsIgnoreCase("reset")) {
            if (PermissionHelper.isHasPermission(commandSender, "op",PermissionCommonNode, "reset")) {
                if (RestartTools.isRestart) {
                    RestartTools.cancel();
                    RestartTools.Restart(Integer.parseInt(strings[1]));
                    MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器重启时间已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "重设为" + ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE + "秒"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前没有正在进行的重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限执行这个指令"));
            }
        } else if (isNumber(strings[0])) {
            int num = Integer.parseInt(strings[0]);
            if (PermissionHelper.isHasPermission(commandSender, "op",PermissionCommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    RestartTools.Restart(num);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "计划重启任务创建成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前已经有了计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有执行这个指令的权限"));
            }
        } else if (strings.length == 2) {
            int num = ProcessingTime(strings[0], Integer.parseInt(strings[1]));
            if (PermissionHelper.isHasPermission(commandSender, "op",PermissionCommonNode, "start")) {
                if (!RestartTools.isRestart) {
                    if (num == -1) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "参数错误"));
                        return true;
                    }
                    RestartTools.Restart(num);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "计划重启任务创建成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "当前已经有了计划重启任务"));
                }
            } else {
                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有执行这个指令的权限"));
            }
        }
        return true;

//        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length==1){
            for (Map.Entry<String,HelpMap> entry:RestartMap.entrySet()){
                if (commandSender.hasPermission(entry.getValue().PermissionNode)&&entry.getKey().startsWith(strings[0].toLowerCase())){
                    list.add(entry.getKey());
                }
            }

        }
        return list;
    }
}
