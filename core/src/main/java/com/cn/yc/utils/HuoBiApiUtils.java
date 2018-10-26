package com.cn.yc.utils;

import com.cn.yc.bean.*;
import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.excel.ExceUtil;
import com.cn.yc.web.ws.WechatConnector;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.Selector;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2018/3/18.
 */
public class HuoBiApiUtils {
    protected static Logger logger = LoggerFactory.getLogger(HuoBiApiUtils.class);
    public static WebSocketClient client;
    private static boolean isConnect = false;
    private static List<HuobiDetailVO> dataList = new ArrayList<>();
    private static int getNum = 0;
    private static String url = "wss://api.huobi.br.com/ws";
    private static Pipe pipe;
    private static ByteBuffer buf = ByteBuffer.allocate(102400);
    private static Selector selector;

    public static Pipe getPipe() {
        return pipe;
    }

    public static Selector getSelector() {
        return selector;
    }

    static {
        try {
            selector = Selector.open();
            pipe = Pipe.open();
            client = new WebSocketClient(new URI(url)) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    isConnect = true;
                    System.out.println("打开链接");
                }

                @Override
                public void onMessage(String arg0) {
                    System.out.println("收到消息" + arg0);
                }

                @Override
                public void onError(Exception arg0) {
                    arg0.printStackTrace();
                    System.out.println("发生错误已关闭");
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {
                    System.out.println("链接已关闭");
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    try {
                        String result = new String(GzipUtils.uncompress(bytes.array()), "utf-8");
                        if (result.contains("ping")) {
                            PingDTO pingDTO = JSONStrReaderUtils.jsonToObj(result, PingDTO.class);
                            PongDTO pongDTO = new PongDTO();
                            pongDTO.setPong(pingDTO.getPing());
                            client.send(JSONStrReaderUtils.objToJson(pongDTO));
                            System.out.println("接受到Ping的请求，ping:" + result + " pong:" + JSONStrReaderUtils.objToJson(pongDTO));
                        } else if (result.contains("pong")) {
                            PongDTO pongDTO = JSONStrReaderUtils.jsonToObj(result, PongDTO.class);
                            PingDTO pingDTO = new PingDTO();
                            pingDTO.setPing(pongDTO.getPong());
                            client.send(JSONStrReaderUtils.objToJson(pongDTO));
                            System.out.println("接受到Pong的请求，ping:" + result + " pone:" + JSONStrReaderUtils.objToJson(pongDTO));
                        } else if (result.contains("kline")) {
                            System.out.println("---------" + result);
                            //判断数据为请求K线数据，用Pip,放进去，另外一个线程调用进行数据获取
                            buf.clear();
                            buf.put(bytes);
                            buf.flip();
                            while (buf.hasRemaining()) {
                                pipe.sink().write(buf);
                            }
                        } else if (result.contains("depth")) {
                            //判断数据为请求depth线数据，用Pip,放进去，另外一个线程调用进行数据获取
                            buf.clear();
                            buf.put(bytes);
                            buf.flip();
                            while (buf.hasRemaining()) {
                                pipe.sink().write(buf);
                            }
                        } else if (result.contains("trade")) {
                            //判断数据为请求trade数据，用Pip,放进去，另外一个线程调用进行数据获取
                            buf.clear();
                            buf.put(bytes);
                            buf.flip();
                            while (buf.hasRemaining()) {
                                pipe.sink().write(buf);
                            }
                        }else {
                            //未处理
                            logger.info("未处理:" + result);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            };
            client.connect();
        } catch (URISyntaxException e) {
            isConnect = false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isIsConnect() {
        return isConnect;
    }

    public static WebSocketClient getClient() {
        return client;
    }

    public static boolean isConnect() {
        if (client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            return true;
        }
        return false;
    }

    public static void exportToFile(String filePath,String fileName,List<HuobiDetailVO> huobiDetailVOList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        System.out.println("获取数据结束");
        DecimalFormat df = new DecimalFormat(".##");
        huobiDetailVOList.forEach(huobiDetailVO -> {
            huobiDetailVO.setAmount(df.format(new BigDecimal(huobiDetailVO.getAmount())));
            huobiDetailVO.setClose(df.format(new BigDecimal(huobiDetailVO.getClose())));
            huobiDetailVO.setHigh(df.format(new BigDecimal(huobiDetailVO.getHigh())));
            huobiDetailVO.setLow(df.format(new BigDecimal(huobiDetailVO.getLow())));
            huobiDetailVO.setOpen(df.format(new BigDecimal(huobiDetailVO.getOpen())));
            huobiDetailVO.setVol(df.format(new BigDecimal(huobiDetailVO.getVol())));
            huobiDetailVO.setDateDay(DateUtils.stampToDate(huobiDetailVO.getId(), "yyyy.MM.dd"));
            huobiDetailVO.setDateMin(DateUtils.stampToDate(huobiDetailVO.getId(), "HH:mm"));
        });
        List<String> titleColumn = new ArrayList<>();
        titleColumn.add("dateDay");
        titleColumn.add("dateMin");
        titleColumn.add("open");
        titleColumn.add("close");
        titleColumn.add("low");
        titleColumn.add("high");
        titleColumn.add("vol");
        titleColumn.add("count");
        titleColumn.add("amount");
        List<String> titleName = new ArrayList<>();
        titleName.add("年月日");
        titleName.add("时间");
        titleName.add("开盘价");
        titleName.add("收盘价");
        titleName.add("最低价");
        titleName.add("最高价");
        titleName.add("成交额");
        titleName.add("成交笔数");
        titleName.add("成交量");
        System.out.println("开始导出数据");
        ExceUtil.exportFile(filePath, fileName, titleName, titleColumn, huobiDetailVOList);
        System.out.println("导出完毕");
    }

    public static void exportToFileAppend(String filePath,String fileName,List<HuobiDetailVO> huobiDetailVOList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        System.out.println("追加获取数据开始");
        huobiDetailVOList.forEach(huobiDetailVO -> {
            huobiDetailVO.setAmount(new BigDecimal(huobiDetailVO.getAmount()).toString());
            huobiDetailVO.setClose(new BigDecimal(huobiDetailVO.getClose()).toString());
            huobiDetailVO.setHigh(new BigDecimal(huobiDetailVO.getHigh()).toString());
            huobiDetailVO.setLow(new BigDecimal(huobiDetailVO.getLow()).toString());
            huobiDetailVO.setOpen(new BigDecimal(huobiDetailVO.getOpen()).toString());
            huobiDetailVO.setVol(new BigDecimal(huobiDetailVO.getVol()).toString());
            huobiDetailVO.setDateDay(DateUtils.stampToDate(huobiDetailVO.getId(), "yyyy.MM.dd"));
            huobiDetailVO.setDateMin(DateUtils.stampToDate(huobiDetailVO.getId(), "HH:mm"));
        });
        List<String> titleColumn = new ArrayList<>();
        titleColumn.add("dateDay");
        titleColumn.add("dateMin");
        titleColumn.add("open");
        titleColumn.add("close");
        titleColumn.add("low");
        titleColumn.add("high");
        titleColumn.add("vol");
        titleColumn.add("count");
        titleColumn.add("amount");
        List<String> titleName = new ArrayList<>();
        titleName.add("年月日");
        titleName.add("时间");
        titleName.add("开盘价");
        titleName.add("收盘价");
        titleName.add("最低价");
        titleName.add("最高价");
        titleName.add("成交额");
        titleName.add("成交笔数");
        titleName.add("成交量");
        System.out.println("追加开始导出数据");
        ExceUtil.exportFileAppend(filePath, fileName, titleName, titleColumn, huobiDetailVOList);
        System.out.println("追加导出完毕");
    }

    public static List<HuobiDetailVO>  importToFile(String filePath){
        List<List<String>> lists = ExceUtil.importFile(new File(filePath));
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
        return huobiDetailVOList;
    }

}
