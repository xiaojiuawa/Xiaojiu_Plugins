package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.Vanish.Vanish;
import com.github.xiaojiu.Handles.Vanish.VanishTask;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VanishCommand implements XiaojiuCommandExecutor {
    public static final String PermissionNode = "vanish";
    public static final String CommandNode = "vanish";
    public static final Map<String, HelpMap> vanishMap = new HashMap<>();

    @Override
    public void InitMap() {
        vanishMap.put("", new HelpMapHandler(CommandNode, "/xj v [时间]", "xiaojiu.op.vanish.use.self", "使用这个指令可以将你自己的影身状态设置为开启，时间为选填选项"));
        vanishMap.put(" ", new HelpMapHandler(CommandNode, "/xj v [玩家名] [时间]", "xiaojiu.op.vanish.use.otherPlayer", "使用这个指令将其他玩家的隐身状态设置为开启"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return vanishMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommandNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender);
            if (strings.length == 2) {
                if (!strings[0].equalsIgnoreCase(commandSender.getName())) {
                    if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "otherPlayer")) {
                        Player player1 = Xiaojiu.getInstance().getServer().getPlayer(strings[0]);
                        if (player1 == null) {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.noPlayer"), strings[0])));
                            return true;
                        }
                        VanishTask.AddPlayerVanishTime(Xiaojiu.getInstance(), player1, Integer.parseInt(strings[1]));
                        player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.other.success"), commandSender.getName(), strings[1])));
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.other.success2"), player.getName())));
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
                    }
                } else {
                    if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "self")) {
                        VanishTask.AddPlayerVanishTime(Xiaojiu.getInstance(), player, Integer.parseInt(strings[1]));
                        player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.self.success"), strings[1])));
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("Command.noPermission")));
                    }
                }

            } else if (strings.length == 1) {
                if (!strings[0].equalsIgnoreCase(player.getName())) {
                    player = Xiaojiu.getInstance().getServer().getPlayer(strings[0]);
                    if (player == null) {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.noPlayer"), ChatColor.WHITE + strings[0] + ChatColor.LIGHT_PURPLE)));
                        return true;
                    } else if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "otherPlayer")) {
                        Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                        if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                            player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.other.success"), commandSender.getName(), "永久")));
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format("Vanish.start.other.success2", player.getName())));
                        } else {
                            player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.end.other.success"), commandSender.getName())));
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.end.other.success2"), player.getName())));
                        }

                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
                    }
                } else {
                    if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "self")) {
                        Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                        if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                            player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.self.success"), "永久")));
                        } else {
                            player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("Vanish.end.self.success")));
                        }
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
                    }
                }
            } else if (strings.length == 0) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "self")) {
                    Vanish.VanishPlayer(player, !Vanish.VanishPlayers.contains(player.getUniqueId()));
                    if (Vanish.VanishPlayers.contains(player.getUniqueId())) {
                        player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("Vanish.start.self.success"), "永久")));
                    } else {
                        player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("Vanish.end.self.success")));
                    }
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("Command.noPermission")));
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            for (Player player : Xiaojiu.getInstance().getServer().getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(strings[0].toLowerCase()))
                    list.add(player.getName());
            }
        }
        return list;
    }
}
