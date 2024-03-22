package Xiaojiu;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StartPlugins extends JavaPlugin {
    private static StartPlugins Instance;
    @Override
    public void onEnable(){
        this.getLogger().info("XiaojiuPluginOnEnable");
        Instance = this;
        Bukkit.getPluginManager().registerEvents(new EventLoader(),this);
    }
    public static StartPlugins getInstance(){
        return Instance;
    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");

    }
}
