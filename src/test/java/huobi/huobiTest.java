package huobi;

import com.cn.yc.bean.HuobiDetailVO;
import com.cn.yc.bean.HuobiKlineReqDTO;
import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.HuoBiApiUtils;

import com.cn.yc.utils.JSONStrReaderUtils;
import org.java_websocket.client.WebSocketClient;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2018/3/18.
 */
public class huobiTest {
    @Test
    public void downSj() throws UnsupportedEncodingException {
   /*     BaseThreadPool.submitRunnable(new Runnable() {
            @Override
            public void run() {
                HuoBiApiUtils huoBiApiUtils = new HuoBiApiUtils();
            }
        });*/
        //下载数据
        //2017/10/27日开始-至今
/*        Long startTime = DateUtils.getDateUnix("2017/10/27 00:00:00");
        Long endTime = DateUtils.getDateUnix(new Date().toString());
        System.out.println(startTime);
     while(1==1){

     }*/
        // HuoBiApiUtils.send("{ping: 18212558000}");
    }

    @Test
    public void test() {
        //xport(String fileName, String FilePath, List<String> rowNameList, ArrayList<ArrayList<String>> dataList)
    /*    ArrayList<String> nameList = new ArrayList<String>();
        nameList.add("t1");
        nameList.add("t2");
        ArrayList<String> contentList = new ArrayList<String>();
        contentList.add("t1Value");
        contentList.add("t2Value");
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list.add(contentList);
        list.add(contentList);
        try {
            ExcelExport.export("hzm.xlsx","F:\\开源\\数据\\",nameList,list);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void testss() throws InterruptedException, UnsupportedEncodingException {
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
            int i = 0,k=0;
            try {

                while (startTime <= endTime) {
                    huobiKlineReqDTO.setFrom(startTime);
                    huobiKlineReqDTO.setTo(startTime + 60 * 30 * 300);
                    huobiKlineReqDTO.setId("id10");
                    huobiKlineReqDTO.setReq("market.ethusdt.kline.30min");
                    startTime = 60 * 30 * 301 + startTime;
                    client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                    i++;
                }
                System.out.println(i);
                //HuoBiApiUtils.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO), "wb");
            } catch (Exception e) {
                e.printStackTrace();
            }
            while(!HuoBiApiUtils.getEnd(i)){
                Thread.sleep(3000);
                System.out.println("donging");
            }
            List<HuobiDetailVO> huobiDetailVOList = HuoBiApiUtils.getHuoBiData();
            i = 2;
            k = 0;
            long mr = 0;
            long mc = 0;
            long lr = 0;
            List<String> sy = new ArrayList<>();
            while (i <= 20) {
                k = 0;
                mr = huobiDetailVOList.get(0).getOpen();
                mc = huobiDetailVOList.get(2).getOpen();
                lr = mc - mr;
                for (int j = 0; j < huobiDetailVOList.size() - 3; j++) {
                    if (k >= i) {
                        lr += mr - mc;
                        mr = huobiDetailVOList.get(j).getOpen();
                        mc = huobiDetailVOList.get(j+2).getOpen();
                        k=0;
                        continue;
                    }
                    if (huobiDetailVOList.get(j).getClose() > huobiDetailVOList.get(j + 1).getClose()) {
                        i++;
                        mc = huobiDetailVOList.get(j+2).getOpen();
                    } else {
                        k = 0;
                        mr = huobiDetailVOList.get(0).getOpen();
                    }
                }
                i++;
            }
            for(String str:sy){
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
