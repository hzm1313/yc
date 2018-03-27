package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by hasee on 2018/3/22.
 */
public class HuobiDetailVO {
    private String id;
    private BigDecimal amount;
    private int count;
    private long open;
    private long close;
    private long low;
    private long high;
    private BigDecimal vol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getClose() {
        return close;
    }

    public void setClose(long close) {
        this.close = close;
    }

    public long getLow() {
        return low;
    }

    public void setLow(long low) {
        this.low = low;
    }

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }
}
