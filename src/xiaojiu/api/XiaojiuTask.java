package xiaojiu.api;

import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    boolean isTasking();
    void Cancel();
    void RunTaskAsynchronously(long time,int delay);
    void RunTask(long time,long delay);
    String GetName();
    Timer GetTimer();
    boolean canAsynchronously();
}
