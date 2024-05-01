package xiaojiu.Handles.Vanish;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xiaojiu.StartPlugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vanish {
    public static List<UUID> VanishPlayers = new ArrayList<>();

    public static boolean VanishPlayer(Player player, boolean mode) {
        UUID uuid = player.getUniqueId();
        if (VanishPlayers.contains(uuid) && mode || !VanishPlayers.contains(uuid) && !mode) return false;
        if (mode) {
            VanishPlayers.add(uuid);
            if (player.hasPermission("xiaojiu.op.vanish.effect")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));
            }
            StartPlugins.getInstance().getServer().getOnlinePlayers().forEach(player2 -> {
                if (!player2.hasPermission("xiaojiu.op.vanish.see")) {
                    player2.hidePlayer(StartPlugins.getInstance(), player);
                }
            });
        } else {
            VanishPlayers.remove(uuid);
            if (player.hasPermission("xiaojiu.op.vanish.effect")) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            StartPlugins.getInstance().getServer().getOnlinePlayers().forEach(player1 -> {
                player1.showPlayer(StartPlugins.getInstance(), player);
            });

        }
        return true;
    }

    public static void hideNewPlayer(Player player) {
        getVanishedPlayers().forEach(player1 -> {
            player.hidePlayer(StartPlugins.getInstance(), player1);
        });
    }

    public static List<Player> getVanishedPlayers() {
        List<Player> list = new ArrayList<>();
        VanishPlayers.forEach(uuid -> {
            list.add(StartPlugins.getInstance().getServer().getPlayer(uuid));
        });
        return list;
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
