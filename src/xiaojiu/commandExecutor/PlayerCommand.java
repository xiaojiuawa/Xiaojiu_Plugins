package xiaojiu.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;
import xiaojiu.config.SaveConfig;
import xiaojiu.tools.LimitPlayerTools;

import java.io.IOException;

public class PlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length!=0){
            Player player= (Player) commandSender;
            if (strings[0].equalsIgnoreCase("save")){
                try {
                    SaveConfig.Save();
                    commandSender.sendMessage("save");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (strings[0].equalsIgnoreCase("add")||strings[0].equalsIgnoreCase("添加")){
                if (strings[1]!=null){
                    Player player1 = GetPlayer(strings[1]);
                    if (player1!=null){
                        String message;
                        if (strings[2].equalsIgnoreCase("true")||strings[2].equalsIgnoreCase("离线")){
                            message =  LimitPlayerTools.add(IntegrateStr(strings),player1,true);
                        }else{
                            message =  LimitPlayerTools.add(IntegrateStr(strings),player1,false);
                        }
                        player.sendMessage(ChatColor.LIGHT_PURPLE+message);
                    }else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE+"玩家"+strings[1]+"当前未在线或未在本子服");
                    }

                }else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"请输入需要添加的玩家名");
                }
            }
            if (strings[0].equalsIgnoreCase("del")||strings[0].equalsIgnoreCase("remove")||strings[0].equalsIgnoreCase("解除")){
                if (strings[1]!=null){
                    OfflinePlayer player1 = GetPlayerOffer(strings[1]);
                    if (LimitPlayerTools.isPlayerIn(player1.getUniqueId())){
                        commandSender.sendMessage(ChatColor.LIGHT_PURPLE+LimitPlayerTools.remove(player1));
                    }else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE+"玩家"+strings[1]+"当前未在线或未在本子服");
                    }
                }else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"请输入想解除限制的玩家名");
                }
            }
            return true;
        }
        return false;
    }
    public static String IntegrateStr(String[] strings){
        StringBuilder ans= new StringBuilder();
        for (int i=3;i<strings.length;i++){
            ans.append(strings[i]);
        }
        return ans.toString();
    }
    public static Player GetPlayer(String PlayerName){
        return StartPlugins.getInstance().getServer().getPlayer(PlayerName);
    }
    public static OfflinePlayer GetPlayerOffer(String PlayerName){
        return StartPlugins.getInstance().getServer().getOfflinePlayer(PlayerName);
    }
}
