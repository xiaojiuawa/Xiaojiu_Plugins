package com.github.xiaojiu.Handles.Restart;

import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.Utils;

public class RestartTools {
    public static boolean isRestart = false;

    public static int ProcessingTime(String unit, int number) {
        int num = -1;
        switch (unit) {
            case "d":
                num = number * 24 * 60 * 60;
                break;
            case "h":
                num = number * 60 * 60;
                break;
            case "m":
                num = number * 60;
        }
        return num;
    }

    public static boolean Restart(int num) {
        if (isRestart) return false;
        isRestart = true;
        RestartServerUseTimer.Restart(num);
        return true;
    }

    public static boolean cancel() {
        RestartServerUseTimer.cancel();
        isRestart = false;
        return true;
    }

    public static void ShutdownServer() {
        Xiaojiu.getInstance().getServer().shutdown();
    }

    public static void Done() {
        int n=0;
        while (n<3){
            try {
                Utils.KickAllPlayers(Xiaojiu.getInstance().getServer().getOnlinePlayers(), MessageHelper.getMessageCompletion("Reload.Kick"));
            }catch (Exception ignored){
            }
            n++;
            if (Xiaojiu.getInstance().getServer().getOnlinePlayers().isEmpty()) n+=3;
        }
        ShutdownServer();
    }

}
