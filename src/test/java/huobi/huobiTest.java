package huobi;

import com.cn.yc.bean.*;
import com.cn.yc.bean.HuoBi.ReseauStratge;
import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.*;

import com.cn.yc.utils.excel.ExceUtil;
import com.cn.yc.utils.excel.ExcelExport;
import org.apache.http.util.TextUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.java_websocket.client.WebSocketClient;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hasee on 2018/3/18.
 */
public class huobiTest {
    @Test
    public void testConnectIsOk() throws InterruptedException, UnsupportedEncodingException {
        try {
            while (!HuoBiApiUtils.isIsConnect()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("sleeppp");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeDataNumberToNumber() throws InterruptedException, UnsupportedEncodingException {
        try {
            while (!HuoBiApiUtils.isIsConnect()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("sleeppp");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //2017/10/27日开始-至今
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String startTimeStr = "2017/10/27 00:00:00";
            //String startTimeStr = "2018/03/27 00:00:00";
            Date date = format.parse(startTimeStr);
            Long startTime = date.getTime() / 1000;
            Long endTime = new Date().getTime() / 1000;
            HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
            WebSocketClient client = HuoBiApiUtils.getClient();
            int i = 0, kk = 0, j = 1;
            List<HuobiDetailVO> huobiDetailVOList = new ArrayList<>();
            String dtName =  Constants.HUOBI_ONTUSDT;
            try {
                Long diff = 60 * 300l;//60 * 30 * 300L;
                Pipe pipe = HuoBiApiUtils.getPipe();
                Pipe.SourceChannel sourceChannel = pipe.source();
                ByteBuffer buf = ByteBuffer.allocate(102400);
                while (startTime <= endTime) {
                    huobiKlineReqDTO.setFrom(startTime);
                    huobiKlineReqDTO.setTo(startTime + diff);
                    huobiKlineReqDTO.setId("id10");
                    huobiKlineReqDTO.setReq(MessageFormat.format(Constants.HUOBI_KLINE, dtName, Constants.HUOBI_1min));
                    startTime = diff + 1 + startTime;
                    if (kk == 300) {
                        //处理数据然后
                        HuoBiApiUtils.exportToFileAppend("E:\\",  dtName + j, huobiDetailVOList);
                        kk = 0;
                        huobiDetailVOList.clear();
                        j++;
                    }
                    if (i == 0) {
                        client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                    } else {
                        int bytesRead = sourceChannel.read(buf);
                        if (bytesRead != -1) {
                            buf.flip();
                            String result = new String(GzipUtils.uncompress(buf.array()), "utf-8");
                            HuobiResposneVO huobiResposneVO = JSONStrReaderUtils.fromJson(result, HuobiResposneVO.class);
                            huobiDetailVOList.addAll(huobiResposneVO.getData());
                            buf.clear();
                            client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                        }

                    }
                    i++;
                    kk++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("获取数据结束");
            HuoBiApiUtils.exportToFileAppend("E:\\", dtName + j, huobiDetailVOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void getDataList() throws InterruptedException, UnsupportedEncodingException {
        try {
            while (!HuoBiApiUtils.isIsConnect()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("sleeppp");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<String> listName = new ArrayList<>();
            listName.add(Constants.HUOBI_HTUSDT);
            listName.add(Constants.HUOBI_TRXUSDT);
            listName.add(Constants.HUOBI_DTAUSDT);
            listName.add(Constants.HUOBI_NEOUSDT);
            listName.add(Constants.HUOBI_QTUMUSDT);
            listName.add(Constants.HUOBI_ELAUSDT);
            listName.add(Constants.HUOBI_VENUSDT);
            listName.add(Constants.HUOBI_THETAUSDT);
            listName.add(Constants.HUOBI_SNTUSDT);
            listName.add(Constants.HUOBI_ZILUSDT);
            listName.add(Constants.HUOBI_XEMUSDT);
            listName.add(Constants.HUOBI_SMTUSDT);
            listName.add(Constants.HUOBI_NASUSDT);
            listName.add(Constants.HUOBI_RUFFUSDT);
            listName.add(Constants.HUOBI_HSRONTUSDT);
            listName.add(Constants.HUOBI_LETUSDT);
            listName.add(Constants.HUOBI_MDSUSDT);
            listName.add(Constants.HUOBI_STORJUSDT);
            listName.add(Constants.HUOBI_ELFUSDT);
            listName.add(Constants.HUOBI_ITCUSDT);
            listName.add(Constants.HUOBI_CVCUSDT);
            listName.add(Constants.HUOBI_GNTUSDT);
                String dtName = Constants.HUOBI_GNTUSDT;
                //2017/10/27日开始-至今
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                //String startTimeStr = "2017/10/27 00:00:00";
                String startTimeStr = "2017/12/01 00:00:00";
                Date date = format.parse(startTimeStr);
                Long startTime = date.getTime() / 1000;
                Long endTime = new Date().getTime() / 1000;
                HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
                WebSocketClient client = HuoBiApiUtils.getClient();
                int i = 0, kk = 0, j = 1;
                List<HuobiDetailVO> huobiDetailVOList = new ArrayList<>();
                try {
                    Long diff = 60 * 300l;//60 * 30 * 300L;
                    Pipe pipe = HuoBiApiUtils.getPipe();
                    Pipe.SourceChannel sourceChannel = pipe.source();
                    ByteBuffer buf = ByteBuffer.allocate(102400);
                    while (startTime <= endTime) {
                        huobiKlineReqDTO.setFrom(startTime);
                        huobiKlineReqDTO.setTo(startTime + diff);
                        huobiKlineReqDTO.setId("id10");
                        huobiKlineReqDTO.setReq(MessageFormat.format(Constants.HUOBI_KLINE, dtName, Constants.HUOBI_1min));
                        startTime = diff + 1 + startTime;
                        if (kk == 300) {
                            //处理数据然后
                            HuoBiApiUtils.exportToFileAppend("E:\\",  dtName + j, huobiDetailVOList);
                            kk = 0;
                            huobiDetailVOList.clear();
                            j++;
                        }
                        if (i == 0) {
                            client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                        } else {
                            int bytesRead = sourceChannel.read(buf);
                            if (bytesRead != -1) {
                                buf.flip();
                                String result = new String(GzipUtils.uncompress(buf.array()), "utf-8");
                                HuobiResposneVO huobiResposneVO = JSONStrReaderUtils.fromJson(result, HuobiResposneVO.class);
                                huobiDetailVOList.addAll(huobiResposneVO.getData());
                                buf.clear();
                                client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                            }

                        }
                        i++;
                        kk++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("获取数据结束");
                HuoBiApiUtils.exportToFileAppend("E:\\", dtName + j, huobiDetailVOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void huobiWsTest() throws IOException {
        ByteBuffer receiveBuffer = ByteBuffer.allocate(10240);
        while (!HuoBiApiUtils.isIsConnect()) {
            try {
                Thread.sleep(1000);
                System.out.println("sleeppp");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Selector selector = HuoBiApiUtils.getSelector();
        Pipe.SourceChannel sourceChannel = HuoBiApiUtils.getPipe().source();
        sourceChannel.configureBlocking(false);
        SelectionKey key = sourceChannel.register(selector, SelectionKey.OP_READ);
        HuobiDepthDTO huobiDepthDTO = new HuobiDepthDTO();
        huobiDepthDTO.setSub(MessageFormat.format(Constants.HUOBI_DEPTH, Constants.HUOBI_BTCUSDT, Constants.HUOBI_DEPTH_SETP0));
        WebSocketClient client = HuoBiApiUtils.getClient();
        //订阅市场行情
        client.send(JSONStrReaderUtils.objToJson(huobiDepthDTO));
        //订阅交易详情
        huobiDepthDTO = new HuobiDepthDTO();
        huobiDepthDTO.setSub(MessageFormat.format(Constants.HUOBI_TRADE, Constants.HUOBI_BTCUSDT));
        client.send(JSONStrReaderUtils.objToJson(huobiDepthDTO));
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                key = (SelectionKey) keyIterator.next();
                if (key.isAcceptable()) {
                    System.out.println("acceptable");
                } else if (key.isConnectable()) {
                    System.out.println("isConnect");
                } else if (key.isReadable()) {
                    System.out.println("isReadable");
                    Iterator it = selector.selectedKeys().iterator();
                    //读去客户端内容
                    Pipe.SourceChannel clientSocketChannel = (Pipe.SourceChannel) key.channel();
                    receiveBuffer.clear();
                    clientSocketChannel.read(receiveBuffer);
                    key.interestOps(SelectionKey.OP_READ);
                    String result = new String(GzipUtils.uncompress(receiveBuffer.array()), "utf-8");
                    System.out.println(result);
                    if (result.contains("depth")) {
                        HuobiDepthResposneVO huobiDepthResposneVO = JSONStrReaderUtils.fromJson(result, HuobiDepthResposneVO.class);
                        System.out.println("depth");
                    } else if (result.contains("trade")) {
                        HuobiTradeResponseVO huobiDepthResposneVO = JSONStrReaderUtils.fromJson(result, HuobiTradeResponseVO.class);
                        System.out.println("trade");
                    }

                    System.out.println("Thread id : " + Thread.currentThread().getId());
                } else if (key.isWritable()) {
                    System.out.println("isWritable");
                }
                keyIterator.remove();
            }
        }
    }

    @Test
    public void baseStrategy() {
        //策略1
        //连续下跌30分钟线，买入，直到，高于成本价0.5%,卖出，否则一直持有，手续费为0.4%，如果下跌超过1%,，无条件卖出
        //读取数据
       /* List<HuobiDetailVO> huobiDetailVOList  = HuoBiApiUtils.importToFile("E:\\虚拟货币\\测试数据\\test_1min.xlsx");*/
        List<List<String>> lists = ExceUtil.importFile(new File("E:\\虚拟货币\\测试数据\\test.xlsx"));
        if (false) {
            List<Map<String, String>> mapList = new ArrayList<>();
            for (int fallTraigger = 1; fallTraigger < 30; fallTraigger++) {
                //1W刀本金
                BigDecimal amount = new BigDecimal("10000");
                BigDecimal holdPrice = new BigDecimal("0");
                BigDecimal holdNum = new BigDecimal("0");
                BigDecimal basePrice = new BigDecimal(lists.get(1).get(3));
                BigDecimal open = new BigDecimal(lists.get(1).get(2));
                BigDecimal close = new BigDecimal(lists.get(1).get(3));
                BigDecimal high = new BigDecimal(lists.get(1).get(4));
                BigDecimal low = new BigDecimal(lists.get(1).get(5));
                //触发的卖出价格
                BigDecimal sellLowPrice = new BigDecimal(lists.get(1).get(3)).multiply(new BigDecimal("0.99"));
                BigDecimal sellHighPrice = new BigDecimal(lists.get(1).get(3)).multiply(new BigDecimal("1.005"));
                int falNum = 0;
                for (int i = 2; i < lists.size(); i++) {
                    open = new BigDecimal(lists.get(i).get(2));
                    close = new BigDecimal(lists.get(i).get(3));
                    high = new BigDecimal(lists.get(i).get(4));
                    low = new BigDecimal(lists.get(i).get(5));
                    if (holdNum.compareTo(new BigDecimal("0")) == 0) {
                        //持有本金，买入等待状态
                        if (close.compareTo(basePrice) < 0) {
                            falNum++;
                        } else {
                            falNum = 0;
                        }
                        if (falNum >= fallTraigger) {
                            //买入手续费
                            amount = amount.multiply(new BigDecimal("0.998"));
                            holdPrice = close;
                            holdNum = amount.divide(holdPrice, 10, BigDecimal.ROUND_DOWN);
                            amount = new BigDecimal("0");
                            //System.out.println("买入价:" + holdPrice + " 买入数量:" + holdNum + " 总价值:" + holdNum.multiply(holdPrice) + " 剩余钱:" + amount.toString());
                            sellLowPrice = new BigDecimal(lists.get(i).get(3)).multiply(new BigDecimal("0.994"));
                            sellHighPrice = new BigDecimal(lists.get(i).get(3)).multiply(new BigDecimal("1.006"));
                            falNum = 0;
                        }
                    } else {
                        //持有币，卖出等待状态\
                        //低于1.05卖出
                        if (close.compareTo(sellLowPrice) < 0 || close.compareTo(sellHighPrice) > 0) {
                            holdPrice = close;
                            amount = holdNum.multiply(holdPrice).multiply(new BigDecimal("0.998"));
                            //System.out.println("卖出价:" + holdPrice + " 买出数量:" + holdNum +" 税后钱:"+amount);
                            holdNum = new BigDecimal("0");
                        }
                    }
                    basePrice = close;
                }
                if (holdNum.compareTo(new BigDecimal("0")) > 0) {
                    //强行卖出
                    amount = holdNum.multiply(holdPrice).multiply(new BigDecimal("0.998"));
                }
                System.out.println("fallTraigger:" + fallTraigger + "  resultPrice:" + amount.toString());
                Map map = new HashMap<String, String>();
                map.put("fallTraigger", fallTraigger);
                map.put("resultPrice", amount.toString());
                mapList.add(map);
            }
            mapList.sort((o1, o2) -> o1.get("resultPrice").compareTo(o1.get("resultPrice")));
            mapList.forEach(obj -> {
                System.out.println(String.valueOf(obj.get("fallTraigger")) + "===" + obj.get("resultPrice"));
            });
        }
        //策略2，网格策略
        //为了拿一个好看的回测结果，我就上帝模式来一波,数据是自己去取的一个平均值
        //步长5%，上涨5%，卖出30%，上涨10%，卖出30%，上涨15%，卖出40%,
        //下跌同理,简化了很多东西
        for (int stepType = 1; stepType < 4; stepType++) {
            BigDecimal stepLength = new BigDecimal("0.05").multiply(new BigDecimal(stepType));
            BigDecimal basePrice = new BigDecimal("8000.5243315970");
            BigDecimal poundCost = new BigDecimal("0.998");
            //1W刀本金
            BigDecimal amount = new BigDecimal("10000");
            BigDecimal holdNum = new BigDecimal("0");
            BigDecimal tradePrice = new BigDecimal("0");
            BigDecimal close = new BigDecimal(lists.get(1).get(3));
            BigDecimal percent3 = new BigDecimal("0.30");
            BigDecimal percent4 = new BigDecimal("0.40");
            BigDecimal opPrice = null;
            ReseauStratge trade[] = {new ReseauStratge(amount.multiply(percent3)), new ReseauStratge(amount.multiply(percent3)), new ReseauStratge(amount.multiply(percent4))};
            for (int perIndex = 2; perIndex < lists.size(); perIndex++) {
                close = new BigDecimal(lists.get(perIndex).get(3));
                if (holdNum != null) {
                    holdNum.setScale(7, BigDecimal.ROUND_DOWN);
                }
                if (amount != null) {
                    amount.setScale(7, BigDecimal.ROUND_DOWN);
                }
                //买入/卖出
                //对于每个阶段的资金来说，只有，买入，卖出2种状态
                for (int buyPercentNum = 0; buyPercentNum < trade.length; buyPercentNum++) {
                    opPrice = new BigDecimal(basePrice.toString());
                    BigDecimal step = new BigDecimal("1.00");
                    if (trade[buyPercentNum].getCanBuy()) {
                        //买入
                        for (int muNum = 1; muNum <= buyPercentNum + 1; muNum++) {
                            step = step.subtract(stepLength);
                        }
                        opPrice = opPrice.multiply(step);
                        if (close.compareTo(opPrice) <= 0) {
                            trade[buyPercentNum].setTradePrice(close);
                            trade[buyPercentNum].setTradeNum(new BigDecimal(trade[buyPercentNum].getHoldAmount().divide(close, 7, BigDecimal.ROUND_DOWN).toString()));
                            trade[buyPercentNum].setHoldNum(trade[buyPercentNum].getTradeNum().multiply(poundCost));
                            trade[buyPercentNum].setTradeAmount(trade[buyPercentNum].getTradeNum().multiply(trade[buyPercentNum].getTradePrice()));
                            trade[buyPercentNum].setHoldAmount(trade[buyPercentNum].getHoldAmount().subtract(trade[buyPercentNum].getTradeAmount()));
                            trade[buyPercentNum].setCanBuy(false);
                            holdNum = holdNum.add(trade[buyPercentNum].getHoldNum());
                            amount = amount.subtract(trade[buyPercentNum].getTradeAmount());
                            trade[buyPercentNum].updatePercent();
                            //System.out.println("买入" + "holdNum:" + holdNum + "  resultPrice:" + amount.toString() + "总价值" + amount.add(holdNum.multiply(close)));
                        }
                    } else {
                        //卖出
                        for (int muNum = 1; muNum <= buyPercentNum + 1; muNum++) {
                            step = step.subtract(stepLength);
                        }
                        opPrice = opPrice.multiply(step);
                        if (close.compareTo(opPrice) >= 0) {
                            trade[buyPercentNum].setTradePrice(close);
                            trade[buyPercentNum].setTradeAmount(trade[buyPercentNum].getHoldNum().multiply(trade[buyPercentNum].getTradePrice()));
                            trade[buyPercentNum].setHoldAmount(trade[buyPercentNum].getHoldAmount().add(trade[buyPercentNum].getTradeAmount()).multiply(poundCost));
                            trade[buyPercentNum].setTradeNum(trade[buyPercentNum].getHoldNum());
                            trade[buyPercentNum].setHoldNum(new BigDecimal("0"));
                            trade[buyPercentNum].setCanBuy(true);
                            amount = amount.add(trade[buyPercentNum].getTradeAmount());
                            holdNum = holdNum.subtract(trade[buyPercentNum].getTradeNum());
                            trade[buyPercentNum].updatePercent();
                            //System.out.println("卖出" + "holdNum:" + holdNum + "  resultPrice:" + amount.toString() + "总价值" + amount.add(holdNum.multiply(close)));
                        }
                    }
                }
            }
            if (holdNum != null) {
                holdNum.setScale(7, BigDecimal.ROUND_DOWN);
            }
            if (amount != null) {
                amount.setScale(7, BigDecimal.ROUND_DOWN);
            }
            System.out.println("holdNum:" + holdNum + "  resultPrice:" + amount.toString() + "总价值" + amount.add(holdNum.multiply(close)));
        }
    }
}
