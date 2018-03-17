package com.cn.yc.utils;

import com.cn.wkc.ethereum.core.Transaction;
import com.cn.wkc.ethereum.util.Unit;
import com.cn.wkc.ethereum.wallet.CommonWallet;
import com.cn.wkc.ethereum.wallet.Wallet;
import com.cn.yc.bean.TransactionCountVo;
import com.cn.yc.bean.TransactionRecordsVo;
import com.cn.yc.bean.WalletTradeApiRequestVo;
import com.cn.yc.service.LinkService;
import okhttp3.Headers;
import okhttp3.Response;
import oracle.jrockit.jfr.VMJFR;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by DT167 on 2018/3/5.
 */
public class WkcWalltHttpUtils {
    protected static Logger LOGGER = LoggerFactory.getLogger(WkcWalltHttpUtils.class);

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
        parmsList.add("0");
        parmsList.add("0");
        parmsList.add("1");
        parmsList.add("10");
        WalletTradeApiRequestVo walletTradeApiRequestVo = new WalletTradeApiRequestVo();
        walletTradeApiRequestVo.setId(1);
        walletTradeApiRequestVo.setJsonrpc("2.0");
        walletTradeApiRequestVo.setMethod("eth_getBalance");
        walletTradeApiRequestVo.setParams(parmsList);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/getTransactionRecords", headerMap, JSONStrReaderUtils.objArryToJson(parmsList));
        return result;
    }

    /**
     * 交易次数
     *
     * @param address
     * @return
     */
    public static String getTradeRecordNum(String address) {
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

    /**
     * 转账
     * 1.钱包文件+密码
     * 2.获取交易次数
     * 3.获取gas,gasList
     * 4.对上面参数进行加密，并用钱包进行签名
     * 5.获取hash，并签名
     *
     * @return
     */
    public static String sendTrade(String fromAddress, String password, String walletPath,
                                   BigDecimal tradeAmount, String toAddress) throws IOException, GeneralSecurityException {
        Wallet wallet = null;
        String test = FileReadUtils.readFileReturnString(walletPath);
        wallet = CommonWallet.fromV3(test, password);
        //1.调用之前输入
        //2.获取交易次数
        TransactionCountVo transactionCountVo = JSONStrReaderUtils.jsonToObj(getTradeRecordNum(fromAddress), TransactionCountVo.class);
        BigInteger count = AmountConvert.toBigNumber(transactionCountVo.getResult());
        AtomicLong txInd = new AtomicLong(count.intValue());
        BigInteger nonce = BigInteger.valueOf(txInd.getAndIncrement());
        //3.获取gas,gasList
        //以下两组数据参考   https://github.com/ImbaQ/MyLinkToken_js/blob/master/js/app.js  57行
        BigInteger gasLimit = new BigInteger("186a0", 16);
        BigInteger gasPrice = new BigInteger("174876e800", 16);
        BigInteger amount = Unit.valueOf(Unit.ether.toString()).toWei(String.valueOf(tradeAmount));
        //4.对上面参数进行加密，并用钱包进行签名
        Transaction tx = Transaction.create(toAddress.replace("0x", ""), amount, nonce, gasPrice, gasLimit, null);
        try {
            tx.sign(wallet);
        } catch (SignatureException e) {
            LOGGER.error("*** WkcWalltHttpUtils sendTrade {}","签名失败");
        }
        byte[] encoded = tx.getEncoded();

        WalletTradeApiRequestVo walletApiRequestVo = new WalletTradeApiRequestVo();
        walletApiRequestVo.setJsonrpc("2.0")
                .setMethod("eth_sendRawTransaction")
                .addParam("0x" + Hex.toHexString(encoded))
                .setId(1);
        String parm = null;
        ArrayList<String> parmsList = new ArrayList<>();
        parmsList.add(parm);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("NC", "IN");
        String result = HttpUtils.sendPostRequest("https://walletapi.onethingpcs.com/", headerMap, walletApiRequestVo.toJson());
        return result;
    }
}
