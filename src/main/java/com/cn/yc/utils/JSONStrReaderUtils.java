package com.cn.yc.utils;

import com.cn.yc.bean.WkyVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by DT167 on 2017/6/2.
 */
public class JSONStrReaderUtils {
    protected static  Logger logger = LoggerFactory.getLogger(JSONStrReaderUtils.class);

    public static String objToJson(Object object){
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }


    public static String jsonToMapGetVal(String json,String key){
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (!StringUtils.isEmpty(json) && !StringUtils.isEmpty(key)) {
                Map<String, String> map = mapper.readValue(json, java.util.Map.class);
                return map.get(key);
            }
        } catch (Exception e) {
            logger.error(" " + e.getMessage());
        }
        return "";
    }

    public static <T> T jsonToObj(String jsonStr,Class<T> clazz){
        JSONObject obj =  JSONObject.fromObject(jsonStr);
        return (T) JSONObject.toBean(obj,clazz);
    }

    public static String read(HttpEntity httpEntity) {
        StringBuilder sb = new StringBuilder(256);
        try {
            DataInputStream inStream = new DataInputStream(httpEntity.getContent());

            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            inStream.close();
        } catch (Exception e) {
            logger.error("Read jsonStr failed due to [{}]", e.getMessage(), e);
        }
        return sb.toString();
    }
}
