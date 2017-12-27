import com.cn.yc.bean.NewsDO;
import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.service.impl.LinkSpiderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by hasee on 2017/12/25.
 */
public class LinkSpiderServiceTest {

    @Test
    public void spiderInfo(){
        LinkSpiderService linkSpiderService = new LinkSpiderServiceImpl();
        linkSpiderService.spiderNews();
    }

    @Test
    public void spiderMockInfo(){
        LinkSpiderService linkSpiderMockService = mock(LinkSpiderServiceImpl.class);
        when(linkSpiderMockService.getNews()).thenReturn(null);
        linkSpiderMockService.spiderNews();
        NewsDO result = linkSpiderMockService.getNews();
        Assert.assertNotNull(result);
    }
}
