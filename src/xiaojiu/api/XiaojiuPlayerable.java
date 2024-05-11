package xiaojiu.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface XiaojiuPlayerable {
    void setPlayer(Player player);
    void setPlayer(String playerName);
    void setPlayer(UUID uuid);

    Player getPlayer();


}
