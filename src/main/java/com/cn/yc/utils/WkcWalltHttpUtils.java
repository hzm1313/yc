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
    public String getRradeRemainSum(){

        return null;
    }

    public String getTradeRecord(String address){
        List<String> data = new ArrayList<>();
        data.add(address);
        data.add("0");
        data.add("0");
        data.add("1");
        data.add("100");
        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("data", "caonimabigongjinidayeo"));
            Response response = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com");
                    //HttpUtil.postWalletApi("getTransactionRecords", JsonHelper.toJson(data));
            String json = response.body().string();
            TransactionRecordsVo transactionRecordsVo = JSONStrReaderUtils.jsonToObj(json, TransactionRecordsVo.class);
            return transactionRecordsVo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
