package com.github.xiaojiu.config.Savecfg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LimitPlayer implements ConfigurationSerializable {
    public final long leastSigBits;
    public final long mostSigBits;
    public final String Name;
    public final String message;

    public LimitPlayer(long leastSigBits, long mostSigBits, String name, String message) {
        this.leastSigBits = leastSigBits;
        this.mostSigBits = mostSigBits;
        this.Name = name;
        this.message = message;
    }

    public LimitPlayer(UUID uuid, String name, String message) {
        this.message = message;
        this.mostSigBits = uuid.getMostSignificantBits();
        this.leastSigBits = uuid.getLeastSignificantBits();
        this.Name = name;
    }

    public static LimitPlayer deserialize(Map<String, Object> map) {
        return new LimitPlayer((long) map.get("leastSigBits"), (long) map.get("mostSigBits"), (String) map.get("name"), (String) map.get("message"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitPlayer that = (LimitPlayer) o;
        return leastSigBits == that.leastSigBits && mostSigBits == that.mostSigBits && Objects.equals(Name, that.Name) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leastSigBits, mostSigBits, Name, message);
    }
}
