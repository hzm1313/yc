import com.cn.yc.bean.TransactionCountVo;
import com.cn.yc.bean.TransactionRecordsVo;
import com.cn.yc.bean.WalletBalanceVo;
import com.cn.yc.bean.WalletTradeApiRequestVo;
import com.cn.yc.utils.AmountConvert;
import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.utils.WkcWalltHttpUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by DT167 on 2018/3/6.
 */
public class WkcWalletTest {
    @Test
    public void WkcWalletInfo(){
        String result = WkcWalltHttpUtils.getBalance("0xd9ebe52040954f9a212023b9905188e0482f131c");
        WalletBalanceVo walletBalanceVo = JSONStrReaderUtils.jsonToObj(result,WalletBalanceVo.class);
        BigDecimal bigDecimal = AmountConvert.fromWei(walletBalanceVo.getResult(), AmountConvert.Unit.ETHER);
        System.out.println(bigDecimal.toString());
    }

    @Test
    public void WkcWalletTradeInfo(){
        String result = WkcWalltHttpUtils.getTradeRecord("0xd9ebe52040954f9a212023b9905188e0482f131c");
        TransactionRecordsVo transactionRecordsVo = JSONStrReaderUtils.jsonToObj(result,TransactionRecordsVo.class);
        System.out.println(JSONStrReaderUtils.objToJson(transactionRecordsVo));
    }

    @Test
    public void WkcWalletTradeNum(){
        //挂VPN
        String result = WkcWalltHttpUtils.getTradeRecordNum("0xd9ebe52040954f9a212023b9905188e0482f131c");
        TransactionCountVo transactionCountVo = JSONStrReaderUtils.jsonToObj(result,TransactionCountVo.class);
        BigInteger count = AmountConvert.toBigNumber(transactionCountVo.getResult());
        System.out.println(count);
    }

    @Test
    public void WkcWalletTrade(){
        //挂VPN
        String yh1="0xd9ebe52040954f9a212023b9905188e0482f131c";
        String yh2="0x4a6966efd47c181b84d2506b6375aab7911ec004";

    }

    @Test
    public void WkcTtest(){
        BigDecimal bigDecimal = AmountConvert.fromWei("0x215ec7adb87ea23b", AmountConvert.Unit.ETHER);
        System.out.println(bigDecimal.toString());
    }

}
