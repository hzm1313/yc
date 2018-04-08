package com.cn.yc.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DT167 on 2018/3/2.
 */
public class TradeConstatns {
    public static double TRADE_PROFIT_CHECK_RATE = 0.01d;
    public static String HUOBI_HOST = "api.huobipro.com";
    public static String HUOBI_API_URL ="https://" + HUOBI_HOST;
    public static String HUOBI_TRADE_URL = "https://api.huobipro.com/v1";
    public static String HUOBI_MARKET_URL ="https://api.huobipro.com/market";
    public static String HUOBI_HADAX_MARKET_URL ="https://api.hadax.com/market";
    public static String HUOBI_HADAX_TRADE_URL ="https://api.hadax.com/v1";

    //下单交易
    public final static String ADD_ORDER_API="api";
    public final static String ADD_ORDER_MARGIN_API="margin-api";
    public final static String 	ADD_ORDER_BUY_MARKET="buy-market";
    public final static String 	ADD_ORDER_SELL_MARKET="sell-market";
    public final static String 	ADD_ORDER_BUY_LIMIT="buy-limit";
    public final static String 	ADD_ORDER_SELLL_LIMIT="sell-limit";

    public static String ACCESS_API = null;
    public static String SECRET_API = null;
    public static String PASSWORD_API = null;
    static {
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream("D:\\myWorkSpace\\yc\\src\\main\\resources\\ApiInfo.properties"));
            //InputStream in = new BufferedInputStream(new FileInputStream("ApiInfo.properties"));
            prop.load(in);     ///加载属性列表
            ACCESS_API = prop.getProperty("Huobi.access.api");
            SECRET_API = prop.getProperty("Huobi.secret.api");
            PASSWORD_API = prop.getProperty("Huobi.password.api");
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
