package top.smallway.icantbeoncampus.net;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Okhttp {
    private static final Okhttp instance = new Okhttp();
    private OkHttpClient okHttpClient = new OkHttpClient();
    private final static int READ_TIMEOUT = 120;

    private final static int CONNECT_TIMEOUT = 120;

    private final static int WRITE_TIMEOUT = 120;

    private Okhttp() {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        //读取超时
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //连接超时
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //写入超时
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        //自定义连接池最大空闲连接数和等待时间大小，否则默认最大5个空闲连接
        clientBuilder.connectionPool(new ConnectionPool(32, 5, TimeUnit.MINUTES));

        okHttpClient = clientBuilder.build();
    }

    public static Okhttp getInstance() {
        return instance;
    }

    public String doGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public String doPost(String string, String url) throws IOException {
        RequestBody requestBody = new FormBody.Builder().add("text", string).build();
        Request request = new Request.Builder().url(url).post(requestBody).addHeader("Connection", "close").build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public Map login_(String url) throws IOException {
        Map map = new HashMap();
//        定义返回值为Map
        RequestBody requestBody = new FormBody.Builder().add("", "").build();
//        定义请求体
        Request request = new Request.Builder().url(url).post(requestBody)
                .addHeader("Host", "student.wozaixiaoyuan.com")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "en-us,en")
                .addHeader("Connection", "keep-alive")
                .addHeader("User-Agent", "")
                .addHeader("Referer", "")
                .addHeader("Content-Length", "")
                .build();
//        定义请求头部
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        map.put("response", response.body().string());
//        添加response为Map中的值
        map.put("JWSESSION", response.headers().get("JWSESSION"));
//        添加JWSESSION为Map中的值
        return map;
    }

}



