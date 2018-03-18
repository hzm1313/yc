/**
 * Created by hasee on 2018/3/2.
 */

import com.cn.yc.Startup;
import com.cn.yc.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class RedisTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        Assert.isNull(redisService.get("hzm"),"null");
    }
}
