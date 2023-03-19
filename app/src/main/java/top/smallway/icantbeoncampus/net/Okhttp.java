package top.smallway.icantbeoncampus.net;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
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



    public String get(String location)throws IOException{
        OkHttpClient client = new OkHttpClient();
        String url="https://apis.map.qq.com/ws/geocoder/v1/";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("location", location)
                .addQueryParameter("key", "QNEBZ-4TKRD-XQQ4L-P3UUT-3RG2Q-URFIO");
//        转换成String url
        String Url = urlBuilder.build().toString();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(Url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }



    public Request login_(String url, String username, String password) throws IOException {
//        定义request Body
        RequestBody requestBody = new FormBody.Builder().add("", "").build();

//        定义Query值，将url传入
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("username", username)
                .addQueryParameter("password", password);
//        转换成String url
        String Url = urlBuilder.build().toString();
//        创建请求体 “session”
        Request request = new Request.Builder()
                .url(Url).post(requestBody)
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

        return request;
    }

}



