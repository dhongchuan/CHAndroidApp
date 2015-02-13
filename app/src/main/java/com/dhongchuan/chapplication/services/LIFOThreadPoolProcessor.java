package com.dhongchuan.chapplication.services;

import com.dhongchuan.chapplication.base.BaseLIFOTask;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class LIFOThreadPoolProcessor {

    private BlockingQueue<Runnable> opsToRun = new PriorityBlockingQueue<Runnable>(10,new Comparator<Runnable>() {

        @Override
        public int compare(Runnable lhs, Runnable rhs) {
            // TODO Auto-generated method stub
            if(lhs instanceof LIFOTask && rhs instanceof LIFOTask){
                LIFOTask l = (LIFOTask) lhs;
                LIFOTask r = (LIFOTask) rhs;
                return l.compare(l, r);
            }
            return 0;
        }
    });

    private ThreadPoolExecutor threadExecutor;

    public LIFOThreadPoolProcessor(int poolSize) {
        threadExecutor = new ThreadPoolExecutor(poolSize, 10, 0, TimeUnit.SECONDS, opsToRun);

    }


    public Future<?> submitTask(BaseLIFOTask task){
        threadExecutor.execute(task);
        return threadExecutor.submit(task);
    }
    public void clear(){
        threadExecutor.purge();
    }
}
