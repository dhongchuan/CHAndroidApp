package com.dhongchuan.chapplication.services;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class LIFOTask extends FutureTask<Object> implements Comparator<LIFOTask> {
    private static long counter = 0;
    private final long priority;

    public LIFOTask(Runnable runnable, int taskTag) {
        super(runnable, taskTag);
        // TODO Auto-generated constructor stub
        //在单线程中创建
        priority = counter++;
    }

    public long getPriority(){
        return priority;
    }

    @Override
    public int compare(LIFOTask lifoTask, LIFOTask lifoTask2) {
        return lifoTask.getPriority() > lifoTask2.getPriority() ? -1 : 1;
    }
}
