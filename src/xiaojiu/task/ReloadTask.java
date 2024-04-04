package xiaojiu.task;

import com.yuhai.core.MapEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xiaojiu.StartPlugins;
import xiaojiu.tools.MessageHelper;
import xiaojiu.tools.RestartTools;
import xiaojiu.tools.SuggestHelper;
import xiaojiu.tools.Until;

import java.util.*;

public class ReloadTask {
    protected static Timer task = null;
    public static SuggestHelper suggestHelper = null;

    public static void RunTask(JavaPlugin Instance) {
        task=new Timer();
        suggestHelper = new SuggestHelper();
        suggestHelper.isSuggesting=true;
        for (Map.Entry<Integer, String> entry: Until.SuggestMap.entrySet()){
            Date date =new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND,120-entry.getKey());
            task.schedule(new TimerTask() {
                @Override
                public void run() {
                    Bukkit.getScheduler().runTask(Instance, new Runnable() {
                        @Override
                        public void run() {
                            if (entry.getKey()==0){
                                    down();

                            }else {
                                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+String.format(entry.getValue(),"重启"));
                                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+String.format("投票情况:同意人数%s人 拒绝人数%s人",
                                        suggestHelper.Approve.size(),
                                        suggestHelper.Refuse.size()));
                                MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"使用/sug 同意 同意投票,使用/sug 拒绝 拒绝投票");
                            }


                        }
                    });
                }
            },calendar.getTime());
        }

    }
    public static void down(){
        if ((double) suggestHelper.Approve.size() / StartPlugins.getInstance().getServer().getOnlinePlayers().size()>0.7) {
            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE + "投票结束,服务器投票情况符合条件,即将进行重启");
            RestartTools.Restart(30);
        }else {
            MessageHelper.SendMessageAllPlayer(ChatColor.LIGHT_PURPLE+"投票结束,服务器投票情况不符合条件(70%的在线玩家同意)");
        }
    }
    public static void cancel(){
        if (task!=null){
            task.cancel();
            task=null;
            suggestHelper=null;
        }

    }
    public static Timer GetTask() {
        return task;
    }
}
