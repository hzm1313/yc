package huobi;

import com.cn.yc.bean.HuobiDetailVO;
import com.cn.yc.bean.HuobiKlineReqDTO;
import com.cn.yc.bean.PingDTO;
import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.DateUtils;
import com.cn.yc.utils.HuoBiApiUtils;

import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.utils.excel.ExcelExport;
import org.apache.http.util.TextUtils;
import org.java_websocket.client.WebSocketClient;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            int i = 0, kk = 0;
            try {
                Long diff = 60*300l;//60 * 30 * 300L;
                while (startTime <= endTime) {
                    huobiKlineReqDTO.setFrom(startTime);
                    huobiKlineReqDTO.setTo(startTime + diff);
                    huobiKlineReqDTO.setId("id10");
                    huobiKlineReqDTO.setReq("market.btcusdt.kline.1min");
                    startTime = diff+ 1 + startTime;
                    if(kk>=598&&kk<700){
                        client.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO));
                        i++;
                    }
                    kk++;
                }
                System.out.println(i);
                //HuoBiApiUtils.send(JSONStrReaderUtils.objToJson(huobiKlineReqDTO), "wb");
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (!HuoBiApiUtils.getEnd(i)) {
                Thread.sleep(3000);
                System.out.println("donging");
            }
            List<HuobiDetailVO> huobiDetailVOList = HuoBiApiUtils.getHuoBiData();
            DecimalFormat df = new DecimalFormat(".##");
            huobiDetailVOList.forEach(huobiDetailVO -> {
                huobiDetailVO.setAmount(df.format(new BigDecimal(huobiDetailVO.getAmount())));
                huobiDetailVO.setClose(df.format(new BigDecimal(huobiDetailVO.getClose())));
                huobiDetailVO.setHigh(df.format(new BigDecimal(huobiDetailVO.getHigh())));
                huobiDetailVO.setLow(df.format(new BigDecimal(huobiDetailVO.getLow())));
                huobiDetailVO.setOpen(df.format(new BigDecimal(huobiDetailVO.getOpen())));
                huobiDetailVO.setVol(df.format(new BigDecimal(huobiDetailVO.getVol())));
                huobiDetailVO.setDateDay(DateUtils.stampToDate(huobiDetailVO.getId(),"yyyy.MM.dd"));
                huobiDetailVO.setDateMin(DateUtils.stampToDate(huobiDetailVO.getId(),"HH:mm"));
            });

            ExcelExport excelExport = new ExcelExport("E:\\1.xlsx", "test1");
            //数据
            //调用
            String titleColumn[] = {"dateDay", "dateMin", "open", "close", "low", "high", "vol", "count", "amount"};
            String titleName[] = {"年月日", "时间", "开盘价", "收盘价", "最低价", "最高价", "成交额", "成交笔数", "成交量"};
            int titleSize[] = {13, 13, 13, 13, 13, 13, 13, 13, 13};
            //其他设置 set方法可全不调用
        /*    String colFormula[] = new String[5];
            colFormula[4] = "D@*12";   //设置第5列的公式
            pee.setColFormula(colFormula);
            pee.setAddress("A:D");  //自动筛选*/

            excelExport.wirteExcel(titleColumn, titleName, titleSize, huobiDetailVOList);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testtttt(){
        long timeStamp = 1509035400*1000l;  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间
        System.out.println(DateUtils.stampToDate("1509035400","yyyy.MM.dd"));
        System.out.println(DateUtils.stampToDate("1509035400","HH:mm"));
    }
}
