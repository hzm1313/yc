package com.cn.yc.service.impl;

import com.cn.yc.bean.NewsVO;
import com.cn.yc.component.BkbCompoent;
import com.cn.yc.bean.WkyVO;
import com.cn.yc.service.QqRoobotService;
import com.cn.yc.utils.JsonUtils;
import com.cn.yc.web.ws.WechatConnector;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by hasee on 2017/12/22.
 */
@Service
public class QqRoobotServiceImpl implements QqRoobotService {
    protected Logger logger = LoggerFactory.getLogger(WechatConnector.class);

    private SmartQQClient client;

    @Override
    public void initStartQqRoot() {
        if (client != null) {
            return;
        }
        //创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的回调，如果你不需要接收消息，可以传null
        client = new SmartQQClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                StringBuilder hfStrBuilder = null;
                String replayContent = null;
                try{
                    if (message.getContent().contains("行情")) {
                        String result = BkbCompoent.getBkbString();
                        WkyVO wkyVO = JsonUtils.jsonToObj(result, WkyVO.class);
                        if (wkyVO != null) {
                            hfStrBuilder =new StringBuilder("玩家网:" + wkyVO.getWjw() + "\n" + "悠雨林:" + wkyVO.getUyl() + "\n更多信息请点击http://www.wlsecret.com/");
                            replayContent = hfStrBuilder.toString();
                            client.sendMessageToGroup(message.getGroupId(),replayContent );
                        }
                    } else if ("新闻".equals(message.getContent().trim())) {
                        List<NewsVO> newsVOList = BkbCompoent.getBkbNewsList(5);
                        hfStrBuilder = new StringBuilder();
                        for (int i = 0; newsVOList != null && i<newsVOList.size() ; i++) {
                            hfStrBuilder.append( newsVOList.get(0).getTitle()+ "\n" + newsVOList.get(i).getUrl()+"\n");
                        }
                        hfStrBuilder.append("更多信息请点击http://www.wlsecret.com/");
                        replayContent = hfStrBuilder.toString();
                        client.sendMessageToGroup(message.getGroupId(), replayContent);
                    }
                }catch (Exception e){
                    logger.error("*** qq roobot GroupMessage {}",message);
                    logger.error("*** qq roobot error {}",e.getMessage());
                }

            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println(message.getContent());
            }
        });
        //登录成功后便可以编写你自己的业务逻辑了
        List<Category> categories = client.getFriendListWithCategory();
        for (Category category : categories) {
            System.out.println(category.getName());
            for (Friend friend : category.getFriends()) {
                System.out.println("————" + friend.getNickname());
            }
        }
/*        while (true) {
            if (11 != 11) {
                break;
            }
        }*/
        //使用后调用close方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
     /*   try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
