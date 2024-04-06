package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;
import xiaojiu.config.SaveConfig;
import xiaojiu.tools.LimitPlayerTools;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.Until;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) {
            if (strings[0].equalsIgnoreCase("save") || strings[0].equalsIgnoreCase("保存")) {
                if (commandSender.hasPermission("xiaojiu.playerLimit.save")) {
                    SaveConfig.SaveLimitPlayer();
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "玩家限制列表保存成功"));
                } else {
                    commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你没有权限保存玩家限制列表"));
                }
            } else if (strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("添加")) {
                if (commandSender.hasPermission("xiaojiu.playerLimit.add")) {
                    if (!(strings.length < 2)) {
                        Player player = GetPlayer(strings[1]);
                        if (commandSender instanceof Player) {
                            Player player1 = (Player) commandSender;
                            if (player1.getUniqueId().equals(player.getUniqueId())) {
                                commandSender.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "你不能添加自己进入限制列表"));
                            }
                        }
                        if (player != null) {
                            String message;
                            if (strings[2].equalsIgnoreCase("true") || strings[2].equalsIgnoreCase("离线")) {
                                message = LimitPlayerTools.add(IntegrateStr(strings), player, true);
                            } else if (strings[2].equalsIgnoreCase("false") || strings[2].equalsIgnoreCase("非离线")) {
                                message = LimitPlayerTools.add(IntegrateStr(strings), player, false);
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
                if (commandSender.hasPermission("xiaojiu.PlayerLimit.remove")) {
                    if (!(strings.length < 2)) {
                        OfflinePlayer player = GetPlayerOffer(strings[1]);
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
        System.out.println(strings.length);
        System.out.println(Arrays.toString(strings));
        if (strings.length == 1) {
            if (commandSender.hasPermission("xiaojiu.PlayerLimit.save") && ("保存".startsWith(strings[0])))
                list.add("保存");
            if (commandSender.hasPermission("xiaojiu.PlayerLimit.add") && ("添加".startsWith(strings[0])))
                list.add("添加");
            if (commandSender.hasPermission("xiaojiu.PlayerLimit.remove") && ("删除".startsWith(strings[0])))
                list.add("删除");
        } else if (strings.length == 2) {
            if (commandSender.hasPermission("xiaojiu.PlayerLimit.add") && strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("添加")) {
                list.addAll(Until.GetOnlinePlayerNames(strings[1]));
            } else if (commandSender.hasPermission("xiaojiu.PlayerLimit.remove") && strings[0].equalsIgnoreCase("删除") || strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("remove")) {
                list.addAll(LimitPlayerTools.GetAllPlayerName(strings[0]));
            }
        }
        return list;
    }

    public static String IntegrateStr(String[] strings) {
        StringBuilder ans = new StringBuilder();
        for (int i = 3; i < strings.length; i++) {
            ans.append(strings[i]).append(' ');
        }
        return ans.toString();
    }

    public static Player GetPlayer(String PlayerName) {
        return StartPlugins.getInstance().getServer().getPlayer(PlayerName);
    }

    public static OfflinePlayer GetPlayerOffer(String PlayerName) {
        return StartPlugins.getInstance().getServer().getOfflinePlayer(PlayerName);
    }


}
