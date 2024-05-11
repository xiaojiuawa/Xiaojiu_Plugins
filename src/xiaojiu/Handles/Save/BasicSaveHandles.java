package xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xiaojiu.StartPlugins;
import xiaojiu.api.XiaojiuPlayerable;
import xiaojiu.api.XiaojiuTask;

import java.util.*;

public abstract class BasicSaveHandles extends TimerTask implements XiaojiuTask, XiaojiuPlayerable {
    protected int taskid;
    protected Timer timer;
    protected String name;
    protected boolean tasking=false;
    protected boolean canAsynchronously=false;
    protected String[] args;
    protected JavaPlugin plugin;
    protected Player player;
    public BasicSaveHandles(int taskid,JavaPlugin plugin,Player player,String... args){
        this.player=player;
        this.taskid=taskid;
        if (taskid==-1) return;
        this.timer=new Timer(this.GetName()+taskid);
        this.args=args;
        this.plugin=plugin;
    }
    @Override
    public void Cancel() {
        tasking=false;
        this.cancel();

    }

    @Override
    public void RunTaskAsynchronously(long time, int delay) {
        if (!canAsynchronously) return;
        this.tasking=true;
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
        this.tasking=true;
        if (time==0){
            Bukkit.getScheduler().runTaskLater(plugin,this,delay);
            return;
        }
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

    @Override
    public String GetName() {
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
