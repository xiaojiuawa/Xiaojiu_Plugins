package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xiaojiu.Handles.Help.HelpMapHandler;
import xiaojiu.Handles.Restart.TimeHelper;
import xiaojiu.Handles.Save.SaveTaskManager;
import xiaojiu.StartPlugins;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;
import xiaojiu.config.ConfigManager;
import xiaojiu.config.SaveConfig;
import xiaojiu.task.PlayerJoinTimeTask;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerTimeCommand implements XiaojiuCommandExecutor {
    public static Map<String, HelpMap> PlayerTimeMap = new HashMap<>();
    public static String CommonNode = "playerTime";
    public static String PermissionNode = "PlayerTime";

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
                    OfflinePlayer offlinePlayer = StartPlugins.getInstance().getServer().getOfflinePlayer(strings[1]);
                    Date date = PlayerJoinTimeTask.GetPlayerLastJoinTime(offlinePlayer.getUniqueId());
                    if (date != null) {
                        String time = format.format(date);
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "玩家" + strings[1] + "上次上线的时间为" + time));
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.WHITE + "距现在有" + TimeHelper.GetTime(date)));
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + ChatColor.WHITE + strings[1] + ChatColor.LIGHT_PURPLE + "从未进入过服务器"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限查询玩家上线时间"));
                }
            } else if (strings[0].equalsIgnoreCase("save") || strings[0].equalsIgnoreCase("保存")) {
                if (PermissionHelper.isHasPermission(commandSender, true, PermissionNode, "save")) {
                    ConfigManager.getConfigMap().get("PlayerTime").Save();
//                    SaveConfig.SavePlayerTime();
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "保存玩家上线时间成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.RED + "你没有保存玩家上线时间的权限"));
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
                list.addAll(PlayerJoinTimeTask.getRecordedPlayers(strings[1]));

        }
        return list;
    }
}
