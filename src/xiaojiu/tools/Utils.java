package xiaojiu.tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Utils {
    public static HashMap<Integer, String> WaitMap = new HashMap<>();
    public static HashMap<Integer, String> SuggestMap = new HashMap<>();
    public static HashMap<Integer, String> VanishMap = new HashMap<>();

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

    }

    public static void KickAllPlayers(Collection<? extends Player> list, String message) {
        for (Player player : list) {
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    public static void KickAllPlayers(String message) {
        KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers(), message);
    }

    public static void KickAllPlayersNoOp(String message) {
        Collection<? extends Player> list = StartPlugins.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.isOp()) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    public static void KickAllPlayersUseNode(String node, String message) {
        Collection<? extends Player> list = StartPlugins.getInstance().getServer().getOnlinePlayers();
        for (Player player : list) {
            if (player.hasPermission(node)) continue;
            player.kickPlayer(ChatColor.LIGHT_PURPLE + message);
        }
    }

    public static ArrayList<String> GetOnlinePlayerNames(@Nullable String a) {
        if (a == null) a = "";
        ArrayList<String> list = new ArrayList<>();
        for (Player player : StartPlugins.getInstance().getServer().getOnlinePlayers()) {
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
}
