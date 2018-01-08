package com.cn.yc.trade;

/**
 * Created by DT167 on 2018/1/5.
 */
public abstract class AbstractTradeFactory {
    public abstract WjwTrade getWjwTrade();
    public abstract UylTrade getUylTrade();
}