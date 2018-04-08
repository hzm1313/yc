package huobi;

import com.cn.yc.bean.HuoBi.HuobiAccountsResDO;
import com.cn.yc.bean.HuoBi.HuobiAddOrdersReqDO;
import com.cn.yc.utils.*;
import com.cn.yc.utils.TradeConstatns;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by hasee on 2018/4/8.
 */
public class HuobiRestfulTest {
    private String accountId = null;
    @Test
    public void getAccounts(){
        List<HuobiAccountsResDO> accounts = HuoBiRestfulApiUtils.getAccounts();
        for(HuobiAccountsResDO huobiAddOrdersReqDO:accounts){
            accountId = huobiAddOrdersReqDO.getId();
        }
        System.out.println(JSONStrReaderUtils.objArryToJson(accounts));
    }

    @Test
    public void addOrder() {
        HuobiAddOrdersReqDO createOrderReq = new HuobiAddOrdersReqDO();
        createOrderReq.accountId = String.valueOf(accountId);
        createOrderReq.amount = "0.02";
        createOrderReq.price = "0.1";
        createOrderReq.symbol = "eosusdt";
        createOrderReq.type = HuobiAddOrdersReqDO.OrderType.BUY_LIMIT;
        createOrderReq.source = "api";
        Long orderId = HuoBiRestfulApiUtils.createOrder(createOrderReq);
        System.out.println(orderId);
    }
}
