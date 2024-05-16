package xiaojiu.Handles.Save;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xiaojiu.tools.MessageHelper;

import java.util.Arrays;

public class SaveCmdHelper {
    public static void ShowTaskInfo(BasicSaveHandles task, Player player) {
        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "任务信息"));
        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "任务id:" + task.getTaskid()));
        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "任务名:" + task.getName()));
        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "任务参数" + (task.getArgs() == null ? "无" : Arrays.toString(task.getArgs()))));
        if (task.getDelay() != 0)
            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "延迟时间:" + task.getDelay()));
        if (task.getTimerTime() != 0)
            player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "执行时间间隔:" + task.getTimerTime()));
        player.sendMessage(MessageHelper.InitMessage(ChatColor.LIGHT_PURPLE + "创建任务玩家名" + task.getPlayer().getName()));
    }
}
