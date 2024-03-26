package Xiaojiu.RestartServer;

import Xiaojiu.StartPlugins;
import Xiaojiu.tools.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.Timer;

import static java.lang.Thread.sleep;


public class WaitTimeToRestart {
    public static BukkitTask task;
    public static void ReLoadServer(int RestartTime){
        int Restart = RestartTime;

        task = Bukkit.getScheduler().runTaskAsynchronously(StartPlugins.getInstance(), new Runnable() {
            @Override
            public void run() {
                int RestartTime = Restart;
                while(RestartTime!=0){

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    switch (RestartTime){
                        case 3600:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在一个小时后重启");
                            break;
                        case 1800:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在30分钟后重启");
                            break;
                        case 600:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在10分钟后重启");
                            break;
                        case 60:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在1分钟后重启");
                            break;
                        case 30:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在30秒后重启");
                            break;
                        case 10:
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在10秒后重启");
                            break;
                        default:
                            if(RestartTime<10){
                                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"服务器将在"+ String.valueOf(RestartTime)+"秒后重启");
                            }
                            break;
                    }
                    RestartTime--;
                }
                Bukkit.getScheduler().runTask(StartPlugins.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        RestartHelper.Done();
                    }
                });

            }
        });

    }
}
