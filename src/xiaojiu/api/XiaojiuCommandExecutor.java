package xiaojiu.api;

import org.bukkit.command.TabExecutor;
import xiaojiu.Handles.Help.HelpMapHandler;

import java.util.Map;

public interface XiaojiuCommandExecutor extends TabExecutor {
    void InitMap();
    Map<String, HelpMap> GetHelpMap();
    String GetPermissionNode();
    String GetCommandNode();
    //todo 添加isHasPermission函数
}
