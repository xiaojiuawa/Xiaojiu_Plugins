package xiaojiu.tools;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;

public class PlayerTools {
    public static Player GetPlayer(String PlayerName) {
        return StartPlugins.getInstance().getServer().getPlayer(PlayerName);
    }

    public static OfflinePlayer GetPlayerOffer(String PlayerName) {
        return StartPlugins.getInstance().getServer().getOfflinePlayer(PlayerName);
    }
}
