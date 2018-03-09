import com.cn.yc.service.TradeService;
import com.cn.yc.service.impl.TradeServiceImpl;
import com.cn.yc.utils.Constants;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by DT167 on 2018/1/5.
 */
public class TradeTest {

    @Test
    public void getInfoTestMock() throws NoSuchFieldException, IllegalAccessException {
        RedisTemplate<String, String> redisTemplateMock = mock(RedisTemplate.class);
        Mockito.when(redisTemplateMock.boundValueOps(Constants.TRADE_INFO_LIST)).thenReturn(Mockito.mock(BoundValueOperations.class));
        Mockito.when(redisTemplateMock.boundValueOps(Constants.TRADE_INFO_LIST).get()).thenReturn(null);
        TradeService tradeServiceMock = new TradeServiceImpl();//mock(TradeServiceImpl.class);
        Field tradeServiceField = TradeServiceImpl.class.getDeclaredField("redisTemplate");
        tradeServiceField.setAccessible(true);
        tradeServiceField.set(tradeServiceMock,redisTemplateMock);
      //  System.out.println(tradeServiceMock.getTradeInfo());
    }

    @Test
    public void getInfoTest(){
        List<String> data = new ArrayList<>();
        data.add("hzm");
        data.add("0");
        data.add("0");
        data.add("1");
        data.add("100");
        System.out.println(data.toString());
    }
}
