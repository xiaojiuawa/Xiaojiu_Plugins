package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xiaojiu.tools.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafeGuardCommand implements TabExecutor {
    public static String PermissionNode = "safeguard";
    public static String CommonNode = "sg";
    public static Map<String,HelpMap> SafeGuardMap = new HashMap<>();
    public static void InitMap(){
        SafeGuardMap.put("now",new HelpMap(CommonNode,"/xj sg now","xiaojiu.safeguard.now","通过这个指令立即开始进行服务器维护"));
        SafeGuardMap.put("cancel",new HelpMap(CommonNode,"/xj sg cancel","xiaojiu.safeguard.cancel","通过这个指令来取消计划服务器维护"));
        SafeGuardMap.put("end",new HelpMap(CommonNode,"/xj sg end","xiaojiu.safeguard.end","通过这个指令结束服务器的维护"));
        SafeGuardMap.put("",new HelpMap(CommonNode,"/xj sg [时间]","xiaojiu.safeguard.start","通过这个指令发起一个维护任务"));
        SafeGuardMap.put("m",new HelpMap(CommonNode,"/xj sg m/h/d [时间]","xiaojiu.safeguard.start","通过这个指令发起一个维护任务(使用重设时间单位)，其中m表示天,h表示小时,m表示分钟"));
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length>0) {
            if (strings[0].equalsIgnoreCase("now") || strings[0].equalsIgnoreCase("现在")) {
                if (PermissionHelper.isHasPermission(commandSender, PermissionNode, "now")) {
                    SafeGuardHelper.done();
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器维护状态设置成功"));
                }
            } else if (strings[0].equalsIgnoreCase("cancel") || strings[0].equalsIgnoreCase("解除")) {
                if (PermissionHelper.isHasPermission(commandSender, PermissionNode, "cancel")) {
                    if (SafeGuardHelper.timer == null) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器没有计划维护任务"));
                    } else {
                        SafeGuardHelper.cancel();
                        MessageHelper.SendMessageAllPlayer(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器计划维护任务已被管理员" + ChatColor.WHITE + commandSender.getName() + ChatColor.LIGHT_PURPLE + "取消"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage("你没有权限取消服务器计划维护任务"));
                }
            } else if (strings[0].equalsIgnoreCase("end") || strings[0].equalsIgnoreCase("结束")) {
                if (PermissionHelper.isHasPermission(commandSender, PermissionNode, "end")) {
                    if (SafeGuardHelper.isSafeGuard) {
                        SafeGuardHelper.SafeGuard();
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器维护状态结束成功"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器并没有在维护"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有结束服务器维护的权限"));
                }
            } else if (Until.isNumber(strings[0])) {
                if (PermissionHelper.isHasPermission(commandSender, PermissionNode, "start")) {
                    if (SafeGuardHelper.isSafeGuard) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器已经在维护状态了"));
                    } else {
                        SafeGuardHelper.startSafeGuard(Integer.parseInt(strings[0]));
                    }
                }
            } else if (strings[0].equalsIgnoreCase("d") || strings[0].equalsIgnoreCase("h") || strings[0].equalsIgnoreCase("m") && strings.length > 1) {
                int num = RestartTools.ProcessingTime(strings[0], Integer.parseInt(strings[1]));
                if (num == -1) {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "命令参数错误"));
                    return true;
                }
                if (SafeGuardHelper.isSafeGuard) {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器已经在维护状态了"));
                } else {
                    RestartTools.Restart(num);
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "服务器计划维护任务创建成功"));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length==1){
            for (Map.Entry<String,HelpMap> entry:SafeGuardMap.entrySet()){
                if (commandSender.hasPermission(entry.getValue().PermissionNode)&&entry.getKey().startsWith(strings[0].toLowerCase())){
                    list.add(entry.getKey());
                }
            }
        }
        return list;
    }
}
