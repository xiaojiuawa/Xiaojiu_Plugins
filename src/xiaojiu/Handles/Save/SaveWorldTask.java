package xiaojiu.Handles.Save;

import net.minecraftforge.event.world.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;

import java.util.*;

public class SaveWorldTask extends BasicSaveHandles {
    public SaveWorldTask(int taskid, JavaPlugin plugin, Player player, String... args){
        super(taskid, plugin,player, args);
        this.canAsynchronously=true;
        this.name="World";
    }
    @Override
    public void run() {
        if (args.length!=0){
            List<String> list = new ArrayList<>();
            for (String arg : args) {
                World world = this.plugin.getServer().getWorld(arg);
                if (world==null){
                    list.add(arg);
                }else{
                    world.save();
                }

            }
            if (!list.isEmpty()){
                this.player.sendMessage(MessageHelper.InitMessage("世界:"+Arrays.toString(list.toArray()) +"未找到"));
            }
        }
        this.plugin.getServer().getWorlds().forEach(World::save);
//        MessageHelper.SendMessageAllPlayer("1");
    }

}
