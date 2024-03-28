package Xiaojiu.tools;

import Xiaojiu.StartPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class SafeGuardHelper {
    public static boolean isSafeGuard = false;
    public static Timer timer;
    public static void SafeGuard(){
        isSafeGuard=true;
    }
    public static void SafeGuardEnd(){
        isSafeGuard=false;

    }
    public static void SafeGuard(int time){
        timer=new Timer();
        for (Map.Entry<Integer,String> entry:Until.WaitMap.entrySet()){
            if (entry.getKey()>time) continue;
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND,time-entry.getKey());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (entry.getValue().equalsIgnoreCase("down")){
                        Bukkit.getScheduler().runTask(StartPlugins.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                SafeGuard();
                                Until.KickAllPlayersUseNode("xiaojiu.safeguard.nokick","服务器维护,请耐心等待");
                            }
                        });
                    }else {
                        MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+String.format(entry.getValue(),"维护"));
                    }

                }
            },calendar.getTime());
        }
    }
    public static void Cancel(){
        if (timer==null) return;
        timer.cancel();
        timer=null;
    }
}
