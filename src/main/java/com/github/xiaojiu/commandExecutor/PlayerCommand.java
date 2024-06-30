package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.PlayerLimit.PlayerLimitTools;
import com.github.xiaojiu.Handles.PlayerTime.PlayerTools;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.config.ConfigManager;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCommand implements XiaojiuCommandExecutor {
    public static final Map<String, HelpMap> PlayerCommandMap = new HashMap<>();
    public static final String CommandNode = "Limit";
    public static final String PermissionNode = "PlayerLimit";

    @Override
    public void InitMap() {
        PlayerCommandMap.put("save", new HelpMapHandler(CommandNode, "/pl save", "xiaojiu.op.PlayerLimit.save", "通过这个方法立即保存玩家限制列表"));
        PlayerCommandMap.put("add", new HelpMapHandler(CommandNode, "/pl add [玩家名] true/false(是否保存) [任意一段字符串](选填 踢出玩家时给玩家发送的踢出解释)", "xiaojiu.op.PlayerLimit.add", "通过这个指令来添加一位玩家到达限制列表中 注:其中第二个参数填true则会启用保存，服务器关闭后仍有效，填false则禁用保存，服务器关闭后重置 "));
        PlayerCommandMap.put("remove", new HelpMapHandler(CommandNode, "/pl remove [玩家名]", "xiaojiu.op.PlayerLimit.remove", "通过这个指令来解除对玩家的限制"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return PlayerCommandMap;
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
        if (strings.length != 0) {
            if (strings[0].equalsIgnoreCase("save") || strings[0].equalsIgnoreCase("保存")) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "save")) {
                    ConfigManager.getConfigMap().get("LimitPlayer").Save();
//                    SaveConfig.SaveLimitPlayer();
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("LimitPlayer.save.success")));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("LimitPlayer.save.noPermission")));
                }
            } else if (strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("添加")) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "add")) {
                    if (strings.length > 2) {
                        Player player = PlayerTools.GetPlayer(strings[1]);
                        if (commandSender instanceof Player) {
                            Player player1 = (Player) commandSender;
                            if (player1.equals(player)) {
                                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.add.self")));
                            }
                        }
                        if (player != null) {
                            String message;
                            if (strings[2].equalsIgnoreCase("true") || strings[2].equalsIgnoreCase("离线")) {
                                message = PlayerLimitTools.add(Utils.IntegrateString(strings,3), player, true);
                            } else if (strings[2].equalsIgnoreCase("false") || strings[2].equalsIgnoreCase("非离线")) {
                                message = PlayerLimitTools.add(Utils.IntegrateString(strings,3), player, false);
                            } else {
                                message = MessageHelper.getMessageCompletion("PlayerLimit.save.set");
                            }
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + message));
                        } else {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.add.notFind")));
                        }
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.add.noName")));
                    }
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.add.noPermission")));
                }

            } else if (strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("remove") || strings[0].equalsIgnoreCase("删除")) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "remove")) {
                    if (!(strings.length < 2)) {
                        OfflinePlayer player = PlayerTools.GetPlayerOffer(strings[1]);
                        if (commandSender instanceof Player) {
                            Player player1 = (Player) commandSender;
                            if (player1.getUniqueId().equals(player.getUniqueId()) && player1.hasPermission("xiaojiu.op.PlayerLimit.delSelf")) {
                                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.del.self")));
                            }
                        }
                        if (player != null) {
                            if (PlayerLimitTools.isPlayerIn(player.getUniqueId())) {
                                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + PlayerLimitTools.remove(player)));
                            } else {
                                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("PlayerLimit.del.notFind"), player.getName())));
                            }
                        } else {
                            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("PlayerLimit.del.noOnline"), strings[1])));
                        }
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.del.noName")));
                    }
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerLimit.del.noPermission")));
                }

            }
            return true;
        } else {
            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("PlayerLimit.noCommand")));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
//        System.out.println(strings.length);
//        System.out.println(Arrays.toString(strings));
        if (strings.length == 1) {
            for (Map.Entry<String, HelpMap> entry : PlayerCommandMap.entrySet()) {
                if (commandSender.hasPermission(entry.getValue().getPermissionNode()) && entry.getKey().startsWith(strings[0].toLowerCase()))
                    list.add(entry.getKey());
            }
        } else if (strings.length == 2) {
            if (commandSender.hasPermission("xiaojiu.op.PlayerLimit.add") && strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("添加")) {
                list.addAll(Utils.GetOnlinePlayerNames(strings[1]));
            } else if (commandSender.hasPermission("xiaojiu.op.PlayerLimit.remove") && strings[0].equalsIgnoreCase("删除") || strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("remove")) {
                list.addAll(PlayerLimitTools.GetAllPlayerName(strings[0]));
            }
        }
        return list;
    }


}
