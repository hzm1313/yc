import com.cn.yc.bean.TransactionCountVo;
import com.cn.yc.bean.TransactionRecordsVo;
import com.cn.yc.bean.WalletBalanceVo;
import com.cn.yc.bean.WalletTradeApiRequestVo;
import com.cn.yc.utils.AmountConvert;
import com.cn.yc.utils.FileReadUtils;
import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.utils.WkcWalltHttpUtils;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by DT167 on 2018/3/6.
 */
public class WkcWalletTest {
    //查询账户信息
    @Test
    public void WkcWalletInfo() {
        String result = WkcWalltHttpUtils.getBalance("0xd9ebe52040954f9a212023b9905188e0482f131c");
        WalletBalanceVo walletBalanceVo = JSONStrReaderUtils.jsonToObj(result, WalletBalanceVo.class);
        BigDecimal bigDecimal = AmountConvert.fromWei(walletBalanceVo.getResult(), AmountConvert.Unit.ETHER);
        System.out.println(bigDecimal.toString());
    }

    //转账记录
    @Test
    public void WkcWalletTradeInfo() {
        String result = WkcWalltHttpUtils.getTradeRecord("0xd9ebe52040954f9a212023b9905188e0482f131c");
        TransactionRecordsVo transactionRecordsVo = JSONStrReaderUtils.jsonToObj(result, TransactionRecordsVo.class);
        System.out.println(JSONStrReaderUtils.objToJson(transactionRecordsVo));
    }

    //交易次数
    @Test
    public void WkcWalletTradeNum() {
        String result = WkcWalltHttpUtils.getTradeRecordNum("0xd9ebe52040954f9a212023b9905188e0482f131c");
        TransactionCountVo transactionCountVo = JSONStrReaderUtils.jsonToObj(result, TransactionCountVo.class);
        BigInteger count = AmountConvert.toBigNumber(transactionCountVo.getResult());
        System.out.println(count);
    }

    //转账
    @Test
    public void WkcWalletTrade() throws IOException {
        //请挂VPN
        String fromAddress = "0xd9ebe52040954f9a212023b9905188e0482f131c";
        String toAddress = "0x4a6966efd47c181b84d2506b6375aab7911ec004";
        String pathname = "F:\\yc\\密码.txt";
        String password = FileReadUtils.readFileReturnString(pathname);
        String walletPath = "F:\\yc\\d9ebe52040954f9a212023b9905188e0482f131c";
        BigDecimal tradeAmount = new BigDecimal(0.1);
        try {
            String result = WkcWalltHttpUtils.sendTrade(fromAddress, password, walletPath, tradeAmount, toAddress);
            System.out.println(result);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WkcTtest() {
        BigDecimal bigDecimal = AmountConvert.fromWei("0x215ec7adb87ea23b", AmountConvert.Unit.ETHER);
        System.out.println(bigDecimal.toString());
    }

}
