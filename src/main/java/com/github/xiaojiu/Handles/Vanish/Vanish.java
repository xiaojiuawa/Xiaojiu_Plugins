package com.github.xiaojiu.Handles.Vanish;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.github.xiaojiu.Xiaojiu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vanish {
    public static final List<UUID> VanishPlayers = new ArrayList<>();

    public static boolean VanishPlayer(Player player, boolean mode) {
        UUID uuid = player.getUniqueId();
        if (VanishPlayers.contains(uuid) && mode || !VanishPlayers.contains(uuid) && !mode) return false;
        if (mode) {
            VanishPlayers.add(uuid);
            if (player.hasPermission("xiaojiu.op.vanish.effect")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));
            }
//            player.setPlayerListName("");
            player.getPlayer().getPlayer().getPlayer().getPlayer();
            player.setDisplayName("");
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

    public static void hideNewPlayer(Player player) {
        getVanishedPlayers().forEach(player1 -> player.hidePlayer(Xiaojiu.getInstance(), player1));
    }

    public static List<Player> getVanishedPlayers() {
        List<Player> list = new ArrayList<>();
        VanishPlayers.forEach(uuid -> list.add(Xiaojiu.getInstance().getServer().getPlayer(uuid)));
        return list;
    }

    public static void OffVanish() {
        VanishPlayers.forEach(uuid -> {
            Player player = Xiaojiu.getInstance().getServer().getPlayer(uuid);
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
