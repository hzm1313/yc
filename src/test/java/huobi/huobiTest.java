package huobi;

import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.HuoBiApiUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by hasee on 2018/3/18.
 */
public class huobiTest {
    @Test
    public void test() throws UnsupportedEncodingException {
        BaseThreadPool.submitRunnable(new Runnable() {
            @Override
            public void run() {
                HuoBiApiUtils huoBiApiUtils = new HuoBiApiUtils();
            }
        });
     while(1==1){

     }
       // HuoBiApiUtils.send("{ping: 18212558000}");
    }
}
