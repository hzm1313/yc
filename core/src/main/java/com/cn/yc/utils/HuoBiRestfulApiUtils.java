package com.cn.yc.utils;

import com.cn.yc.bean.ApiResponse;
import com.cn.yc.bean.HuoBi.*;
import com.cn.yc.exception.ApiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by hasee on 2018/4/8.
 */
public class HuoBiRestfulApiUtils {

    static final int CONN_TIMEOUT = 5;
    static final int READ_TIMEOUT = 5;
    static final int WRITE_TIMEOUT = 5;

    //static final String API_HOST = TradeConstatns.HUOBI_HOST;
    //static final String API_URL = TradeConstatns.HUOBI_API_URL;
    static final String API_HOST = "api.huobipro.com";
    static final String API_URL = "https://" + API_HOST;
    static final MediaType JSON = MediaType.parse("application/json");
    static final OkHttpClient client = createOkHttpClient();

    static final String accessKeyId = TradeConstatns.ACCESS_API;
    static final String accessKeySecret = TradeConstatns.SECRET_API;
    static final String assetPassword = TradeConstatns.PASSWORD_API;


    /**
     * 查询所有账户信息
     *
     * @return List of accounts.
     */
    public static List<HuobiAccountsResDO> getAccounts() {
        ApiResponse<List<HuobiAccountsResDO>> resp =
                get("/v1/account/accounts", null, new TypeReference<ApiResponse<List<HuobiAccountsResDO>>>() {
                });
        return resp.checkAndReturn();
    }

    /**
     * 创建订单
     *
     * @param request CreateOrderRequest object.
     * @return Order id.
     */
    public static Long createOrder(HuobiAddOrdersReqDO request) {
        ApiResponse<Long> resp =
                post("/v1/order/orders/place", request, new TypeReference<ApiResponse<Long>>() {
                });
        return resp.checkAndReturn();
    }

    /**
     * POST /v1/order/orders/{order-id}/submitcancel 申请撤销一个订单请求
     *
     * @param orderId
     * @return
     */
    public static HuobiRecOrderResDO submitcancel(String orderId) {
        HuobiRecOrderResDO resp = get("/v1/order/orders/" + orderId + "/submitcancel", null, new TypeReference<HuobiRecOrderResDO>() {
        });
        return resp;
    }

    /**
     * POST /v1/order/orders/batchcancel 批量撤销订单
     *
     * @param orderList
     * @return
     */
    public static BatchcancelResponse submitcancels(List orderList) {
        BatchcancelResponse resp = post("/v1/order/orders/batchcancel", orderList, new TypeReference<BatchcancelResponse<Batchcancel<List, BatchcancelBean>>>() {
        });
        return resp;
    }

    /**
     * GET /v1/order/orders/{order-id} 查询某个订单详情
     *
     * @param orderId
     * @return TODO 返回为order-id，HuobiOrderDetailResDO用这个类，但是-这里可能有问题，所以先不转
     */
    public static BaseResponse ordersDetail(String orderId) {
        BaseResponse resp = get("/v1/order/orders/" + orderId, null, new TypeReference<BaseResponse>() {
        });
        return resp;
    }

    /**
     * GET /v1/order/orders/{order-id}/matchresults 查询某个订单的成交明细
     *
     * @param orderId
     * @return
     */
    public static BaseResponse matchresults(String orderId) {
        BaseResponse resp = get("/v1/order/orders/" + orderId + "/matchresults", null, new TypeReference<BaseResponse>() {
        });
        return resp;
    }

    /**
     * 查看当前/历史委托
     * @param req
     * @return
     */
    public static BaseResponse getHistoryEntrust(HuobiHistoryEntrustReqDO req) {
        HashMap map = new HashMap();
        map.put("symbol", req.getSymbol());
        map.put("states", req.getStates());
        //剩余字段看文档，非必须
        BaseResponse resp = get("/v1/order/orders/", map, new TypeReference<BaseResponse>() {
        });
        return resp;
    }

    /**
     * 查看当前/历史交易
     * @param req
     * @return
     */
    public static BaseResponse getHistoryTrade(HuobiHistoryTradeReqDO req) {
        HashMap map = new HashMap();
        map.put("symbol", req.getSymbol());
        //剩余字段看文档，非必须
        BaseResponse resp = get("/v1/order/matchresults/", map, new TypeReference<BaseResponse>() {
        });
        return resp;
    }

    // send a GET request.
    private static <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
        if (params == null) {
            params = new HashMap<>();
        }
        return call("GET", uri, null, params, ref);
    }

    // send a POST request.
    private static <T> T post(String uri, Object object, TypeReference<T> ref) {
        return call("POST", uri, object, new HashMap<String, String>(), ref);
    }

    // call api by endpoint.
    private static <T> T call(String method, String uri, Object object, Map<String, String> params,
                              TypeReference<T> ref) {
        ApiSignature sign = new ApiSignature();
        sign.createSignature(accessKeyId, accessKeySecret, method, API_HOST, uri, params);
        try {
            Request.Builder builder = null;
            if ("POST".equals(method)) {
                RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
                builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).post(body);
            } else {
                builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).get();
            }
            if (assetPassword != null) {
                builder.addHeader("AuthData", authData());
            }
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            return JsonUtil.readValue(s, ref);
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    private static String authData() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(assetPassword.getBytes(StandardCharsets.UTF_8));
        md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
        Map<String, String> map = new HashMap<>();
        map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
        try {
            return ApiSignature.urlEncode(JsonUtil.writeValue(map));
        } catch (IOException e) {
            throw new RuntimeException("Get json failed: " + e.getMessage());
        }
    }

    // Encode as "a=1&b=%20&c=&d=AAA"
    private static String toQueryString(Map<String, String> params) {
        return String.join("&", params.entrySet().stream().map((entry) -> {
            return entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue());
        }).collect(Collectors.toList()));
    }

    // create OkHttpClient:
    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

}


/**
 * API签名，签名规范：
 * <p>
 * http://docs.aws.amazon.com/zh_cn/general/latest/gr/signature-version-2.html
 *
 * @Date 2018/1/14
 * @Time 16:02
 */
class ApiSignature {

    final Logger log = LoggerFactory.getLogger(getClass());

    static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    static final ZoneId ZONE_GMT = ZoneId.of("Z");

    /**
     * 创建一个有效的签名。该方法为客户端调用，将在传入的params中添加AccessKeyId、Timestamp、SignatureVersion、SignatureMethod、Signature参数。
     *
     * @param appKey       AppKeyId.
     * @param appSecretKey AppKeySecret.
     * @param method       请求方法，"GET"或"POST"
     * @param host         请求域名，例如"be.huobi.com"
     * @param uri          请求路径，注意不含?以及后的参数，例如"/v1/api/info"
     * @param params       原始请求参数，以Key-Value存储，注意Value不要编码
     */
    public void createSignature(String appKey, String appSecretKey, String method, String host,
                                String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(host.toLowerCase()).append('\n') // Host
                .append(uri).append('\n'); // /path
        params.remove("Signature");
        params.put("AccessKeyId", appKey);
        params.put("SignatureVersion", "2");
        params.put("SignatureMethod", "HmacSHA256");
        params.put("Timestamp", gmtNow());
        // build signature:
        SortedMap<String, String> map = new TreeMap<>(params);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append('=').append(urlEncode(value)).append('&');
        }
        // remove last '&':
        sb.deleteCharAt(sb.length() - 1);
        // sign:
        Mac hmacSha256 = null;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey =
                    new SecretKeySpec(appSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        }
        String payload = sb.toString();
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        String actualSign = Base64.getEncoder().encodeToString(hash);
        params.put("Signature", actualSign);
        if (log.isDebugEnabled()) {
            log.debug("Dump parameters:");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                log.debug("  key: " + entry.getKey() + ", value: " + entry.getValue());
            }
        }
    }

    /**
     * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不是+。
     *
     * @param s String字符串
     * @return URL编码后的字符串
     */
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    /**
     * Return epoch seconds
     */
    long epochNow() {
        return Instant.now().getEpochSecond();
    }

    String gmtNow() {
        return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
    }
}


class JsonUtil {

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
