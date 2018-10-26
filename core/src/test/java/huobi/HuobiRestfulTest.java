package huobi;

import com.cn.yc.bean.HuoBi.*;
import com.cn.yc.utils.*;
import com.cn.yc.utils.TradeConstatns;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by hasee on 2018/4/8.
 */
public class HuobiRestfulTest {
    private static  String accountId ="1169749";
    //设置IP了的，所以这个只是测试，后面我就会删除的，大佬们万万不要套路我
    @Test
    public void getAccounts(){
        List<HuobiAccountsResDO> accounts = HuoBiRestfulApiUtils.getAccounts();
        for(HuobiAccountsResDO huobiAddOrdersReqDO:accounts){
            accountId = huobiAddOrdersReqDO.getId();
        }
        //获得多个，基础交易选择type为spot的ID
        System.out.println(JSONStrReaderUtils.objArryToJson(accounts));
    }

    @Test
    public void addOrder() {
        HuobiAddOrdersReqDO createOrderReq = new HuobiAddOrdersReqDO();
        System.out.println(accountId);
        createOrderReq.accountId = String.valueOf(accountId);
        createOrderReq.amount = "0.02";
        createOrderReq.price = "0.1";
        createOrderReq.symbol = Constants.HUOBI_BTCUSDT;
        createOrderReq.type = HuobiAddOrdersReqDO.OrderType.BUY_LIMIT;
        createOrderReq.source = "api";
        Long orderId = HuoBiRestfulApiUtils.createOrder(createOrderReq);
        System.out.println(orderId);
        //com.cn.yc.exception.ApiException: trade account balance is not enough, left: `0.0008`
    }
    //撤销订单，等我换一波钱再测
    //批量撤销
    //查询订单详情
    //查询某个订单的成交明细
    @Test
    public void getHistoryEntrust() throws IOException {
        HuobiHistoryEntrustReqDO huobiHistoryEntrustReqDO  = new HuobiHistoryEntrustReqDO();
        huobiHistoryEntrustReqDO.setSymbol(Constants.HUOBI_ETHUSDT);
        huobiHistoryEntrustReqDO.setStates(HuobiHistoryEntrustReqDO.States.FILLED);
        BaseResponse baseResponse = HuoBiRestfulApiUtils.getHistoryEntrust(huobiHistoryEntrustReqDO);
        System.out.println(JSONStrReaderUtils.writeValue(baseResponse));
    }

    @Test
    public void getHistoryTrade() throws IOException {
        HuobiHistoryTradeReqDO huobiHistoryTradeReqDO  = new HuobiHistoryTradeReqDO();
        huobiHistoryTradeReqDO.setSymbol(Constants.HUOBI_ETHUSDT);
        BaseResponse baseResponse = HuoBiRestfulApiUtils.getHistoryTrade(huobiHistoryTradeReqDO);
        System.out.println(JSONStrReaderUtils.writeValue(baseResponse));
    }

}
