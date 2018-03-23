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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2018/3/18.
 */
public class HuoBiApiUtils {
    public static WebSocketClient client;
    private static boolean isConnect = false;
    private static List<HuobiDetailVO> dataList = new ArrayList<>();
    private static int getNum = 0;
    static {
        try {
            System.out.println("************");
            client = new WebSocketClient(new URI("wss://api.huobipro.com/ws")) {

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
                            HuoBiApiUtils.send(JSONStrReaderUtils.objToJson(pongDTO),"neibu");
                            System.out.println("ping:" + result + " pone:" + JSONStrReaderUtils.objToJson(pongDTO));
                        }else{
                            HuobiResposneVO huobiResposneVO = JSONStrReaderUtils.jsonToObj(result, HuobiResposneVO.class);
                            dataList.addAll(huobiResposneVO.getData());
                            FileReadUtils.appendFile("E:\\huobi.txt",huobiResposneVO.getData().toString());
                            getNum++;
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
