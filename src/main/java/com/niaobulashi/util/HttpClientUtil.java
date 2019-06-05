package com.niaobulashi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: javademo
 * @description: http客户请求
 * @author: hulang
 * @create: 2019-06-05 11:25
 */
public class HttpClientUtil {

    private static Logger logger= LoggerFactory.getLogger(HttpClientUtil.class);

    private static final String UTF8_STRING = "UTF-8";

    public void HttpClientUtil() {
    }

    public static String sendPostJSON(String url, String param) {
        return sendPostJSON(url, param, 10000);
    }

    public static String sendPostJSON(String url, String param, int timeout) {
        try {
            logger.info("发送Http请求:sendPostJSON-start");
            logger.info("sendPostJSON-data: \n"+param);

            CloseableHttpClient e = HttpClients.createDefault();
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            method.setConfig(requestConfig);
            StringEntity entity = new StringEntity(param, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            CloseableHttpResponse result = e.execute(method);
            String resData = EntityUtils.toString(result.getEntity(), "UTF-8");

            logger.info("发送Http请求:sendPostJSON-end");
            logger.info("sendPostJSON-resData: \n"+resData);
            return resData;
        } catch (Exception var9) {
            logger.error("发送Http请求异常:sendPostJSON-error");
            logger.error(var9.getMessage(),var9);
            return "";
        }
    }

    public static String sendPostXML(String url, String param) {
        return sendPostXML(url, param, 10000);
    }

    public static String sendPostXML(String url, String param, int timeout) {
        try {
            DefaultHttpClient e = new DefaultHttpClient();
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            method.setConfig(requestConfig);
            StringEntity entity = new StringEntity(param, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/xml");
            method.setEntity(entity);
            HttpResponse result = e.execute(method);
            String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
            return resData;
        } catch (Exception var9) {
            logger.error("Http请求发送异常:sendPostXML-error");
            logger.error(var9.getMessage(),var9);
            return "";
        }
    }

    public static String sendPostForm(String url, Map<String, String> param) {
        try {
            DefaultHttpClient e = new DefaultHttpClient();
            HttpPost method = new HttpPost(url);
            ArrayList formparams = new ArrayList();
            Iterator entity = param.entrySet().iterator();

            while(entity.hasNext()) {
                Map.Entry result = (Map.Entry)entity.next();
                formparams.add(new BasicNameValuePair((String)result.getKey(), (String)result.getValue()));
            }

            UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(formparams, "UTF-8");
            method.setEntity(entity1);
            HttpResponse result1 = e.execute(method);
            String resData = EntityUtils.toString(result1.getEntity(), "UTF-8");
            return resData;
        } catch (Exception var8) {
            logger.error("Http请求发送异常:sendPostForm-error");
            logger.error(var8.getMessage(),var8);
            return "";
        }
    }
}

