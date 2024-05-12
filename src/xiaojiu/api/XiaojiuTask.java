package xiaojiu.api;

import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    boolean isTasking();
    void Cancel();
    void RunTaskAsynchronously(long time,int delay);
    void RunTask(long time,long delay);
    int getDelay();
    int getTimerTime();
    String getName();
    Timer getTimer();
    boolean canAsynchronously();
    boolean isAsynchronouslyTask();
}
