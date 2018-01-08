import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.JsonUtils;
import com.cn.yc.utils.LinkUrl;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tomcat.jni.FileInfo;
import org.junit.*;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hasee on 2017/12/17.
 */
public class httpTests {

    @Test
    public void getAboutInfoTest(){
        String result = HttpUtils.sendQueryWkbAboutInfo();
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(result);
    }

    @Test
    public void getInfo(){
        String key = "玩客云";
        String result = HttpUtils.getBaidu(key);
        System.out.println(result);
    }


    @org.junit.Test
    public void test(){
        HttpEntity httpEntity = null;
        try {
            HttpGet httpGet = new HttpGet("http://www.baidu.com/");
            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    System.out.println("http版本号：" + response.getProtocolVersion());
                    //可以获得状态行，和状态行的各个元素的信息，和请求的状态行类似
                    System.out.println("状态行信息：" + response.getStatusLine().toString());
                    System.out.println("状态码：" + response.getStatusLine().getStatusCode());
                    System.out.println("状态码的解释：" + response.getStatusLine().getReasonPhrase());

                    HttpEntity entity = response.getEntity();
                    InputStream is =entity.getContent();

                    FileOutputStream fos = new FileOutputStream("D:\\a.txt");
                    byte[] b = new byte[1024];
                    while((is.read(b)) != -1){
                        fos.write(b);
                    }
                    is.close();
                    fos.close();


                    System.out.println("======");
                    System.out.println(JsonUtils.read(httpEntity));
                    System.out.println("yyy");
                }
            }
        } catch (IOException e) {
        }
    }
}
