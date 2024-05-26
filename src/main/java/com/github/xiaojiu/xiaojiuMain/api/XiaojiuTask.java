package com.github.xiaojiu.xiaojiuMain.api;

import java.util.Date;
import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    boolean isTasking();

    Date getCreateDate();
    Date getRunDate();
    Date getLoadDate();
    void Cancel();

    void RunTaskAsynchronously(long time, int delay);

    void RunTask(long time, long delay);

    int getDelay();

    int getTimerTime();

    String getName();

    Timer getTimer();

    boolean canAsynchronously();

    boolean isAsynchronouslyTask();
}
