package websocket;

import com.cn.yc.bean.PingDTO;
import com.cn.yc.bean.PongDTO;
import com.cn.yc.utils.GzipUtils;
import com.cn.yc.utils.JSONStrReaderUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Created by DT167 on 2018/6/21.
 */
public class websocket {
    static final String url = "ws://localhost:8765";
    @Test
    public void testWebsocket() throws URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI(url)) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("打开链接");
                this.send("hzmm");
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
            }

        };
        client.connect();
        while(true){

        }
    }
}
