package com.cn.yc.utils;

import com.cn.yc.bean.TransactionRecordsVo;
import com.cn.yc.bean.WalletTradeApiRequestVo;
import okhttp3.Response;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

/**
 * Created by DT167 on 2018/3/5.
 */
public class WkcWalltHttpUtils {
    /**
     * @return
     */
    public static String getRradeRemainSum() {
        return null;
    }

    /**
     * 获取交余额
     *
     * @param address
     * @return
     */
    public static String getBalance(String address) {
        ArrayList<String> parmsList = new ArrayList<>();
        parmsList.add(address);
        parmsList.add("latest");
        WalletTradeApiRequestVo walletTradeApiRequestVo = new WalletTradeApiRequestVo();
        walletTradeApiRequestVo.setId(1);
        walletTradeApiRequestVo.setJsonrpc("2.0");
        walletTradeApiRequestVo.setMethod("eth_getBalance");
        walletTradeApiRequestVo.setParams(parmsList);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/", headerMap, walletTradeApiRequestVo.toJson());
        return result;
    }

    /**
     * 获取交易记录
     *
     * @param address
     * @return
     */
    public static String getTradeRecord(String address) {
        ArrayList<String> parmsList = new ArrayList<>();
        parmsList.add(address);
        parmsList.add("latest");
        parmsList.add("0");
        parmsList.add("0");
        parmsList.add("1");
        parmsList.add("10");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        System.out.println(parmsList.toString());
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/", headerMap,parmsList.toString());
        return result;
    }

    public static String getTradeRecordNum(String address){
        ArrayList<String> parmsList = new ArrayList<>();
        parmsList.add(address);
        parmsList.add("pending");
        WalletTradeApiRequestVo walletTradeApiRequestVo = new WalletTradeApiRequestVo();
        walletTradeApiRequestVo.setId(1);
        walletTradeApiRequestVo.setJsonrpc("2.0");
        walletTradeApiRequestVo.setMethod("eth_getTransactionCount");
        walletTradeApiRequestVo.setParams(parmsList);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/", headerMap, walletTradeApiRequestVo.toJson());
        return result;
    }
}
