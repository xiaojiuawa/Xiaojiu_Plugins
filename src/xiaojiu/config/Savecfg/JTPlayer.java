package xiaojiu.config.Savecfg;

//import com.mysql.fabric.xmlrpc.base.Data;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("Date", LastJoinTime.getTime());
        map.put("leastSigBits", leastSigBits);
        map.put("mostSigBits", mostSigBits);
        map.put("name", PlayerName);
        return map;
    }

    public static JTPlayer deserialize(Map<String, Object> map) {
        Date date = new Date();
        UUID uuid1 = new UUID((long) map.get("mostSigBits"), (long) map.get("leastSigBits"));
        date.setTime((long) map.get("Date"));
        return new JTPlayer(date, uuid1, (String) map.get("name"));
    }
}
