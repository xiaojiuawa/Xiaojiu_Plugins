package xiaojiu.api;

import org.bukkit.command.TabExecutor;

import java.util.Map;

public interface XiaojiuCommandExecutor extends TabExecutor {
    void InitMap();

    Map<String, HelpMap> GetHelpMap();

    String GetPermissionNode();

    String GetCommandNode();
//    boolean isHasPermission(boolean isOp,String... nodes);
}
