package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xiaojiu.Handles.Help.HelpMapHandler;
import xiaojiu.Handles.LimitPlayer.LimitPlayerTools;
import xiaojiu.Handles.PlayerTime.PlayerTools;
import xiaojiu.api.HelpMap;
import xiaojiu.api.XiaojiuCommandExecutor;
import xiaojiu.config.SaveConfig;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.PermissionHelper;
import xiaojiu.tools.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCommand implements XiaojiuCommandExecutor {
    public static Map<String, HelpMap> PlayerCommandMap = new HashMap<>();
    public static String CommandNode = "Limit";
    public static String PermissionNode = "PlayerLimit";

    @Override
    public void InitMap() {
        PlayerCommandMap.put("save", new HelpMapHandler(CommandNode, "/pl save", "xiaojiu.op.PlayerLimit.save", "通过这个方法立即保存玩家限制列表"));
        PlayerCommandMap.put("add", new HelpMapHandler(CommandNode, "/pl add true/false [玩家名]", "xiaojiu.op.PlayerLimit.add", "通过这个指令来添加一位玩家到达限制列表中 注:其中第二个参数填true则会启用保存，服务器关闭后仍有效，填false则禁用保存，服务器关闭后重置 "));
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
                if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "save")) {
                    SaveConfig.SaveLimitPlayer();
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家限制列表保存成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限保存玩家限制列表"));
                }
            } else if (strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("添加")) {
                if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "add")) {
                    if (!(strings.length < 2)) {
                        Player player = PlayerTools.GetPlayer(strings[1]);
                        if (commandSender instanceof Player) {
                            Player player1 = (Player) commandSender;
                            if (player1.getUniqueId().equals(player.getUniqueId())) {
                                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你不能添加自己进入限制列表"));
                            }
                        }
                        if (player != null) {
                            String message;
                            if (strings[2].equalsIgnoreCase("true") || strings[2].equalsIgnoreCase("离线")) {
                                message = LimitPlayerTools.add(Utils.IntegrateStr(strings), player, true);
                            } else if (strings[2].equalsIgnoreCase("false") || strings[2].equalsIgnoreCase("非离线")) {
                                message = LimitPlayerTools.add(Utils.IntegrateStr(strings), player, false);
                            } else {
                                message = "请设置是否开启保存";
                            }
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + message));
                        } else {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + strings[1] + "当前未在线或未在本子服"));
                        }
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入想要添加的玩家名"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限添加限制玩家"));
                }

            } else if (strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("remove") || strings[0].equalsIgnoreCase("删除")) {
                if (PermissionHelper.isHasPermission(commandSender, "op", PermissionNode, "remove")) {
                    if (!(strings.length < 2)) {
                        OfflinePlayer player = PlayerTools.GetPlayerOffer(strings[1]);
                        if (commandSender instanceof Player) {
                            Player player1 = (Player) commandSender;
                            if (player1.getUniqueId().equals(player.getUniqueId())) {
                                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你不能把自己从限制列表中移除"));
                            }
                        }
                        if (player != null) {
                            if (LimitPlayerTools.isPlayerIn(player.getUniqueId())) {
                                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + LimitPlayerTools.remove(player)));
                            } else {
                                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + strings[1] + "未在限制列表中"));
                            }
                        } else {
                            commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家" + strings[1] + "从未进入过本服务器或子服"));
                        }
                    } else {
                        commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入想要解除限制的玩家名"));
                    }
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限删除被限制的玩家"));
                }

            }
            return true;
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
                list.addAll(LimitPlayerTools.GetAllPlayerName(strings[0]));
            }
        }
        return list;
    }


}
