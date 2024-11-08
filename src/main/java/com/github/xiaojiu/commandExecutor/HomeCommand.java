package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeCommand implements XiaojiuCommandExecutor {
    public static final Map<String,HelpMap> HomeMap = new HashMap<>();
    public static final String CommandNode = "home";
    public static final String PermissionNode = "Home";
    @Override
    public void InitMap() {

    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return HomeMap;
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
        if (strings.length<2) {
            commandSender.sendMessage(PostHelper.InitMessage(ChatColor.RED+ MessageHelper.getMessageCompletion("message.Command.paramWrong")));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
