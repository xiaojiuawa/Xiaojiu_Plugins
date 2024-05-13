package xiaojiu.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface XiaojiuPlayer {
    void setPlayer(Player player);
    void setPlayer(String playerName);
    void setPlayer(UUID uuid);

    UUID getPlayerUUID();
    OfflinePlayer getPlayer();

}
