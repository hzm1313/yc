package huobi;

import com.cn.yc.bean.*;
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
                    huobiKlineReqDTO.setReq(MessageFormat.format(Constants.HUOBI_KLINE, Constants.HUOBI_BTCUSDT, Constants.HUOBI_5min));
                    startTime = diff + 1 + startTime;
                    if (kk == 1) {
                        //处理数据然后
                        HuoBiApiUtils.exportToFileAppend("E:\\", "hzm" + j, huobiDetailVOList);
                        kk = 0;
                        huobiDetailVOList.clear();
                        j++;
                    }
                    if (i == 0) {
                        client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                    } else {
                        int bytesRead = sourceChannel.read(buf);
                        if (bytesRead != -1) {
                            System.out.println("Read " + bytesRead);
                            buf.flip();
                            String result = new String(GzipUtils.uncompress(buf.array()), "utf-8");
                            HuobiResposneVO huobiResposneVO = JSONStrReaderUtils.fromJson(result, HuobiResposneVO.class);
                            System.out.println(i);
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
            HuoBiApiUtils.exportToFileAppend("E:\\", "hzm" + j, huobiDetailVOList);
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
        huobiDepthDTO.setSub(MessageFormat.format(Constants.HUOBI_DEPTH,Constants.HUOBI_BTCUSDT,Constants.HUOBI_DEPTH_SETP0));
        WebSocketClient client = HuoBiApiUtils.getClient();
        //订阅市场行情
        client.send(JSONStrReaderUtils.objToJson(huobiDepthDTO));
        //订阅交易详情
        huobiDepthDTO = new HuobiDepthDTO();
        huobiDepthDTO.setSub(MessageFormat.format(Constants.HUOBI_TRADE,Constants.HUOBI_BTCUSDT));
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
                    Iterator it = selector.selectedKeys().iterator( );
                    //读去客户端内容
                    Pipe.SourceChannel clientSocketChannel = (Pipe.SourceChannel) key.channel();
                    receiveBuffer.clear();
                    clientSocketChannel.read(receiveBuffer);
                    key.interestOps(SelectionKey.OP_READ);
                    String result = new String(GzipUtils.uncompress(receiveBuffer.array()),"utf-8");
                    System.out.println(result);
                    if(result.contains("depth")){
                        HuobiDepthResposneVO huobiDepthResposneVO = JSONStrReaderUtils.fromJson(result,HuobiDepthResposneVO.class);
                        System.out.println("depth");
                    }else if(result.contains("trade")){
                        HuobiTradeResponseVO huobiDepthResposneVO = JSONStrReaderUtils.fromJson(result,HuobiTradeResponseVO.class);
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
    public void baseStrategy(){
        //策略1
        //连续下跌30分钟线，买入，直到，高于成本价0.5%,卖出，否则一直持有，手续费为0.4%，如果下跌超过5%,，无条件卖出
        //读取数据
       /* List<HuobiDetailVO> huobiDetailVOList  = HuoBiApiUtils.importToFile("E:\\虚拟货币\\测试数据\\test_1min.xlsx");*/
        List<List<String>> lists = ExceUtil.importFile(new File("E:\\虚拟货币\\测试数据\\test.xlsx"));
        List<HuobiDetailVO> huobiDetailVOList = new ArrayList<>();
        HuobiDetailVO huobiDetailVO = null;
        for(List<String> strList:lists){
            huobiDetailVO = new HuobiDetailVO();
            huobiDetailVO.setOpen(strList.get(2));
            huobiDetailVO.setClose(strList.get(3));
            huobiDetailVO.setLow(strList.get(4));
            huobiDetailVO.setHigh(strList.get(5));
            huobiDetailVO.setVol(strList.get(6));
            huobiDetailVO.setCount(strList.get(7));
            huobiDetailVO.setAmount(strList.get(8));
            huobiDetailVOList.add(huobiDetailVO);
        }
        for(HuobiDetailVO huobiDetailVO1: huobiDetailVOList){
            System.out.println(JSONStrReaderUtils.objToJson(huobiDetailVO1));
        }
        //策略2，网格策略
    }
}
