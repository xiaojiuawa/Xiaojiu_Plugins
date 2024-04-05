package xiaojiu.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;

public class TpsTask {
    protected static BukkitTask task = null;


    public static void RunTask(JavaPlugin Instance) {
        task = Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(), new Runnable() {
            @Override
            public void run() {
                double[] Tps = StartPlugins.getInstance().getServer().getTPS();
                if (Tps[0] < 5) {
                    MessageHelper.SendMessageAllPlayer(ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "服务器流畅度分析" + ChatColor.WHITE + "]" + ChatColor.RED + "警告" + ChatColor.WHITE + ":" + ChatColor.LIGHT_PURPLE + "服务器平均tps已低于5");
                } else if (Tps[0] < 10) {
                    MessageHelper.SendMessageAllPlayer(ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "服务器流畅度分析" + ChatColor.WHITE + "]" + ChatColor.RED + "警告" + ChatColor.WHITE + ":" + ChatColor.LIGHT_PURPLE + "服务器平均tps已低于10");
                } else if (Tps[0] < 15) {
                    MessageHelper.SendMessageAllPlayer(ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "服务器流畅度分析" + ChatColor.WHITE + "]" + ChatColor.RED + "警告" + ChatColor.WHITE + ":" + ChatColor.LIGHT_PURPLE + "服务器平均tps已低于15");
                } else {
                    MessageHelper.SendMessageAllPlayer(ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "服务器流畅度分析" + ChatColor.WHITE + "]" + ChatColor.LIGHT_PURPLE + "服务器平均tps正常");
                }
            }
        }, 0, 600);
    }

    public static BukkitTask GetTask() {
        return task;
    }
}
