package com.cn.yc.ws;

import com.cn.yc.bean.InMessage;
import com.cn.yc.bean.OutMessage;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.Tools;
import com.thoughtworks.xstream.XStream;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.util.Date;


/**
 * Created by DT167 on 2017/6/2.
 */
public class WechatConnector extends HttpServlet {
    protected Logger logger = LoggerFactory.getLogger(WechatConnector.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        logger.info("init WechatConnector");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String signature = req.getParameter("signature");// 微信加密签名
            String timestamp = req.getParameter("timestamp");// 时间戳
            String nonce = req.getParameter("nonce");// 随机数
            String echostr = req.getParameter("echostr");//随机字符串
            String token = HttpUtils.getAccessToken(Constants.appId, Constants.secret);

            if (Tools.checkSignature(token, timestamp, nonce,signature)) {
                resp.getWriter().write(echostr);
            }
        } catch (Exception e) {
            logger.error("validating token error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            OutMessage oms = new OutMessage();
            ServletInputStream in = request.getInputStream();
            // 转换微信post过来的xml内容
            XStream xs = new XStream();
            xs.alias("xml", InMessage.class);
            String xmlMsg = Tools.convertStreamToString(in);
            System.out.println(xmlMsg);
            InMessage msg = (InMessage) xs.fromXML(xmlMsg);
            // 取得消息类型
            String type = msg.getMsgType();
            if("event".equals(type)){
                oms.setToUserName(msg.getFromUserName());
                oms.setFromUserName(msg.getToUserName());
                oms.setCreateTime(new Date().getTime()/1000);
                oms.setMsgType("text");
                oms.setContent("苦海中求生之人");
            }
            // 把发送发送对象转换为xml输出
            xs.alias("xml", OutMessage.class);
            System.out.println(xs.toXML(oms));

            response.getWriter().println(xs.toXML(oms));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    /*    response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        OutMessage oms = new OutMessage();
        ServletInputStream in = request.getInputStream();
        // 转换微信post过来的xml内容
        XStream xs = XStreamFactory.init(false);
        xs.alias("xml", InMessage.class);
        String xmlMsg = Tools.inputStream2String(in);
//        logger.info("微信post过来的xml:\n" + xmlMsg);
        InMessage msg = (InMessage) xs.fromXML(xmlMsg);
        try {
            // 取得消息类型
            String type = msg.getMsgType();
            // 针对不同类型消息进行处理
            if (type.equals(MessageProcessingHandler.MSG_TYPE_TEXT)) {
                oms = messageProcessingHandlerImpl.textTypeMsg(msg);
            } else if (type.equals(MessageProcessingHandler.MSG_TYPE_LOCATION)) {
                oms = messageProcessingHandlerImpl.locationTypeMsg(msg);
            } else if (type.equals(MessageProcessingHandler.MSG_TYPE_EVENT)) {
                oms = messageProcessingHandlerImpl.eventTypeMsg(msg);
            } else if (type.equals(MessageProcessingHandler.MSG_TYPE_VOICE)) {
                oms = messageProcessingHandlerImpl.voiceTypeMsg(msg);
            } else if (type.equals(MessageProcessingHandlerImpl.MSG_TYPE_IMAGE)) {
                oms = messageProcessingHandlerImpl.imageTypeMsg(msg);
            }

        } catch (Exception e) {
            logger.error("response wechat error [{}]", e.getMessage());
            oms = null;
        }
        if (oms != null) {
            // 设置发送信息
            oms.setCreateTime(new Date().getTime());
            oms.setToUserName(msg.getFromUserName());//发送方用户微信号
            oms.setFromUserName(msg.getToUserName());//接收方微信公众号

            // 把发送发送对象转换为xml输出
            xs = XStreamFactory.init(false);
            xs.alias("xml", OutMessage.class);
            xs.alias("item", Articles.class);
            xs.alias("Music", Music.class);
            xs.toXML(oms, response.getWriter());
        } else {
            //直接回复""或者"success",微信服务器不会对此作任何处理，并且不会发起重试,则微信不会提示"该公众号暂时无法提供服务，请稍后再试"
            response.getWriter().write("success");
        }*/
    }
}
