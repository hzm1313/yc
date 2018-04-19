package com.cn.yc.utils;

/**
 * Created by DT167 on 2017/6/2.
 */
public class Constants {
    public static String wxAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    public static String wxCreateMenu = "https://api.weixin.qq.com/cgi-bin/menu/create";
    //dev
    public static String appId = "wxcc2dba4029130b68";
    public static String secret = "d4624c36b6795d1d99dcf0547af5443d";
    //test
    // public static String appId = "wx20cf71e9d28f0dfa";
    //public static String secret = "d418a880eb016ea4abf261bb5d6c753b";

    public static String token = "";

    public final static String LINK_INFO = "LINK_INFO";

    public final static String HTML_INFO = "HTML_INFO";

    public final static String LINK_NEWS_INFO = "LINK_NEWS_INFO";

    public final static String LINK_INFO_LOCK = "LINK_iNFO_LOCK";

    public final static String REDIS_USED = "REDIS_USED";

    public final static String UYULIN_URL = "http://www.uyulin.com/ajax/allcoin_a/id/8?t=0.8065370987782623";

    public final static String[] LINK_KEY_ARRAY = {"玩客云", "链客", "玩客币"};

    public final static String TRADE_INFO_LIST = "TRADE_INFO_LIST";

    public final static String WJW = "玩家网";

    public final static String UYL = "悠雨林";

    public final static String PLAY_WKC = "playWkc";


    public final static String PLAYWKC_TRADE_INFO_KEY = "PLAYWKC_TRADE_INFO_KEY";
    public final static String UYL_TRADE_INFO_KEY = "UYL_TRADE_INFO_KEY";
    public final static String WJW_TRADE_INFO_KEY = "WJW_TRADE_INFO_KEY";

    public final static String NEWS_URL_HASH_KEY = "NEWS_URL_HASH_KEY";
    public final static String NEWS_TITLE_HASH_KEY = "NEWS_TITLE_HASH_KEY";

    public final static Integer maxTread = 10;

    //火币网，K线关键字
    public final static String HUOBI_KLINE="market.{0}.kline.{1}";
    public final static String HUOBI_1min="1min";
    public final static String HUOBI_5min="5min";
    public final static String HUOBI_15min="15min";
    public final static String HUOBI_30min="30min";
    public final static String HUOBI_60min="60min";
    public final static String HUOBI_1day="1day";
    public final static String HUOBI_1mon="1mon";
    public final static String HUOBI_1week="1week";
    public final static String HUOBI_1year="1year";
    public final static String HUOBI_BTCUSDT ="btcusdt";
    public final static String HUOBI_BCHUSDT ="bchusdt";
    public final static String HUOBI_ETHUSDT ="ethusdt";
    public final static String HUOBI_ETCUSDT ="etcusdt";
    public final static String HUOBI_LTCUSDT ="ltcusdt";
    public final static String HHUOBI_EOSUSDT ="eosusdt";
    public final static String HHUOBI_XRPUSDT ="xrpusdt";
    public final static String HHUOBI_OMGUSDT ="omgusdt";
    public final static String HHUOBI_DASHUSDT ="dashusdt";
    public final static String HHUOBI_ZECUSDT ="zecusdt";
    public final static String HUOBI_ADAUSDT = "adausdt";
    //火币创新
    public final static String HUOBI_BTMUSDT = "btmusdt";
    public final static String HUOBI_BTSUSDT = "btsusdt";
    public final static String HUOBI_ONTUSDT = "ontusdt";
    public final static String HUOBI_IOSTUSDT = "iostusdt";
    public final static String HUOBI_HTUSDT = "htusdt";
    public final static String HUOBI_TRXUSDT = "trxusdt";
    public final static String HUOBI_DTAUSDT = "dtausdt";
    public final static String HUOBI_NEOUSDT = "neousdt";
    public final static String HUOBI_QTUMUSDT = "qtumusdt";
    public final static String HUOBI_ELAUSDT = "elausdt";
    public final static String HUOBI_VENUSDT = "venusdt";
    public final static String HUOBI_THETAUSDT = "thetausdt";
    public final static String HUOBI_SNTUSDT = "sntusdt";
    public final static String HUOBI_ZILUSDT = "zilusdt";
    public final static String HUOBI_XEMUSDT = "xemusdt";
    public final static String HUOBI_SMTUSDT = "smtusdt";
    public final static String HUOBI_NASUSDT = "nasusdt";
    public final static String HUOBI_RUFFUSDT = "ruffusdt";
    public final static String HUOBI_HSRONTUSDT = "hsrusdt";
    public final static String HUOBI_LETUSDT = "letusdt";
    public final static String HUOBI_MDSUSDT = "mdsusdt";
    public final static String HUOBI_STORJUSDT = "storjusdt";
    public final static String HUOBI_ELFUSDT = "elfusdt";
    public final static String HUOBI_ITCUSDT = "itcusdt";
    public final static String HUOBI_CVCUSDT = "cvcusdt";
    public final static String HUOBI_GNTUSDT = "gntusdt";

    public final static String HUOBI_DEPTH ="market.{0}.depth.{1}";
    public final static String HUOBI_DEPTH_SETP0 = "step0";
    public final static String HUOBI_DEPTH_SETP1 = "step1";
    public final static String HUOBI_DEPTH_SETP2 = "step2";
    public final static String HUOBI_DEPTH_SETP3 = "step3";
    public final static String HUOBI_DEPTH_SETP4 = "step4";
    public final static String HUOBI_DEPTH_SETP5 = "step5";

    public final static String HUOBI_TRADE ="market.{0}.trade.detail";

}
