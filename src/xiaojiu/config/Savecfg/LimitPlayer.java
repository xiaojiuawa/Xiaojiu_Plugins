package xiaojiu.config.Savecfg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LimitPlayer implements ConfigurationSerializable {
    public long leastSigBits;
    public long mostSigBits;
    public String Name;
    public String message;

    public LimitPlayer(long leastSigBits, long mostSigBits, String name, String message) {
        this.leastSigBits = leastSigBits;
        this.mostSigBits = mostSigBits;
        this.Name = name;
        this.message = message;
    }
    public LimitPlayer(UUID uuid,String name,String message){
        this.message=message;
        this.mostSigBits= uuid.getMostSignificantBits();
        this.leastSigBits= uuid.getLeastSignificantBits();
        this.Name=name;
    }
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("leastSigBits", leastSigBits);
        map.put("mostSigBits", mostSigBits);
        map.put("name", Name);
        map.put("message", message);
        return map;
    }

    public static LimitPlayer deserialize(Map<String, Object> map) {
        return new LimitPlayer((Long) map.get("leastSigBits"), (long) map.get("mostSigBits"), (String) map.get("name"), (String) map.get("message"));
    }
}
