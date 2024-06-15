package com.github.xiaojiu;

import com.github.xiaojiu.Handles.Help.HelpCommandHandle;
import com.github.xiaojiu.Handles.Vanish.Vanish;
import com.github.xiaojiu.commandExecutor.CommonExecutorLoader;
import com.github.xiaojiu.config.ConfigManager;
import com.github.xiaojiu.message.Message;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Xiaojiu extends JavaPlugin {
    public static PluginCommand command;
    public static Logger logger;
    private static JavaPlugin Instance;
    private static boolean isInitEd = false;
    private static Message message;

    public static JavaPlugin getInstance() {
        return Instance;
    }

    public static boolean isIsInitEd() {
        return isInitEd;
    }

    public static Message getMessage() {
        return message;
    }

    @Override
    public void onEnable() {
        long startData = System.currentTimeMillis();
        message = new Message(this);
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
