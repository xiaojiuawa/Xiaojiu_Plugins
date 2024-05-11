package xiaojiu;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.Handles.Help.HelpCommandHandle;
import xiaojiu.Handles.Save.SaveTaskManager;
import xiaojiu.Handles.Vanish.Vanish;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.config.ConfigManager;
import xiaojiu.config.LoadConfig;
import xiaojiu.config.SaveConfig;
import xiaojiu.config.Savecfg.JTPlayer;
import xiaojiu.config.Savecfg.LimitPlayer;
import xiaojiu.task.TaskLoader;
import xiaojiu.tools.Utils;

import javax.rmi.CORBA.Util;
import java.util.logging.Logger;

public class StartPlugins extends JavaPlugin {
    private static JavaPlugin Instance;
    public static PluginCommand command;
    public static Logger logger;

    @Override
    public void onEnable() {
        long startData = System.currentTimeMillis();
        Instance = this;
        logger = this.getLogger();
        this.Init();
        this.getLogger().info("XiaojiuPluginOnEnable");
        long endDate= System.currentTimeMillis();
        logger.info("xiaojiuPlugin启动完毕，用时"+ (endDate - startData)+"MS");
    }

    public static JavaPlugin getInstance() {
        return Instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");
        ConfigManager.SaveConfig();
//        SaveConfig.Save();
        Vanish.OffVanish();
    }
    public void Init(){
        Bukkit.getPluginManager().registerEvents(new EventLoader(), this);
        //Init event Listener 注册事件监听器
        Utils.Init();
        //Init Utils 初始化库
        CommonExecutorLoader.Load(this);
        HelpCommandHandle.HelpMapInit();
        //初始化命令
        ConfigManager.InitManager(this);
        //读取配置文件


    }
}
