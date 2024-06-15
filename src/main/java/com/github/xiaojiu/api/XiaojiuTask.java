package com.github.xiaojiu.api;

import java.util.Date;
import java.util.Timer;

public interface XiaojiuTask extends Runnable {
    boolean isTasking();

    String getDescribeName();

    void setDescribeName(String name);

    String getDescribe();

    void setDescribe(String describe);

    Date getCreateDate();

    Date getRunDate();

    Date getLoadDate();

    void cancelTask();

    void RunTaskAsynchronously(long time, int delay);

    void RunTask(long time, long delay);

    int getDelay();

    int getTimerTime();

    String getName();

    Timer getTimer();

    boolean canAsynchronously();

    boolean isAsynchronouslyTask();
}
