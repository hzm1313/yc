package huobi;

import com.cn.yc.thread.BaseThreadPool;
import com.cn.yc.utils.DateUtils;
import com.cn.yc.utils.HuoBiApiUtils;
import com.cn.yc.utils.excel.ExcelExport;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

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
        Long startTime = DateUtils.getDateUnix("2017/10/27 00:00:00");
        Long endTime = DateUtils.getDateUnix(new Date().toString());
        System.out.println(startTime);
     while(1==1){

     }
       // HuoBiApiUtils.send("{ping: 18212558000}");
    }

    @Test
    public void test(){
        //xport(String fileName, String FilePath, List<String> rowNameList, ArrayList<ArrayList<String>> dataList)
        ArrayList<String> nameList = new ArrayList<String>();
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
        }
    }
}
