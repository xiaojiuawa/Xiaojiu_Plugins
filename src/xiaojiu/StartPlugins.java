package xiaojiu;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.Handles.Help.HelpCommandHandle;
import xiaojiu.Handles.Vanish.Vanish;
import xiaojiu.commandExecutor.CommonExecutorLoader;
import xiaojiu.config.ConfigManager;
import xiaojiu.tools.Utils;

import java.util.logging.Logger;

public class StartPlugins extends JavaPlugin {
    public static PluginCommand command;
    public static Logger logger;
    private static JavaPlugin Instance;
    private static boolean isInitEd = false;

    public static JavaPlugin getInstance() {
        return Instance;
    }

    public static boolean isIsInitEd() {
        return isInitEd;
    }

    @Override
    public void onEnable() {
        long startData = System.currentTimeMillis();
        Instance = this;
        logger = this.getLogger();
        this.Init();
        this.getLogger().info("XiaojiuPluginOnEnable");
        long endDate = System.currentTimeMillis();
        logger.info("xiaojiuPlugin启动完毕，用时" + (endDate - startData) + "MS");
        isInitEd = true;
    }

    @Override
    public void onDisable() {
        isInitEd = false;
        Bukkit.getScheduler().cancelTasks(this);
        this.getLogger().info("XiaojiuPluginOnDisable");
        ConfigManager.SaveConfig();
//        SaveConfig.Save();
        Vanish.OffVanish();
    }

    public void Init() {
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
