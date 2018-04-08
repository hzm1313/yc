package com.cn.yc.utils;

import com.cn.yc.bean.WkyVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Created by DT167 on 2017/6/2.
 */
public class JSONStrReaderUtils {
    protected static  Logger logger = LoggerFactory.getLogger(JSONStrReaderUtils.class);

    private static Gson sGson = new Gson();

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

    public static <T> T fromJson(Object json, Class<T> tClass) {
        try {

            if (json == null) {
                return null;
            }
            if (json instanceof JsonElement) {
                return sGson.fromJson((JsonElement) json, tClass);
            }
            return sGson.fromJson(json.toString(), tClass);
        } catch (Throwable t) {

        }
        return null;
    }

    public static<T> String objArryToJson(List<T> listT){
        return JSONArray.fromObject(listT).toString();
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

    public static String writeValue(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T readValue(String s, TypeReference<T> ref) throws IOException {
        return objectMapper.readValue(s, ref);
    }

    static final ObjectMapper objectMapper = createObjectMapper();

    static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // disabled features:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
