package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuPlayer;
import xiaojiu.api.XiaojiuTask;

import java.util.*;

public abstract class BasicSaveHandles extends TimerTask implements XiaojiuTask, XiaojiuPlayer {
    protected int taskid;
    protected Timer timer;
    protected String name;
    protected int delay=-1;
    protected int timerTime=-1;
    protected boolean tasking=false;
    protected boolean canAsynchronously=false;
    protected boolean Asynchronously = false;
    protected String[] args;
    protected JavaPlugin plugin;
    protected Player player;
    protected BukkitTask task;
    public BasicSaveHandles(int taskid,JavaPlugin plugin,Player player,String taskName,String... args){
        this.player=player;
        this.name=taskName;
        this.taskid=taskid;
        if (taskid==-1) return;
        this.timer=new Timer(this.getName()+taskid);
        this.args=args;
        this.plugin=plugin;
    }
    @Override
    public void Cancel() {
        if (!tasking) return;
        if (!Asynchronously) {
            task.cancel();
        }else{
            this.cancel();
        }

    }
    public int getTaskid(){
        return this.taskid;
    }
    @Override
    public void RunTaskAsynchronously(long time, int delay) {
        if (!canAsynchronously||tasking) return;
        this.tasking=true;
        this.Asynchronously=true;
        this.timerTime=(int)time;
        this.delay=delay;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,delay);
        if (time==0){
            this.timer.schedule(this,calendar.getTime());
            return;
        }
        this.timer.schedule(this,calendar.getTime(),time*1000);
    }
    @Override
    public void RunTask(long time, long delay) {
        if (tasking) return;
        this.tasking=true;
        this.Asynchronously=false;
        this.timerTime=(int)time;
        this.delay= (int) delay;
        if (time==0){
            task= Bukkit.getScheduler().runTaskLater(plugin,this,delay);
            return;
        }
        task= Bukkit.getScheduler().runTaskTimer(StartPlugins.getInstance(),this, delay,time);
    }

    @Override
    public int getDelay() {
        return delay;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public int getTimerTime() {
        return timerTime;
    }

    @Override
    public Timer getTimer() {
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPlayer(UUID uuid) {
        //任务发起者不允许重设
    }

    @Override
    public void setPlayer(String playerName) {
        //任务发起者不允许重设
    }

    @Override
    public void setPlayer(Player player) {
        //任务发起者不允许重设
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isAsynchronouslyTask() {
        return this.Asynchronously;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicSaveHandles)) return false;
        BasicSaveHandles that = (BasicSaveHandles) o;
        return taskid == that.taskid && isTasking() == that.isTasking() && canAsynchronously == that.canAsynchronously && Objects.equals(timer, that.timer) && Objects.equals(name, that.name) && Arrays.equals(args, that.args) && Objects.equals(plugin, that.plugin);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(taskid, timer, name, isTasking(), canAsynchronously, plugin);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
