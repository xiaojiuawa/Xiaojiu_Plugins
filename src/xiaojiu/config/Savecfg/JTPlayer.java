package xiaojiu.config.Savecfg;

//import com.mysql.fabric.xmlrpc.base.Data;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class JTPlayer implements ConfigurationSerializable {
    public Date LastJoinTime;
    public UUID uuid;
    public String PlayerName;
    public long leastSigBits;
    public long mostSigBits;

    public JTPlayer(Date lastJoinTime, UUID uuid, String playerName) {
        this.LastJoinTime = lastJoinTime;
        this.uuid = uuid;
        this.PlayerName = playerName;
        this.leastSigBits = uuid.getLeastSignificantBits();
        this.mostSigBits = uuid.getMostSignificantBits();
    }

    public static JTPlayer deserialize(Map<String, Object> map) {
        Date date = new Date();
        UUID uuid1 = new UUID((long) map.get("mostSigBits"), (long) map.get("leastSigBits"));
        date.setTime((long) map.get("Date"));
        return new JTPlayer(date, uuid1, (String) map.get("name"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("Date", LastJoinTime.getTime());
        map.put("leastSigBits", leastSigBits);
        map.put("mostSigBits", mostSigBits);
        map.put("name", PlayerName);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTPlayer player = (JTPlayer) o;
        return leastSigBits == player.leastSigBits && mostSigBits == player.mostSigBits && Objects.equals(LastJoinTime, player.LastJoinTime) && Objects.equals(uuid, player.uuid) && Objects.equals(PlayerName, player.PlayerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LastJoinTime, uuid, PlayerName, leastSigBits, mostSigBits);
    }
}
