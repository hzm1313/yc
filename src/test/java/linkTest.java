import com.cn.yc.Startup;
import com.cn.yc.service.LinkService;
import com.cn.yc.service.impl.LinkServiceImpl;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.LinkUrl;
import org.apache.http.HttpRequest;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by hasee on 2017/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class linkTest {
    @Autowired
    private LinkService linkService;


    @Test
    public void linkInfo(){
        String result = linkService.updateHttpInfo();
        System.out.println(result);
    }
}
