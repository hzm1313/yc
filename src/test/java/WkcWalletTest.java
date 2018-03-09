import com.cn.yc.bean.WalletBalanceVo;
import com.cn.yc.bean.WalletTradeApiRequestVo;
import com.cn.yc.utils.AmountConvert;
import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.utils.WkcWalltHttpUtils;
import org.junit.Test;

import java.math.BigDecimal;

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
        WalletBalanceVo walletBalanceVo = JSONStrReaderUtils.jsonToObj(result,WalletBalanceVo.class);
    }
}
