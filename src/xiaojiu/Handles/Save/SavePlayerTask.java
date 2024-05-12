package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class SavePlayerTask extends BasicSaveHandles{
    public SavePlayerTask(int taskid, JavaPlugin plugin,Player player,String... args){
        super(taskid, plugin,player,"player", args);
        this.canAsynchronously=true;
//        this.name="Player";
    }
    @Override
    public void run() {
//        MessageHelper.SendMessageAllPlayer("1");
        if (args.length!=0){
            List<String> list = new ArrayList<>();
            for (String arg : args) {
                Player player2 =  plugin.getServer().getPlayer(arg);
                if (player2==null){
                    list.add(arg);
                }else{
                    player2.saveData();
                    StartPlugins.logger.info("玩家"+player2.getName()+"数据保存完毕");
                }
            }
            if (!list.isEmpty()){
                this.player.sendMessage(MessageHelper.InitMessage(Arrays.toString(list.toArray())+"未在线或未在本子服"));
            }
        }else{
            plugin.getServer().getOnlinePlayers().forEach(player1 -> {
                player1.saveData();
                StartPlugins.logger.info("玩家"+player1.getName()+"数据保存完毕");
            });

        }

    }
}
