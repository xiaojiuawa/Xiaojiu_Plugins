package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.PlayerTime.PlayerJoinTimeTool;
import com.github.xiaojiu.Handles.Restart.TimeHelper;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.config.ConfigManager;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerTimeCommand implements XiaojiuCommandExecutor {
    public static final Map<String, HelpMap> PlayerTimeMap = new HashMap<>();
    public static final String CommonNode = "playerTime";
    public static final String PermissionNode = "PlayerTime";

    @Override
    public void InitMap() {
        PlayerTimeMap.put("find", new HelpMapHandler(CommonNode, "/pt find [玩家名]", "xiaojiu.op.PlayerTime.find", "通过这个查询玩家的上一次上线时间和时间差"));
        PlayerTimeMap.put("save", new HelpMapHandler(CommonNode, "/pt save", "xiaojiu.op.PlayerTime.save", "通过这个立刻保存玩家上线时间"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return PlayerTimeMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommonNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            if (strings[0].equalsIgnoreCase("find") || strings[0].equalsIgnoreCase("查询") && strings.length == 2) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "find")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    OfflinePlayer offlinePlayer = Xiaojiu.getInstance().getServer().getOfflinePlayer(strings[1]);
                    Date date = PlayerJoinTimeTool.GetPlayerLastJoinTime(offlinePlayer.getUniqueId());
                    if (date != null) {
                        String time = format.format(date);
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.WHITE + String.format(MessageHelper.getMessageCompletion("PlayerTime.info"), strings[1], time)));
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.WHITE + String.format(MessageHelper.getMessageCompletion("PlayerTime.info2"), TimeHelper.GetTime(date))));
                    } else {
                        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + String.format(MessageHelper.getMessageCompletion("PlayerTime.notFind"), ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE)));
                    }
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerTime.noPermission")));
                }
            } else if (strings[0].equalsIgnoreCase("save") || strings[0].equalsIgnoreCase("保存")) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "save")) {
                    ConfigManager.getConfigMap().get("PlayerTime").Save();
//                    SaveConfig.SavePlayerTime();
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + MessageHelper.getMessageCompletion("PlayerTime.save.success")));
                } else {
                    commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + MessageHelper.getMessageCompletion("PlayerTime.save.noPermission")));
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
            for (Map.Entry<String, HelpMap> entry : PlayerTimeMap.entrySet()) {
                if (commandSender.hasPermission(entry.getValue().getPermissionNode()) && entry.getKey().startsWith(strings[0].toLowerCase()))
                    list.add(entry.getKey());
            }
        } else if (strings.length == 2) {
            if (strings[1].equalsIgnoreCase("find") && commandSender.hasPermission("xiaojiu.op.PlayerTime.find"))
                list.addAll(PlayerJoinTimeTool.getRecordedPlayers(strings[1]));

        }
        return list;
    }
}
