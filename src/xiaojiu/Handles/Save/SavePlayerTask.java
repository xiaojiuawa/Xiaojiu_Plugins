package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;

import java.util.TimerTask;

public class SavePlayerTask extends BasicSaveHandles{
    private static final String Name="Player";
    public SavePlayerTask(){
        this.canAsynchronously=true;
    }
    @Override
    public void run() {
        StartPlugins.getInstance().getServer().getOnlinePlayers().forEach(Player::saveData);
    }

    @Override
    public void specialParametersTask(String parameter, boolean isAsynchronously, long time, long delay) {
        if (isAsynchronously && !this.canAsynchronously) return;
        if (isAsynchronously){
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Player player = StartPlugins.getInstance().getServer().getPlayer(parameter);
                    if (player==null) return;
                    player.saveData();
                }
            },delay*1000,time*1000);
        }else{
            Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Player player = StartPlugins.getInstance().getServer().getPlayer(parameter);
                    if (player==null) return;
                    player.saveData();
                }
            },delay*1000,time*1000);
        }

    }

    @Override
    public String GetName() {
        return Name;
    }
}
