package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xiaojiu.Handles.Help.HelpMap;
import xiaojiu.Handles.Vanish.Vanish;
import xiaojiu.Handles.Vanish.VanishTask;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VanishCommand {
    public static String PermissionNode = "vanish";
    public static String CommandNode = "vanish";
    public static Map<String, HelpMap> vanishMap = new HashMap<>();

    public static void InitMap() {
        vanishMap.put("", new HelpMap(CommandNode, "/xj v [时间]", "xiaojiu.op.vanish.use.self", "使用这个指令可以将你自己的影身状态设置为开启，时间为选填选项"));
        vanishMap.put(" ", new HelpMap(CommandNode, "/xj v [玩家名] [时间]", "xiaojiu.op.vanish.use.otherPlayer", "使用这个指令将其他玩家的隐身状态设置为开启"));
    }

    public static boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender);
            if (strings.length == 2) {
                if (!strings[0].equalsIgnoreCase(commandSender.getName())) {
                    if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "otherPlayer")) {
                        Player player1 = StartPlugins.getInstance().getServer().getPlayer(strings[0]);
                        if (player1 == null) {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + strings[0] + "未在线或者未在本子服"));
                            return true;
                        }
                        VanishTask.AddPlayerVanishTime(StartPlugins.getInstance(), player1, Integer.parseInt(strings[1]));
                        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + commandSender.getName() + "已将你隐身状态设置为开启 时长为:" + strings[1] + "秒"));
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + player.getName() + "的隐身状态已经成功开启"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有权限使用这个指令"));
                    }
                } else {
                    if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "self")) {
                        VanishTask.AddPlayerVanishTime(StartPlugins.getInstance(), player, Integer.parseInt(strings[1]));
                        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "隐身已经成功开启 时长为:" + strings[1] + "秒"));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限使用这个指令"));
                    }
                }

            } else if (strings.length == 1) {
                if (!strings[0].equalsIgnoreCase(player.getName())) {
                    player = StartPlugins.getInstance().getServer().getPlayer(strings[0]);
                    if (player == null) {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + ChatColor.WHITE + strings[0] + ChatColor.LIGHT_PURPLE + "不在线或不在本子服"));
                        return true;
                    } else if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "otherPlayer")) {
                        Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                        if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + commandSender.getName() + "已将你隐身状态设置为开启 时长为:永久"));
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + player.getName() + "的隐身状态已经成功开启"));
                        } else {
                            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + commandSender.getName() + "已将你隐身状态设置为关闭 时长为:永久"));
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + player.getName() + "的隐身状态已经成功关闭"));
                        }

                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有使用这个指令的权限"));
                    }
                } else {
                    if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "self")) {
                        Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                        if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "隐身已经成功开启 时长为:永久"));
                        } else {
                            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "隐身已经成功关闭"));
                        }
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有使用这个指令的权限"));
                    }
                }
            } else if (strings.length == 0) {
                if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "self")) {
                    Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                    if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "隐身已经成功开启 时长为:永久"));
                    } else {
                        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "隐身已经成功关闭"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有使用这个指令的权限"));
                }
            }
        }

        return true;
    }

    public static List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            StartPlugins.getInstance().getServer().getOnlinePlayers().forEach(player -> list.add(player.getName()));
        }
        return list;
    }
}
