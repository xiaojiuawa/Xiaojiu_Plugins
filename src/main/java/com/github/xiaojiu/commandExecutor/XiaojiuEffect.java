package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.tools.MessageHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XiaojiuEffect implements XiaojiuCommandExecutor {
    public static final Map<String,HelpMap> EffectMap = new HashMap<>();
    public static final String CommandNode = "effect";
    public static final String PermissionNode = "effect";
    @Override
    public void InitMap() {
        EffectMap.put("add",new HelpMapHandler(CommandNode,"/xj effect add [玩家名] [药水类型] <时间> <等级>","xiaojiu.op.effect.add","使用这个指令给某个玩家添加一个药水效果"));
        EffectMap.put("remove",new HelpMapHandler(CommandNode,"/xj effect remove [玩家名] [药水类型]","xiaojiu.op.effect.remove","通过这个指令移除某个玩家的药水效果"));


    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return EffectMap;
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
        if (strings.length<3){
            commandSender.sendMessage(MessageHelper.InitMessage(Utils.getMessageCompletion("Command.noCommand")));
        }else{
            if (strings[0].equalsIgnoreCase("add")){
                Player player;
//                try{
//
//                }
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
