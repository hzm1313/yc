package com.cn.yc.bean.HuoBi;

/**
 * Created by hasee on 2018/4/8.
 */
public class HuobiHistoryTradeResDO {
    private String created_at;
    private String filled_amount;
    private String filled_fees;
    private String id;
    private String match_id;
    private String order_id;
    private String price;
    private String source;
    private String symbol;
    private String type;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFilled_amount() {
        return filled_amount;
    }

    public void setFilled_amount(String filled_amount) {
        this.filled_amount = filled_amount;
    }

    public String getFilled_fees() {
        return filled_fees;
    }

    public void setFilled_fees(String filled_fees) {
        this.filled_fees = filled_fees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
