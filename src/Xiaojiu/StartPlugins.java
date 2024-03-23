package Xiaojiu;

import Xiaojiu.CommandExecutor.RestartServerCommand;
//import me.lucko.spark.bukkit.CommandMapUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class StartPlugins extends JavaPlugin {
    private static StartPlugins Instance;
    public static PluginCommand command;
    @Override
    public void onEnable(){
        this.getLogger().info("XiaojiuPluginOnEnable");
        Instance = this;
        Bukkit.getPluginManager().registerEvents(new EventLoader(),this);
        getCommand("restartserver").setExecutor(new RestartServerCommand());
//        Bukkit.getPluginCommand("restart").setExecutor(new RestartServerCommand());
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
