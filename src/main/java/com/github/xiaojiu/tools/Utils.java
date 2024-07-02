package com.github.xiaojiu.tools;

import com.github.xiaojiu.Handles.TpsTask.TpsTask;
import com.github.xiaojiu.Xiaojiu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author xiaojiu
 * @version 1.2
 */
public class Utils {
    public static final HashMap<Integer, String> WaitMap = new HashMap<>();
    public static final HashMap<Integer, String> SuggestMap = new HashMap<>();
    public static final HashMap<Integer, String> VanishMap = new HashMap<>();

    /**
     * 初始化Utils类，初始化各类集合map
     */
    public static void Init() {
        WaitMap.put(0, "down");
        WaitMap.put(1, "服务器将在1秒后%s");
        WaitMap.put(2, "服务器将在2秒后%s");
        WaitMap.put(3, "服务器将在3秒后%s");
        WaitMap.put(4, "服务器将在4秒后%s");
        WaitMap.put(5, "服务器将在5秒后%s");
        WaitMap.put(6, "服务器将在6秒后%s");
        WaitMap.put(7, "服务器将在7秒后%s");
        WaitMap.put(8, "服务器将在8秒后%s");
        WaitMap.put(9, "服务器将在9秒后%s");
        WaitMap.put(10, "服务器将在10秒后%s");
        WaitMap.put(30, "服务器将在30秒后%s");
        WaitMap.put(60, "服务器将在1分钟后%s");
        WaitMap.put(120, "服务器将在2分钟后%s");
        WaitMap.put(1800, "服务器将在30分钟后%s");
        WaitMap.put(3600, "服务器将在一个小时后%s");
        SuggestMap.put(0, "服务器投票%s已结束");
        SuggestMap.put(5, "服务器投票%s已开始,投票将在5秒后结束");
        SuggestMap.put(10, "服务器投票%s已开始,投票将在10秒后结束");
        SuggestMap.put(20, "服务器投票%s已开始,投票将在20秒后结束");
        SuggestMap.put(40, "服务器投票%s已开始,投票将在40秒后结束");
        SuggestMap.put(60, "服务器投票%s已开始,投票将在60秒后结束");
        SuggestMap.put(80, "服务器投票%s已开始,投票将在80秒后结束");
        SuggestMap.put(100, "服务器投票%s已开始,投票将在100秒后结束");
        SuggestMap.put(120, "服务器投票%s已开始,投票将在120秒后结束");
        VanishMap.put(0, "%s已经结束");
        VanishMap.put(5, "%s将在5秒后结束");
        VanishMap.put(10, "%s将在10秒后结束");
        VanishMap.put(60, "%s将在1分钟后结束");
        VanishMap.put(120, "%s将在2分钟后结束");
        Xiaojiu.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Xiaojiu.getInstance(),new TpsTask(Xiaojiu.getInstance()),100,1200);
    }

    /**
     * 踢出指定集合类中的玩家
     *
     * @param list    需要被踢除的玩家列表
     * @param message 踢出时向玩家发送的文字
     */
    public static void KickAllPlayers(Collection<? extends Player> list, String message) {
        for (Player player : list) {
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    /**
     * 踢出当前服务器所有的玩家
     *
     * @param message 踢出时向玩家发送的文字
     */
    public static void KickAllPlayers(String message) {
        KickAllPlayers(Xiaojiu.getInstance().getServer().getOnlinePlayers(), message);
    }

    /**
     * 踢出所有玩家中除过op的所有玩家
     * @param message 踢出时向玩家发送的文字
     */
    public static void KickAllPlayersNoOp(String message) {
        Collection<? extends Player> list = Xiaojiu.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.isOp()) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    /**
     * 踢出所有玩家中除过含有特定权限节点的玩家
     * @param node 免踢出的权限节点
     * @param message 踢出时向玩家发送的文字
     */

    public static void KickAllPlayersUseNode(String node, String message) {
        Collection<? extends Player> list = Xiaojiu.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.hasPermission(node)) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    /**
     * 获得有所玩家中名称以{a}开头的玩家
     * @param a 字符串
     * @return 匹配的玩家名称列表
     */
    public static ArrayList<String> GetOnlinePlayerNames(String a) {
        if (a == null) a = "";
        ArrayList<String> list = new ArrayList<>();
        for (Player player : Xiaojiu.getInstance().getServer().getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(a.toLowerCase())) list.add(player.getName());
        }
        return list;
    }

    /**
     * 获得字符串数组任意下标之后的所有值的字符串,(使用' '分隔)
     * @param strings 需要的字符串数组
     * @param start 开始的下标
     * @return 处理之后的字符串
     */
    public static String IntegrateString(String[] strings,int start) {
        StringBuilder ans = new StringBuilder();
        for (int i = start; i < strings.length; i++) {
            ans.append(strings[i]).append(' ');
        }
        return ans.toString();
    }

    /**
     * 判断任意一个字符串是否为数字
     * @param str 输入字符串
     * @return 是否为数字
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
