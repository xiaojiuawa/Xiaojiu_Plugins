package com.github.xiaojiu.Handles.Vanish;

import com.github.xiaojiu.Xiaojiu;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vanish {
    public static final List<UUID> VanishPlayers = new ArrayList<>();

    /**
     * 设置一个玩家的影身状态
     * @param player 玩家
     * @param mode 要设置的状态
     * @return 是否成功
     */
    public static boolean VanishPlayer(Player player, boolean mode) {
        UUID uuid = player.getUniqueId();
        if (VanishPlayers.contains(uuid) && mode || !VanishPlayers.contains(uuid) && !mode) return false;
        //检查玩家是否已经影身，或者没有隐身，避免无效的设置
        if (mode) {
            VanishPlayers.add(uuid);
            if (player.hasPermission("xiaojiu.op.vanish.effect")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));
            }
//            player.setPlayerListName("");
            player.getPlayer().getPlayer().getPlayer().getPlayer();
            player.setDisplayName("");
//            player.setPlayerListName();
            //这里不知道为什么，你将它的显示名称设置为null会报错，设置为空就还会显示头像
//            CraftServer server = (CraftServer) player.getServer();
//            server.getHandle();
//            player.saveData();
            Xiaojiu.getInstance().getServer().getOnlinePlayers().forEach(player2 -> {
                if (!player2.hasPermission("xiaojiu.op.vanish.see")) {
                    player2.hidePlayer(Xiaojiu.getInstance(), player);
                }
            });
        } else {
            VanishPlayers.remove(uuid);
            if (player.hasPermission("xiaojiu.op.vanish.effect")) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            Xiaojiu.getInstance().getServer().getOnlinePlayers().forEach(player1 -> player1.showPlayer(Xiaojiu.getInstance(), player));

        }
        return true;
    }

    /**
     * 向一个玩家隐藏目前所有的隐身玩家
     * 一般在新玩家进入时调用
     * @param player 目标玩家
     */
    public static void hideNewPlayer(Player player) {
        getVanishedPlayers().forEach(player1 -> player.hidePlayer(Xiaojiu.getInstance(), player1));
    }

    /**
     * 获取所有已经影身玩家的列表
     * @return 玩家列表
     */
    public static List<Player> getVanishedPlayers() {
        List<Player> list = new ArrayList<>();
        VanishPlayers.forEach(uuid -> list.add(Xiaojiu.getInstance().getServer().getPlayer(uuid)));
        return list;
    }

    /**
     * 关闭影身功能
     * 在卸载插件时调用
     */
    public static void OffVanish() {
        VanishPlayers.forEach(uuid -> {
            Player player = Xiaojiu.getInstance().getServer().getPlayer(uuid);
            if (player==null) return;
            //如果玩家不在线则跳过
            VanishPlayer(player, false);
        });
    }
//    public static boolean VanishPlayer(UUID uuid,boolean mode){
//        if (VanishPlayers.contains(uuid)&&mode || !VanishPlayers.contains(uuid)&&!mode) return false;
//        if (mode){
//            StartPlugins.getInstance().getServer().getOnlinePlayers().forEach(player -> {
//                player.canSee()
//            });
//        }
//    }
}
