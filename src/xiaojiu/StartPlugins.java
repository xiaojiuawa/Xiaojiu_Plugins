package xiaojiu;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.Log.EventListener;
import xiaojiu.Log.mysql.BasicSQL;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.commandExecutor.HelpCommand;
import xiaojiu.config.LoadConfig;
import xiaojiu.config.SaveConfig;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.task.TaskLoader;
import xiaojiu.tools.Until;

import java.util.logging.Logger;

public class StartPlugins extends JavaPlugin {
    private static StartPlugins Instance;
    public static PluginCommand command;
    public static Logger logger;

    @Override
    public void onEnable() {
        this.getLogger().info("XiaojiuPluginOnEnable");
        Instance = this;
        logger = this.getLogger();
        Bukkit.getPluginManager().registerEvents(new EventLoader(), this);
        Bukkit.getPluginManager().registerEvents(new EventListener(),this);
        CommonExecutorLoader.Load(this);
        Until.Init();
        TaskLoader.Start(this);
        ConfigurationSerialization.registerClass(LimitPlayer.class);
        ConfigurationSerialization.registerClass(JTPlayer.class);
        SaveConfig.OnEnable(this);
        LoadConfig.ReadAllFile();
        HelpCommand.HelpMapInit();
        BasicSQL.Init();
    }

    public static StartPlugins getInstance() {
        return Instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");
        SaveConfig.Save();

    }
}
