package com.github.xiaojiu.Handles.Restart;

import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class RestartServerUseTimer {
    public static Timer timer;

    public static void Restart(int time) {
        timer = new Timer();
        for (Map.Entry<Integer, String> entry : Utils.WaitMap.entrySet()) {
            if (entry.getKey() > time) continue;
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, time - entry.getKey());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (entry.getValue().equalsIgnoreCase("down")) {
                        Bukkit.getScheduler().runTask(Xiaojiu.getInstance(), RestartTools::Done);
                        //                        RestartHelper.Done();
                    } else {
                        PostHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE + String.format(entry.getValue(), "重启"));
                    }

                }
            }, calendar.getTime());
        }
    }

    public static void cancel() {
        if (timer == null) return;
        timer.cancel();
        timer = null;
    }
}
