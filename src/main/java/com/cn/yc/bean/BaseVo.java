package com.cn.yc.bean;

import com.cn.yc.utils.JSONStrReaderUtils;

public class BaseVo {
    public String toJson() {
        return JSONStrReaderUtils.objToJson(this);
    }
}
