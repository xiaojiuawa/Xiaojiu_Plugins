package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.tools.MessageHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

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
            commandSender.sendMessage(MessageHelper.InitMessage(MessageHelper.InitMessage(Utils.getMessageCompletion("Command.noCommand"))));
        }else{
            Player player = Xiaojiu.getInstance().getServer().getPlayer(strings[1]);
            if (player==null){
                commandSender.sendMessage(MessageHelper.InitMessage(Utils.getMessageCompletion("Player.notFind")));
                return true;
            }
            if (strings[0].equalsIgnoreCase("add")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(strings[2]),Integer.MAX_VALUE,1,true));
                player.sendMessage(MessageHelper.InitMessage(String.format(Utils.getMessageCompletion("Effect.add.success"),strings[2],"永久")));
            } else if (strings[0].equalsIgnoreCase("remove")) {
                player.removePotionEffect(PotionEffectType.getByName(strings[2]));
                player.sendMessage(MessageHelper.InitMessage(String.format(Utils.getMessageCompletion("Effect.remove.success"),strings[2])));
            }
        }
//        UUID

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length==1){
            this.GetHelpMap().forEach((string, helpMap) -> {
                if (strings[0].toLowerCase().startsWith(string)) list.add(string);
            });
        } else if (strings.length==2) {
            Xiaojiu.getInstance().getServer().getOnlinePlayers().forEach(player -> {
                if (strings[1].toLowerCase().startsWith(player.getName())) list.add(player.getName());
            });
        } else if (strings.length==3) {
            for (PotionEffectType value : PotionEffectType.values()) {
                if (strings[2].toLowerCase().startsWith(value.getName().toLowerCase())) list.add(value.getName().toLowerCase());
            }
        }
        return list;
    }
}
