package xiaojiu.api;

import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    void specialParametersTask(String parameter,boolean isAsynchronously,long time,long delay);
    boolean isTasking();
    void Cancel();
    void delayRunTaskAsynchronously(long time);
    void RunTaskTimerAsynchronously(long time);
    void RunTaskTimerDelayAsynchronously(long time,int delay);
    void delayRunTask(long time);
    void RunTaskTimer(long time);
    void RunTaskTimerDelay(long time,int delay);
    String GetName();
    Timer GetTimer();
    boolean canAsynchronously();
}
