package com.cn.yc.utils;

import com.cn.yc.bean.*;
import com.cn.yc.thread.BaseThreadPool;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2018/3/18.
 */
public class HuoBiApiUtils {
    public static WebSocketClient client;
    private static boolean isConnect = false;
    private static List<HuobiDetailVO> dataList = new ArrayList<>();
    private static int getNum = 0;
    private static String url = "ws://api.huobipro.com/ws";
    private static boolean isOver =false;
    //private static String url = "wss://api.huobipro.com/ws";
    private static Long diff,startTime,endTime;
    static {
        try {
            System.out.println("************");
            diff = 60*300l;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String startTimeStr = "2017/10/27 00:00:00";
            Date date = format.parse(startTimeStr);
            startTime = date.getTime() / 1000 +diff+1;
            endTime = new Date().getTime();
            HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
            huobiKlineReqDTO.setFrom(startTime);
            huobiKlineReqDTO.setTo(startTime + diff);
            huobiKlineReqDTO.setId("id10");
            huobiKlineReqDTO.setReq("market.btcusdt.kline.1min");
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
                            client.send("{\"pong\": "+pingDTO.getPing()+"}");
                            pingDTO.setPing(pongDTO.getPong()+1l);
                            client.send(JSONStrReaderUtils.objToJson(pingDTO));
                            System.out.println("ping:" + result + " pone:" + JSONStrReaderUtils.objToJson(pongDTO));
                        } else if (result.contains("pong")) {
                            PongDTO pongDTO = JSONStrReaderUtils.jsonToObj(result, PongDTO.class);
                            System.out.println("1111ping:" + result + " pone:" + JSONStrReaderUtils.objToJson(pongDTO));
                        } else{
                            HuobiResposneVO huobiResposneVO = JSONStrReaderUtils.fromJson(result, HuobiResposneVO.class);
                            dataList.addAll(huobiResposneVO.getData());
                            System.out.println(getNum++);
                            //FileReadUtils.appendFile("E:\\huobi.txt",huobiResposneVO.getData().toString());
                        }
                        //System.out.println("response:" + result);
                       /* HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
                        huobiKlineReqDTO.setFrom(1509033600);
                        huobiKlineReqDTO.setTo(1509035400);
                        huobiKlineReqDTO.setId("id10");
                        huobiKlineReqDTO.setReq("market.ethusdt.kline.30min");
                        HuoBiApiUtils.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO).getBytes("utf-8"),"neibbbb");*/


                       /*HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
                        huobiKlineReqDTO.setFrom(1509033600);
                        huobiKlineReqDTO.setTo(1509035400);
                        huobiKlineReqDTO.setId("id10");
                        huobiKlineReqDTO.setReq("market.ethusdt.kline.30min");
                        send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));*/
                    /*    System.out.println(" request:" + JSONStrReaderUtils.objToJson(huobiKlineReqDTO));*/
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            };
            client.connect();
         /*   while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
                Thread.sleep(1000);
            }
            */

            System.out.println("打开了");
        } catch (URISyntaxException e) {
            isConnect = false;
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void send(String requestMessage, String bs) throws UnsupportedEncodingException {
        System.out.println(bs + "---" + requestMessage);
        send(requestMessage.getBytes("utf-8"));
    }


    public static void send(String requestMessage) throws UnsupportedEncodingException {
        send(requestMessage.getBytes("utf-8"));
    }

    public synchronized static void send(byte[] bytes) {
        client.send(bytes);
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

    public static List<HuobiDetailVO> getHuoBiData(){
        return dataList;
    }

    public static boolean getEnd(int parmNum){
        if(parmNum==getNum){
            return true;
        }
        return false;
    }
}
