package xiaojiu.Handles.Save;

import net.minecraftforge.event.world.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SaveWorldTask extends BasicSaveHandles {
    private static final String Name="world";
    public SaveWorldTask(){
        this.canAsynchronously=true;
    }
    @Override
    public void run() {
        StartPlugins.getInstance().getServer().getWorlds().forEach(World::save);
        MessageHelper.SendMessageAllPlayer("1");
    }

    @Override
    public void specialParametersTask(String parameter, boolean isAsynchronously, long time, long delay) {
        if (isAsynchronously && !this.canAsynchronously) return;
        if (isAsynchronously){
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    World world = StartPlugins.getInstance().getServer().getWorld(parameter);
                    if (world==null) return;
                    world.save();
                }
            },delay*1000,time*1000);
        }else{
            Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(), new Runnable() {
                @Override
                public void run() {
                    World world = StartPlugins.getInstance().getServer().getWorld(parameter);
                    if (world==null) return;
                    world.save();
                }
            },delay*1000,time*1000);
        }
    }

    @Override
    public String GetName() {
        return Name;
    }
}
