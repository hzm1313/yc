import com.cn.yc.Startup;
import com.cn.yc.service.LinkService;
import com.cn.yc.service.QqRoobotService;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created by hasee on 2017/12/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class startProjectTests {
    @Autowired
    QqRoobotService qqRoobotService;


    @Autowired
    private LinkService linkService;


    @Test
    public void linkInfo(){
        String result = linkService.updateHttpInfo();
        System.out.println(result);
    }

    @Test
    public void initTest(){
        qqRoobotService.initStartQqRoot();
    }

    @Test
    public void testJqRen() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }
        );
        //创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的回调，如果你不需要接收消息，可以传null
        SmartQQClient client = new SmartQQClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println(message.getContent());
            }
        });
        //登录成功后便可以编写你自己的业务逻辑了
        List<Category> categories = client.getFriendListWithCategory();
        for (Category category : categories) {
            System.out.println(category.getName());
            for (Friend friend : category.getFriends()) {
                System.out.println("————" + friend.getNickname());
            }
        }
        //使用后调用close方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
