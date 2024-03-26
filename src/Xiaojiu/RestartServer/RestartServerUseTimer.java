package Xiaojiu.RestartServer;


import Xiaojiu.StartPlugins;
import Xiaojiu.tools.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class RestartServerUseTimer {
    public static Timer timer;
    public static HashMap<Integer,String> hashMap = new HashMap<>();
    public static void Init(){
        hashMap.put(0,"down");
        hashMap.put(1,"服务器将在1秒后重启");
        hashMap.put(2,"服务器将在2秒后重启");
        hashMap.put(3,"服务器将在3秒后重启");
        hashMap.put(4,"服务器将在4秒后重启");
        hashMap.put(5,"服务器将在5秒后重启");
        hashMap.put(6,"服务器将在6秒后重启");
        hashMap.put(7,"服务器将在7秒后重启");
        hashMap.put(8,"服务器将在8秒后重启");
        hashMap.put(9,"服务器将在9秒后重启");
        hashMap.put(10,"服务器将在10秒后重启");
        hashMap.put(30,"服务器将在30秒后重启");
        hashMap.put(60,"服务器将在1分钟后重启");
        hashMap.put(120,"服务器将在2分钟后重启");
        hashMap.put(1800,"服务器将在30分钟后重启");
        hashMap.put(3600,"服务器将在一个小时后重启");
    }
    public static void Restart(int time){
        for (Map.Entry<Integer,String> entry:hashMap.entrySet()) {
            if (entry.getKey() > time) continue;
            timer = new Timer();
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, time -entry.getKey());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(entry.getValue().equalsIgnoreCase("down")){
                        Bukkit.getScheduler().runTask(StartPlugins.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                RestartHelper.Done();
                            }
                        });
//                        RestartHelper.Done();
                    }else {
                        MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE +entry.getValue());
                    }

                }
            },calendar.getTime());
        }
    }
    public static void cancel(){
        if (timer==null) return;
        timer.cancel();
        timer=null;
    }
}
