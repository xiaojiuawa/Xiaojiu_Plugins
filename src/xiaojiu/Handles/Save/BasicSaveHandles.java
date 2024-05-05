package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BasicSaveHandles extends TimerTask implements XiaojiuTask {
    protected final Timer timer = new Timer(this.GetName());
    protected boolean tasking=false;
    protected boolean canAsynchronously=false;
    @Override
    public void Cancel() {
        this.cancel();
    }

    @Override
    public void delayRunTaskAsynchronously(long time) {
        if (!canAsynchronously) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,(int) time);
        this.timer.schedule(this,calendar.getTime());
    }

    @Override
    public void RunTaskTimerDelayAsynchronously(long time, int delay) {
        if (!canAsynchronously) return;
        this.tasking=true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,delay);
        this.timer.schedule(this,calendar.getTime(),time*1000);
    }

    @Override
    public void RunTaskTimerAsynchronously(long time) {
        if (!canAsynchronously) return;
        this.tasking=true;
        this.timer.schedule(this,0,time);
    }

    @Override
    public void delayRunTask(long time) {
        Bukkit.getScheduler().runTaskLater(StartPlugins.getInstance(),this,time);
    }

    @Override
    public void RunTaskTimer(long time) {
        this.tasking=true;
        Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(),this,0,time);
    }

    @Override
    public void RunTaskTimerDelay(long time, int delay) {
        this.tasking=true;
        Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(),this, delay,time);
    }

    @Override
    public Timer GetTimer() {
        return this.timer;
    }

    @Override
    public boolean isTasking() {
        return tasking;
    }

    @Override
    public boolean canAsynchronously() {
        return this.canAsynchronously;
    }
}
