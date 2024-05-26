package com.github.xiaojiu.xiaojiuMain.Handles.Save;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SaveWorldTask extends BasicSaveHandles {
    public SaveWorldTask(int taskid, JavaPlugin plugin, OfflinePlayer player, String... args) {
        super(taskid, plugin, player, "world", args);
        this.canAsynchronously = true;
//        this.name="World";
    }

    @Override
    public void run() {
        if (args.length != 0) {
            List<String> list = new ArrayList<>();
            for (String arg : args) {
                World world = this.plugin.getServer().getWorld(arg);
                if (world == null) {
                    list.add(arg);
                } else {
                    world.save();
                }

            }
            if (!list.isEmpty()) {
//                this.player.sendMessage(MessageHelper.InitMessage("世界:"+Arrays.toString(list.toArray()) +"未找到"));
            }
        }
        this.plugin.getServer().getWorlds().forEach(World::save);

//        MessageHelper.SendMessageAllPlayer("1");
    }

}
