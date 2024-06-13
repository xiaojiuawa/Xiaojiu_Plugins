package com.github.xiaojiu.Handles.Save;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.XiaojiuPlayer;
import com.github.xiaojiu.api.XiaojiuTask;
import com.github.xiaojiu.config.Savecfg.SaveTask;

import java.util.*;

public abstract class BasicSaveHandles extends TimerTask implements XiaojiuTask, XiaojiuPlayer {
    protected final int taskid;
    protected Timer timer;
    protected final String name;
    protected String describeName;
    protected String describe;
    protected int delay = -1;
    protected int timerTime = -1;
    protected boolean tasking = false;
    protected boolean canAsynchronously = false;
    protected boolean Asynchronously = false;
    protected String[] args;
    protected JavaPlugin plugin;
    protected final UUID playerUUID;
    protected BukkitTask task;
    protected OfflinePlayer player;
    protected Date createDate;
    protected Date loadDate;
    protected Date runDate;
    public BasicSaveHandles(int taskid, JavaPlugin plugin, OfflinePlayer player, String taskName, String... args) {
        this.playerUUID = player.getUniqueId();
        this.player = player;
        this.name = taskName;
        this.taskid = taskid;
        if (taskid == -1) return;
        this.timer = new Timer(this.getName() + taskid);
        this.args = args;
        this.plugin = plugin;
        this.createDate=new Date();
        this.loadDate=createDate;
    }
    public BasicSaveHandles(SaveTask config){
        this.playerUUID=config.getPlayerUUID();
        this.createDate=config.getDate();
        this.loadDate=new Date();
        this.player=config.getPlayer();
        this.args= config.getArgs();
        this.name= config.getName();
        this.taskid=SaveTaskManager.getInstance().taskList.size()+1;
        if (config.isAsynchronously()){
            this.RunTaskAsynchronously(config.getTimer(), config.getDelay());
        }else{
            this.RunTask(config.getTimer(),config.getDelay());
        }
        Bukkit.dispatchCommand(this.player.getPlayer(),"123");
    }
    @Override
    public void Cancel() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::CanCel);
    }
    private void CanCel(){
        if (!tasking) return;
        if (!Asynchronously) {
            task.cancel();
        } else {
            this.cancel();
        }
    }

    public int getTaskid() {
        return this.taskid;
    }

    protected void ReRunTask(int delay, int time) {
        this.cancel();
        this.task = null;

    }

    @Override
    public void RunTaskAsynchronously(long time, int delay) {
        if (!canAsynchronously || tasking) return;
        this.runDate=new Date();
        this.tasking = true;
        this.Asynchronously = true;
        this.timerTime = (int) time;
        this.delay = delay;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, delay);
        if (time == 0) {
            this.timer.schedule(this, calendar.getTime());
            return;
        }
        this.timer.schedule(this, calendar.getTime(), time * 1000);
    }

    @Override
    public void RunTask(long time, long delay) {
        if (tasking) return;
        this.runDate=new Date();
        this.tasking = true;
        this.Asynchronously = false;
        this.timerTime = (int) time;
        this.delay = (int) delay;
        if (time == 0) {
            task = Bukkit.getScheduler().runTaskLater(plugin, this, delay);
            return;
        }
        task = Bukkit.getScheduler().runTaskTimer(Xiaojiu.getInstance(), this, delay * 20, time * 20);
    }

    @Override
    public void setDescribe(String describe) {
        this.describe=describe;
    }

    @Override
    public String getDescribe() {
        return describe;
    }

    @Override
    public String getDescribeName() {
        return describeName;
    }

    @Override
    public void setDescribeName(String describeName) {
        this.describeName = describeName;
    }

    @Override
    public Date getRunDate() {
        return runDate;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public Date getLoadDate() {
        return loadDate;
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
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    @Override
    public OfflinePlayer getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(UUID uuid) {
        this.player=plugin.getServer().getOfflinePlayer(uuid);
    }

    @Override
    public void setPlayer(String playerName) {
        this.player=plugin.getServer().getOfflinePlayer(playerName);
    }

    @Override
    public void setPlayer(Player player) {
        this.player=player;
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
