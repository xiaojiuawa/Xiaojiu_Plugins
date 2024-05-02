package xiaojiu.api;

import org.bukkit.command.TabExecutor;
import xiaojiu.Handles.Help.HelpMap;

import java.util.Map;

public interface XiaojiuCommandExecutor extends TabExecutor {
    public void InitMap();
    public Map<String, HelpMap> GetHelpMap();
    public String GetPermissionNode();
    public String GetCommandNode();
}
