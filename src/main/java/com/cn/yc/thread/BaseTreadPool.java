package com.cn.yc.thread;

import com.cn.yc.utils.Constants;

import java.util.concurrent.*;

/**
 * Created by DT167 on 2018/3/12.
 */
public class BaseTreadPool {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Constants.maxTread);

    public void submitRunnable(Runnable runnable){
        fixedThreadPool.submit(runnable);
    }

    public <T> T submitCallable(Callable<T> callable) throws ExecutionException, InterruptedException {
        FutureTask<T> futureTask = new FutureTask<T>(callable);
        fixedThreadPool.submit(futureTask);
        return futureTask.get();
    }

}
