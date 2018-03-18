import com.cn.yc.thread.BaseThreadPool;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by DT167 on 2018/3/13.
 */
public class ThreadPoolTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {
        BaseThreadPool baseTreadPool = new BaseThreadPool();
        for(int i=0;i<20;i++){
            baseTreadPool.submitRunnable(new Runnable() {
                private String test;

                public Runnable setTest(String test) {
                    this.test = test;
                    return this;
                }

                @Override
                public void run() {
                    System.out.println("线程："+test);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.setTest(String.valueOf(i)));
        }
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return String.valueOf(Math.random());
            }
        };
        System.out.println(baseTreadPool.submitCallable(callable));
        try {
           // System.out.println(callable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
