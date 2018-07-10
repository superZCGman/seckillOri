package com.limai.user.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

/**
 * restful请求工具类
 */
public enum RestHttpUtil {
    INSTANCE;

    @Getter
    private RestTemplate restTemplate = initRest();

    /* 初始化单例 */
    private RestTemplate initRest() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new ByteArrayHttpMessageConverter());

        // 设置httpMessageConverter
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter4();

        // 设置fastJsonConfig
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);

        // 设置mediaTypes
        ArrayList<MediaType> mediaTypes = new ArrayList<>(Arrays.asList(MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XHTML_XML,
                MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));
        fastJsonHttpMessageConverter4.setSupportedMediaTypes(mediaTypes);

        messageConverters.add(fastJsonHttpMessageConverter4);
        RestTemplate restTemplate = new RestTemplate(messageConverters);

        return restTemplate;
    }

    /**
     * 发送post请求
     *
     * @param uri, param, contentType
     * @return String
     */
    public String restPost(String uri, Object param, String contentType) throws Exception {
        ResponseEntity<String> responseEntity = restPost(uri, null, null, param, contentType);

        return responseEntity == null ? "" : responseEntity.getBody();
    }

    /**
     * 发送post请求, uri参数可变
     *
     * @param uri, uriVeriables, param, contentType
     * @return String
     */
    public String restPost(String uri, Map<String, String> uriVeriables, Object param, String contentType) throws Exception {
        ResponseEntity<String> responseEntity = restPost(uri, uriVeriables, null, param, contentType);

        return responseEntity == null ? "" : responseEntity.getBody();
    }

    /**
     * 发送post请求, uri参数可变, 带请求头
     *
     * @param uri, veriable(uri参数中{}变量替换成对应key的value), headers, param, contentType
     * @return ResponseEntity<String>
     */
    @SuppressWarnings("unchecked")
    public ResponseEntity<String> restPost(String uri, Map<String, String> uriVeriables, Map<String, String> headers, Object param, String contentType) throws Exception {

        // 校验uri非空
        checkURI(uri);

        // 设置requestHeaders
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", StringUtil.isNull(contentType) ? "application/x-www-form-urlencoded" : contentType);
        if (headers != null && headers.size() > 0) {
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for (Map.Entry<String, String> entry : set) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        // 设置requestEntity
        HttpEntity httpEntity = null;
        if (param instanceof String) {
            httpEntity = new HttpEntity<String>((String) param, httpHeaders);
        } else if (param instanceof LinkedMultiValueMap) {
            httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>((LinkedMultiValueMap) param, httpHeaders);
        } else if (param instanceof JSONObject) {
            httpEntity = new HttpEntity<JSONObject>((JSONObject) param, httpHeaders);
        } else if (param != null) {
            throw new RuntimeException("Param参数必须为String, JSONObject或LinkedMultiValueMap类型");
        }

        RestTemplate temp = getRestTemplate();
        ResponseEntity<String> responseEntity;

        // uriVeriables不为空时, 替换uri中的参数
        if (uriVeriables != null && uriVeriables.size() > 0) {
            responseEntity = temp.exchange(uri, HttpMethod.POST, httpEntity, String.class, uriVeriables);
        } else {
            responseEntity = temp.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        }

        return responseEntity;
    }

    /**
     * 发送get请求
     *
     * @param uri
     * @return String
     */
    public String restGet(String uri) throws Exception {
        ResponseEntity<String> responseEntity = restGet(uri, null, null);

        return responseEntity == null ? "" : responseEntity.getBody();
    }

    /**
     * 发送get请求, uri参数可变
     *
     * @param uri, uriVeriables
     * @return String
     */
    public String restGet(String uri, Map<String, String> uriVeriables) throws Exception {
        ResponseEntity<String> responseEntity = restGet(uri, uriVeriables, null);

        return responseEntity == null ? "" : responseEntity.getBody();
    }

    /**
     * 发送get请求, uri参数可变, 带请求头, 获取返回byte[]
     *
     * @param uri, uriVeriables, headers
     * @return String
     */
    public ResponseEntity<byte[]> restGetByte(String uri, Map<String, String> uriVeriables, Map<String, String> headers) throws Exception {

        // 校验uri非空
        checkURI(uri);

        // 设置requestHeaders
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null && headers.size() > 0) {
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for (Map.Entry<String, String> entry : set) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        // 设置requestEntity
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(httpHeaders);

        RestTemplate temp = getRestTemplate();
        ResponseEntity<byte[]> responseEntity;

        // uriVeriables不为空时, 替换uri中的参数
        if (uriVeriables != null && uriVeriables.size() > 0) {
            responseEntity = temp.exchange(uri, HttpMethod.GET, requestEntity, byte[].class, uriVeriables);
        } else {
            responseEntity = temp.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);
        }

        return responseEntity;
    }

    /**
     * 发送get请求, uri参数可变, 带请求头
     *
     * @param uri, uriVeriables, headers
     * @return String
     */
    public ResponseEntity<String> restGet(String uri, Map<String, String> uriVeriables, Map<String, String> headers) throws Exception {

        // 校验uri非空
        checkURI(uri);

        // 设置requestHeaders
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null && headers.size() > 0) {
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for (Map.Entry<String, String> entry : set) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        // 设置requestEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);

        RestTemplate temp = getRestTemplate();
        ResponseEntity<String> responseEntity;

        // uriVeriables不为空时, 替换uri中的参数
        if (uriVeriables != null && uriVeriables.size() > 0) {
            responseEntity = temp.exchange(uri, HttpMethod.GET, requestEntity, String.class, uriVeriables);
        } else {
            responseEntity = temp.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        }

        return responseEntity;
    }

    /* 校验请求uri */
    private void checkURI(String uri) {
        if (StringUtil.isNull(uri)) {
            throw new RuntimeException("Rest请求uri不能为空");
        }
    }

    /**
     * 添加http代理
     *
     * @param host, port
     */
    public void httpProxy(String host, String port) {

        // 针对http开启代理
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", "" + port);

        // 针对https开启代理
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", "" + port);
    }

    public static void main(String[] args) {
        RestHttpUtil.INSTANCE.httpProxy("proxy.it.taikang.com", "8080");

        List<String> list = new ArrayList<>();
        list.add("https://api.weixin.qq.com/cgi-bin/user/info?access_token=7_om-KjKl7kA-frpsnGn7wfkhUbrTzILIstmSNhFAgZZQLtvoGPNF5Z_PQKvmdSRebIsn1NyiU3VDyHTCEi-wEmraQoSmwfBIezO__xddA-n5OUl42grjI-3MNOQDhFSG3TVLGrHJjhuFfmCV6LJXaAIAFDR&openid=o2wBP0-JEwbIoQJqJnkh_rOfGfAE&lang=zh_CN");


        try {
            //String res = new AsyncHttpClient("proxy.it.taikang.com", "8080", "http").requestListGet(list).get(0);

            ResponseEntity<byte[]> res = RestHttpUtil.INSTANCE.restGetByte("https://api.weixin.qq.com/cgi-bin/media/get?access_token=7_O4ZxxSTRLiOqpb7JP9RcHwRTXz3D4GQcEqvbuBWf4vG6VQ19-aEfbEYg5dzCbXXliXu7WxplkGjEZ7K0WQgyRjRrpNXndacQmUOPZlEC3leQH9-4qzPT-aJhOG0GRPdAHANYW&media_id=lk3iUd-bFWoWIlRt3xHM6s6QoDRaVWAqlAc1voyVmTPHvifPKPJR-C6bx0cpKTuR", null,null);
            NIOUtil.byte2File(res.getBody(), "D://1.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
