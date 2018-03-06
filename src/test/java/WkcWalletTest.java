import com.cn.yc.utils.WkcWalltHttpUtils;
import org.junit.Test;

/**
 * Created by DT167 on 2018/3/6.
 */
public class WkcWalletTest {
    @Test
    public void WkcWalletInfo(){
        System.out.println(WkcWalltHttpUtils.getTradeRecord("0xd9ebe52040954f9a212023b9905188e0482f131c"));
    }
}
