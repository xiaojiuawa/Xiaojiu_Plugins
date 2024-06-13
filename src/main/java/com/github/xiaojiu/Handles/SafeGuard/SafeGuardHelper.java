package com.github.xiaojiu.Handles.SafeGuard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.tools.MessageHelper;
import com.github.xiaojiu.tools.Utils;

import java.util.*;

public class SafeGuardHelper {
    public static boolean isSafeGuard = false;
    public static Timer timer;

    public static void SafeGuard() {
        isSafeGuard = true;
    }

    public static void SafeGuardEnd() {
        isSafeGuard = false;

    }

    public static void done() {
        SafeGuardHelper.SafeGuard();
        Utils.KickAllPlayersUseNode("xiaojiu.safeguard.nokick", "服务器维护,请等待服务器维护结束");
//        Until.KickAllPlayers(StartPlugins.getInstance().getServer().getOnlinePlayers(),"服务器维护,请等待服务器维护结束");
    }

    public static boolean startSafeGuard(int num) {
        if (isSafeGuard) return false;
        if (num == -1) {
            done();
            return true;
        }
        SafeGuard(num);
        return true;
    }

    public static void SafeGuard(int time) {
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
                        Bukkit.getScheduler().runTask(Xiaojiu.getInstance(), SafeGuardHelper::done);
                    } else {
                        MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE + String.format(entry.getValue(), "维护"));
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
