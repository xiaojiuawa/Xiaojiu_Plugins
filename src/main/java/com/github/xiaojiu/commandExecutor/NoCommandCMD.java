package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.tools.PostHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class NoCommandCMD implements XiaojiuCommandExecutor {
    @Override
    public void InitMap() {

    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return null;
    }

    @Override
    public String GetPermissionNode() {
        return null;
    }

    @Override
    public String GetCommandNode() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED + "指令名输入错误"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
