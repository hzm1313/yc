package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/3/27.
 */
public class HuobiDataVO {
    private String id;//K线 Id
    private String amount;//成交量
    private String count;//成交笔数
    private String open;//开盘价
    private String close;//收盘价
    private String low;//最低价
    private String high;//最高价
    private String vol;//成交额, 即 sum(每一笔成交价 * 该笔的成交量)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}
