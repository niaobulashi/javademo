package com.niaobulashi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: javademo
 * @description: http请求
 * @author: hulang
 * @create: 2019-05-31 10:59
 */
public class CRMClientUtil {

    public CRMClientUtil(){

    }
    public static String sendPostJSON(String url, String param) {
        return sendPostJSON(url, param, 10000);
    }

    public static String sendCrmPostJSON(String url, String serviceId, String param,String requestId,String appId,String accessToken) {
        return sendPostJSON(url, serviceId, param,requestId,appId,accessToken, 400 * 1000);
    }
    // 机器人调用专用方法，超时时间定为400s
    public static String sendCrmPostJSONRpa(String url, String serviceId, String param,String requestId,String appId,String accessToken) {
        return sendPostJSONRPA(url, serviceId, param,requestId,appId,accessToken, 400 * 1000);
    }

    public static String sendPostJSON(String url, String serviceId, String param,String requestId,String appId,String accessToken, int timeout) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                    .build();
            method.setConfig(requestConfig);
            StringEntity entity = new StringEntity(param, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            method.setHeader("CAITC-REQUEST-ID", requestId);
            method.setHeader("CAITC-SERVICE-ID", serviceId);
            method.setHeader("CAITC-APP-ID", appId);
            method.setHeader("CAITC-ACCESS-TOKEN", accessToken);
            method.setHeader("CAITC-REQUEST-TIME", new Date().toString());
            method.setHeader("CAITC-REQUEST-HASH", "");
            method.setHeader("CAITC_REQUEST_EXT", "");

            method.setEntity(entity);
            CloseableHttpResponse result = httpClient.execute(method);
            String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
            return resData;
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~serviceId:" + serviceId + "," + e);
            return "";
        }
    }

    /**
     * RPA机器人专用发送JSON报文，如果为实时接口，请求之后中断响应
     */
    public static String sendPostJSONRPA(String url, String serviceId, String param,String requestId,String appId,String accessToken, int timeout) {
        try {
            final CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
            httpclient.start();
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                    .build();
            method.setConfig(requestConfig);
            StringEntity entity = new StringEntity(param, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            method.setHeader("CAITC-REQUEST-ID", requestId);
            method.setHeader("CAITC-SERVICE-ID", serviceId);
            method.setHeader("CAITC-APP-ID", appId);
            method.setHeader("CAITC-ACCESS-TOKEN", accessToken);
            method.setHeader("CAITC-REQUEST-TIME", new Date().toString());
            method.setHeader("CAITC-REQUEST-HASH", "");
            method.setHeader("CAITC_REQUEST_EXT", "");

            method.setEntity(entity);
//			CloseableHttpResponse result = httpClient.execute(method);
            httpclient.execute(method, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(final HttpResponse response) {
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("completed callback thread id is : " + Thread.currentThread().getId());
                }
                @Override
                public void failed(final Exception ex) {
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("failed callback thread id is : " + Thread.currentThread().getId());
                }
                @Override
                public void cancelled() {
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("cancelled callback thread id is : " + Thread.currentThread().getId());
                }

            });

            /*if (StringUtils.isNotEmpty(sync) && sync.equals(Constant.SYNC_REAL_TIME)) {
				httpclient.close();
			}*/
//			String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
            return null;
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~serviceId:" + serviceId + "," + e);
            return "";
        }
    }

    public static String sendPostJSON(String url, String param, int timeout) {
        try {
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
            return resData;
        } catch (Exception var9) {
            System.out.println("~~~~~~~~~~~~~~~" + var9);
            return "";
        }
    }

    public static String sendPostXML(String url, String param) {
        try {
            CloseableHttpClient e = HttpClients.createDefault();
            HttpPost method = new HttpPost(url);
            StringEntity entity = new StringEntity(param, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/xml");
            method.setEntity(entity);
            HttpResponse result = e.execute(method);
            String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
            return resData;
        } catch (Exception var7) {
            return "";
        }
    }

    public static String sendPostForm(String url, Map<String, String> param) {
        try {
            CloseableHttpClient e = HttpClients.createDefault();
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
            return "";
        }
    }
}

