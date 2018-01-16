import com.cn.yc.bean.LinkTokenSpiderInfo;
import com.cn.yc.spider.SpiderBaseModel;
import com.cn.yc.spider.SpiderLinkTokenInfoModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hasee on 2018/1/15.
 */
public class SpiderBaseModelTest {
    @Test
    public void spiderWjwTest(){
        SpiderLinkTokenInfoModel spiderLinkTokenInfoModel = new SpiderBaseModel();
        LinkTokenSpiderInfo linkTokenSpiderInfo = spiderLinkTokenInfoModel.spiderWjw();
        Assert.assertNotNull(linkTokenSpiderInfo);
    }

    @Test
    public void spiderPlayWkcTest(){
        SpiderLinkTokenInfoModel spiderLinkTokenInfoModel = new SpiderBaseModel();
        LinkTokenSpiderInfo linkTokenSpiderInfo = spiderLinkTokenInfoModel.spiderPlayWkc();
        Assert.assertNotNull(linkTokenSpiderInfo);
    }

    @Test
    public void spiderUylTest(){
        SpiderLinkTokenInfoModel spiderLinkTokenInfoModel = new SpiderBaseModel();
        LinkTokenSpiderInfo linkTokenSpiderInfo = spiderLinkTokenInfoModel.spiderUyl();
        Assert.assertNotNull(linkTokenSpiderInfo);
    }
}
