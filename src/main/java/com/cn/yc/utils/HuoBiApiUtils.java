package com.cn.yc.utils;

import com.cn.yc.bean.HuobiKlineReqDTO;
import com.cn.yc.bean.PingDTO;
import com.cn.yc.bean.PongDTO;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Created by hasee on 2018/3/18.
 */
public class HuoBiApiUtils {
    public static WebSocketClient client;

    static {
        try {
            client = new WebSocketClient(new URI("wss://api.huobipro.com/ws")) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("打开链接");
                }

                @Override
                public void onMessage(String arg0) {
                    System.out.println("收到消息"+arg0);
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
                        String result = new String(GzipUtils.uncompress(bytes.array()),"utf-8");
                        if(result.contains("ping")){
                            PingDTO pingDTO = JSONStrReaderUtils.jsonToObj(result,PingDTO.class);
                            PongDTO pongDTO = new PongDTO();
                            pongDTO.setPong(pingDTO.getPing());
                            send(JSONStrReaderUtils.objToJson(pongDTO));
                            System.out.println("ping:"+result+" pone:"+JSONStrReaderUtils.objToJson(pongDTO));
                        }
                        System.out.println("response:"+result);
                        HuobiKlineReqDTO huobiKlineReqDTO = new HuobiKlineReqDTO();
                        huobiKlineReqDTO.setFrom(1481869853);
                        huobiKlineReqDTO.setTo(1481873453);
                        huobiKlineReqDTO.setId("id10");
                        huobiKlineReqDTO.setReq("market.ethusdt.kline.30min");
                        send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                        System.out.println(" request:"+JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            };
            client.connect();
            while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                System.out.println("还没有打开");
            }
            System.out.println("打开了");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static void send(String requestMessage) throws UnsupportedEncodingException {
        send("hello world".getBytes("utf-8"));
        client.send(requestMessage);
    }

    public static void send(byte[] bytes){
        client.send(bytes);
    }
}
