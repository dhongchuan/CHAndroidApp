package com.dhongchuan.chapplication.base;

import java.util.Comparator;

/**
 * Created by hongchuan.du on 2015/2/12.
 */
public abstract class BaseLIFOTask implements Runnable, Comparator<BaseLIFOTask> {
    private static long counter = 0;
    private final long priority;

    public BaseLIFOTask() {
        //在单线程中创建
        priority = counter++;
    }

    public long getPriority(){
        return priority;
    }

    @Override
    public int compare(BaseLIFOTask lifoTask, BaseLIFOTask lifoTask2) {
        return lifoTask.getPriority() > lifoTask2.getPriority() ? -1 : 1;
    }
}
