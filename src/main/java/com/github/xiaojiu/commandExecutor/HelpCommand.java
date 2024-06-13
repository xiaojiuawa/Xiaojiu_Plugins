package com.github.xiaojiu.commandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.github.xiaojiu.Handles.Help.HelpCommandHandle;
import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.tools.MessageHelper;
import com.github.xiaojiu.tools.PermissionHelper;
import com.github.xiaojiu.tools.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpCommand implements XiaojiuCommandExecutor {

    public static final Map<String, HelpMap> helpMap = new HashMap<>();

    public static final String PermissionNode = "help";

    public static final String CommandNode = "help";

    @Override
    public void InitMap() {
        helpMap.put("help", new HelpMapHandler(CommandNode, "/xj help [命令大节点] [页面(默认为1)]", "xiaojiu.normal.help.use", "通过这个指令查看命令帮助 当你输入这个指令后，会有一个总列表出现，这时再输入总列表当作页码即可"));

    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return helpMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommandNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!PermissionHelper.isHasPermission(commandSender, false, CommandNode, "use")) {
            MessageHelper.SendNoPermissionMessage(commandSender);
            return true;
        }
        if (strings.length == 0) {
            HelpCommandHandle.SendHelps(commandSender, "", 0);
        } else {
            if (strings.length > 1 && Utils.isNumber(strings[1])) {
                HelpCommandHandle.SendHelps(commandSender, strings[0], Integer.parseInt(strings[1]));
            } else if (strings.length == 1) {
                HelpCommandHandle.SendHelps(commandSender, strings[0], 1);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        return null;
    }
}
