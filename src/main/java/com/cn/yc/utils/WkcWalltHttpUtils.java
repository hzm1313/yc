package com.cn.yc.utils;

import com.cn.yc.bean.TransactionRecordsVo;
import okhttp3.Response;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DT167 on 2018/3/5.
 */
public class WkcWalltHttpUtils {
    /**
     *
     * @return
     */
    public static String getRradeRemainSum(){

        return null;
    }

    /**
     * 获取交易记录
     * @param address
     * @return
     */
    public static String getTradeRecord(String address){
        List<String> data = new ArrayList<>();
        data.add(address);
        data.add("0");
        data.add("0");
        data.add("1");
        data.add("100");
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("data", data.toString()));
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/getTransactionRecords",data.toString());
        return result;
    }
}
