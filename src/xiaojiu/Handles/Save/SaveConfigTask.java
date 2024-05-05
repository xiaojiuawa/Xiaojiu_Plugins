package xiaojiu.Handles.Save;


import org.bukkit.Bukkit;
import xiaojiu.StartPlugins;
import xiaojiu.config.SaveConfig;

import java.util.TimerTask;

public class SaveConfigTask extends BasicSaveHandles{
    private static final String Name = "config";
    public SaveConfigTask(){
        this.canAsynchronously=true;
    }
    @Override
    public void run() {
        SaveConfig.Save();
    }



    @Override
    public void specialParametersTask(String parameter, boolean isAsynchronously, long time, long delay) {
        if (isAsynchronously && !this.canAsynchronously) return;
        if (isAsynchronously){
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (parameter.equalsIgnoreCase("LimitPlayer")) SaveConfig.SaveLimitPlayer();
                    if (parameter.equalsIgnoreCase("PlayerTime")) SaveConfig.SavePlayerTime();
                }
            },delay*1000,time*1000);
        }else{
            Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (parameter.equalsIgnoreCase("LimitPlayer")) SaveConfig.SaveLimitPlayer();
                    if (parameter.equalsIgnoreCase("PlayerTime")) SaveConfig.SavePlayerTime();
                }
            },delay*1000,time*1000);
        }
    }

    @Override
    public String GetName() {
        return Name;
    }
}
