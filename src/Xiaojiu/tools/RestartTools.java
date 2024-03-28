package Xiaojiu.tools;

import Xiaojiu.StartPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class RestartTools {
    public static boolean isRestart =false;

    public static int ProcessingTime(String unit, int number){
        int num = 0;
        switch (unit){
            case "d":
                num=number*24*60*60;
                break;
            case "h":
                num=number*60*60;
                break;
        }
        return num;
    }
    public static boolean isNumber(String str){
        try {
            Integer.parseInt(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean Restart(int num){
        if(isRestart) return false;
        isRestart=true;
        RestartServerUseTimer.Restart(num);
        return true;
    }

    public static boolean cancel(){
        RestartServerUseTimer.cancel();
        isRestart=false;
        return true;
    }

    public static void ShutdownServer(){
        StartPlugins.getInstance().getServer().shutdown();
    }

    public static void Done(){
        Until.KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers(),"服务器重启，请耐心等待");
        ShutdownServer();
    }

    public static class RestartServerUseTimer {
        public static Timer timer;

        public static void Restart(int time){
            timer = new Timer();
            for (Map.Entry<Integer,String> entry: Until.WaitMap.entrySet()) {
                if (entry.getKey() > time) continue;
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
                                    Done();
                                }
                            });
    //                        RestartHelper.Done();
                        }else {
                            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE +String.format(entry.getValue(),"重启"));
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
}
