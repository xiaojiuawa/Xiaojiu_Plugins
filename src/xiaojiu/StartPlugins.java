package xiaojiu;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.Handles.Help.HelpCommandHandle;
import xiaojiu.Handles.Save.SaveTaskManager;
import xiaojiu.Handles.Vanish.Vanish;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.config.LoadConfig;
import xiaojiu.config.SaveConfig;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.task.TaskLoader;
import xiaojiu.tools.Utils;

import java.util.Date;
import java.util.logging.Logger;

public class StartPlugins extends JavaPlugin {
    private static StartPlugins Instance;
    public static PluginCommand command;
    public static Logger logger;

    @Override
    public void onEnable() {
        long startData = System.currentTimeMillis();
        Instance = this;
        logger = this.getLogger();
        Bukkit.getPluginManager().registerEvents(new EventLoader(), this);
//        Bukkit.getPluginManager().registerEvents(new EventListener(),this);
        CommonExecutorLoader.Load(this);
        Utils.Init();
        TaskLoader.Start(this);
        ConfigurationSerialization.registerClass(LimitPlayer.class);
        ConfigurationSerialization.registerClass(JTPlayer.class);
        SaveConfig.OnEnable(this);
        LoadConfig.ReadAllFile();
        HelpCommandHandle.HelpMapInit();
        SaveTaskManager.InitManager();
//        BasicSQL.Init();

        this.getLogger().info("XiaojiuPluginOnEnable");
        long endDate= System.currentTimeMillis();
        logger.info("xiaojiuPlugin启动完毕，用时"+ (endDate - startData)+"MS");
    }

    public static StartPlugins getInstance() {
        return Instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");
        SaveConfig.Save();
        Vanish.OffVanish();
    }
}
