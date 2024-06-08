package com.github.xiaojiu.xiaojiuMain.tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.github.xiaojiu.xiaojiuMain.Xiaojiu;

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
     *  初始化Utils类，初始化各类集合map
     */
    public static void Init() {
        WaitMap.put(0, "down");
        for (int i=1;i<=10;i++){
            WaitMap.put(i,String.format(getMessageCompletion("WaitMap","message"),i,"%s"));
        }

        WaitMap.put(30, String.format(getMessageCompletion("WaitMap","message"),30,"%s"));
        WaitMap.put(60, String.format(getMessageCompletion("WaitMap","message"),60,"%s"));
        WaitMap.put(120, String.format(getMessageCompletion("WaitMap","message"),120,"%s"));
        WaitMap.put(1800, String.format(getMessageCompletion("WaitMap","message"),1800,"%s"));
        WaitMap.put(3600, String.format(getMessageCompletion("WaitMap","message"),3600,"%s"));
        //等待map初始化 用于定时任务
        SuggestMap.put(0,getMessageCompletion("SuggestMap","down"));
        SuggestMap.put(5,String.format(getMessageCompletion("SuggestMap","message"),"%s",5));
        SuggestMap.put(10,String.format(getMessageCompletion("SuggestMap","message"),"%s",10));
        SuggestMap.put(20,String.format(getMessageCompletion("SuggestMap","message"),"%s",20));
        SuggestMap.put(40,String.format(getMessageCompletion("SuggestMap","message"),"%s",40));
        SuggestMap.put(60,String.format(getMessageCompletion("SuggestMap","message"),"%s",60));
        SuggestMap.put(80,String.format(getMessageCompletion("SuggestMap","message"),"%s",80));
        SuggestMap.put(100,String.format(getMessageCompletion("SuggestMap","message"),"%s",100));
        SuggestMap.put(120,String.format(getMessageCompletion("SuggestMap","message"),"%s",120));
        //投票map初始化 用于投票任务
//        SuggestMap.put(0, "服务器投票%s已结束");
//        SuggestMap.put(5, "服务器投票%s已开始,投票将在5秒后结束");
//        SuggestMap.put(10, "服务器投票%s已开始,投票将在10秒后结束");
//        SuggestMap.put(20, "服务器投票%s已开始,投票将在20秒后结束");
//        SuggestMap.put(40, "服务器投票%s已开始,投票将在40秒后结束");
//        SuggestMap.put(60, "服务器投票%s已开始,投票将在60秒后结束");
//        SuggestMap.put(80, "服务器投票%s已开始,投票将在80秒后结束");
//        SuggestMap.put(100, "服务器投票%s已开始,投票将在100秒后结束");
//        SuggestMap.put(120, "服务器投票%s已开始,投票将在120秒后结束");
        VanishMap.put(0,getMessageCompletion("VanishMap","down"));
        VanishMap.put(5,String.format(getMessageCompletion("VanishMap","message"),"%s",5));
        VanishMap.put(10,String.format(getMessageCompletion("VanishMap","message"),"%s",10));
        VanishMap.put(60,String.format(getMessageCompletion("VanishMap","message"),"%s",60));
        VanishMap.put(120,String.format(getMessageCompletion("VanishMap","message"),"%s",120));

//        VanishMap.put(0, "%s已经结束");
//        VanishMap.put(5, "%s将在5秒后结束");
//        VanishMap.put(10, "%s将在10秒后结束");
//        VanishMap.put(60, "%s将在1分钟后结束");
//        VanishMap.put(120, "%s将在2分钟后结束");

    }

    /**
     * 踢出指定集合类中的玩家
     * @param list 需要被踢除的玩家列表
     * @param message 踢出时向玩家发送的文字
     */
    public static void KickAllPlayers(Collection<? extends Player> list, String message) {
        for (Player player : list) {
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    /**
     * 踢出当前服务器所有的玩家
     * @param message 踢出时向玩家发送的文字
     */
    public static void KickAllPlayers(String message) {
        KickAllPlayers(Xiaojiu.getInstance().getServer().getOnlinePlayers(), message);
    }

    public static void KickAllPlayersNoOp(String message) {
        Collection<? extends Player> list = Xiaojiu.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.isOp()) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    public static void KickAllPlayersUseNode(String node, String message) {
        Collection<? extends Player> list = Xiaojiu.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.hasPermission(node)) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    public static ArrayList<String> GetOnlinePlayerNames( String a) {
        if (a == null) a = "";
        ArrayList<String> list = new ArrayList<>();
        for (Player player : Xiaojiu.getInstance().getServer().getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(a.toLowerCase())) list.add(player.getName());
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

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static String getMessage(String node){
        return Xiaojiu.getMessage().getMessage(node);
    }
    public static String getMessageCompletion(String... nodes){
        StringBuilder builder = new StringBuilder();
        if (!nodes[0].equalsIgnoreCase("message")){
            builder.append("message.");
        }
        for (String node : nodes) {
            builder.append(node);
            builder.append(".");
        }
        builder.delete(builder.length()-1, builder.length());
        return getMessage(builder.toString());
    }
//    public static String getMessageNode(String )
}
