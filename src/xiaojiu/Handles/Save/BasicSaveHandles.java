package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BasicSaveHandles extends TimerTask implements XiaojiuTask {
//    public abstract String Name;
    public Timer timer = new Timer(this.GetName());
    @Override
    public void Cancel() {
        this.cancel();
    }

    @Override
    public void delayRunTaskAsynchronously(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,time);
        this.timer.schedule(this,calendar.getTime());
    }

    @Override
    public void RunTaskTimerDelayAsynchronously(long time, int delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,delay);
        this.timer.schedule(this,calendar.getTime(),time*1000);
    }

    @Override
    public void RunTaskTimerAsynchronously(long time) {
        this.timer.schedule(this,time);
    }

    @Override
    public void delayRunTask(long time) {
        Bukkit.getScheduler().runTaskLater(StartPlugins.getInstance(),this,time);
    }

    @Override
    public void RunTaskTimer(long time) {
        Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(),this,0,time);
    }

    @Override
    public void RunTaskTimerDelay(long time, int delay) {
        Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(),this, delay,time);
    }

    @Override
    public Timer GetTimer() {
        return this.timer;
    }
}
