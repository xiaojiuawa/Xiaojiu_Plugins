package xiaojiu;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.commandExecutor.RestartServerCommand;
//import me.lucko.spark.bukkit.CommandMapUtil;
import xiaojiu.commandExecutor.SafeGuardCommand;
import xiaojiu.config.SaveConfig;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.task.TaskLoader;
import xiaojiu.task.TpsTask;
import xiaojiu.tools.LimitPlayerTools;
import xiaojiu.tools.Until;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class StartPlugins extends JavaPlugin {
    private static StartPlugins Instance;
    public static PluginCommand command;
    @Override
    public void onEnable(){
        this.getLogger().info("XiaojiuPluginOnEnable");
        Instance = this;
        Bukkit.getPluginManager().registerEvents(new EventLoader(),this);
        CommonExecutorLoader.Load(this);
        Until.Init();
        TaskLoader.Start(this);
        ConfigurationSerialization.registerClass(LimitPlayer.class);
        SaveConfig.OnEnable(this);
        LimitPlayerTools.ReadPlayers(this);
    }
    public static StartPlugins getInstance(){
        return Instance;
    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");
        try {
            SaveConfig.Save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
