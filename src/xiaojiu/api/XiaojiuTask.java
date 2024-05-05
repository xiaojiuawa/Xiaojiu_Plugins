package xiaojiu.api;

import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    void Cancel();
    void delayRunTaskAsynchronously(int time);
    void RunTaskTimerAsynchronously(long time);
    void RunTaskTimerDelayAsynchronously(long time,int delay);
    void delayRunTask(long time);
    void RunTaskTimer(long time);
    void RunTaskTimerDelay(long time,int delay);
    String GetName();
    Timer GetTimer();
}
