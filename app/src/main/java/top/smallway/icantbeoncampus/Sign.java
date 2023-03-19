package top.smallway.icantbeoncampus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.smallway.icantbeoncampus.net.Okhttp;


public class Sign extends AppCompatActivity implements LocationListener {
    private TextView id, logid, title, type, time, status, jingdu, weidu, cs;
    private Double latitude, longitude;
    private Button GPS, schoolGPS;
    private LocationManager locationManager;
    private String url = "http://student.wozaixiaoyuan.com/sign/doSign.json/";
    private String h;

    @Override
    public void onLocationChanged(Location location) {
        // 在这里获取当前经纬度
        latitude = location.getLatitude(); // 获取纬度
        longitude = location.getLongitude(); // 获取经度
        jingdu.setText(String.valueOf(longitude));
        weidu.setText(String.valueOf(latitude));
        Log.d("MainActivity", "Latitude: " + latitude + ", Longitude: " + longitude);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止位置更新
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initview();
        Intent intent = getIntent();
        String V_id = intent.getStringExtra("id");
        String V_logid = intent.getStringExtra("logId");
        String V_title = intent.getStringExtra("title");
        String V_time = intent.getStringExtra("time");
        String V_type = intent.getStringExtra("type");
        String V_status = intent.getStringExtra("status");
        id.setText("signId:" + V_id);
        logid.setText("id:" + V_logid);
        title.setText(V_title);
        type.setText(V_type);
        time.setText(V_time);
        if (V_status.equals("已签到")) {
            status.setBackgroundResource(R.color.gree);
        } else {
            status.setBackgroundResource(R.color.red);
        }
        status.setText(V_status);


        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Response response = null;
                        String location = latitude + "," + longitude;
                        Log.d("location", location);
                        String res;

                        {
                            try {
                                res = Okhttp.getInstance().get(location);
                                JSONObject jsonObject = JSON.parseObject(res);
                                String REAL_location = JSON.parseObject(jsonObject.getString("result")).getString("ad_info");
                                String nation = JSON.parseObject(REAL_location).getString("nation");
                                String city = JSON.parseObject(REAL_location).getString("city");
                                String district = JSON.parseObject(REAL_location).getString("district");
                                String province = JSON.parseObject(REAL_location).getString("province");
                                OkHttpClient client2 = new OkHttpClient();
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("id", V_logid);
                                jsonObject1.put("signId", V_id);
                                jsonObject1.put("latitude", String.valueOf(latitude));
                                jsonObject1.put("longitude", String.valueOf(longitude));
                                jsonObject1.put("country", nation);
                                jsonObject1.put("province", province);
                                jsonObject1.put("city", city);
                                jsonObject1.put("district", district);
                                jsonObject1.put("township", "");
                                String json = jsonObject1.toString();
                                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                                Request request = new Request.Builder()
                                        .url(url).post(requestBody)
                                        .addHeader("Host", "student.wozaixiaoyuan.com")
                                        .addHeader("Content-Type", "application/json")
                                        .addHeader("Accept-Encoding", "gzip, deflate, br")
                                        .addHeader("Connection", "keep-alive")
                                        .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 11; V2055A Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/4277 MMWEBSDK/20220706 Mobile Safari/537.36 MMWEBID/815 MicroMessenger/8.0.25.2200(0x2800193B) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 miniProgram/wxce6d08f781975d91")
                                        .addHeader("Referer", "https://gw.wozaixiaoyuan.com/h5/mobile/basicinfo/index/login/index")
                                        .addHeader("Content-Length", "500")
                                        .addHeader("Cookie", "")
                                        .addHeader("JWSESSION", MainActivity.get_JWSESSION())
                                        .addHeader("charset", "utf-8")
                                        .build();
                                response = client2.newCall(request).execute();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                Toast.makeText(Sign.this, "签到完成，请不要多次签到哦！", Toast.LENGTH_SHORT).show();

            }
        });
        schoolGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", V_logid);
                jsonObject.put("signId", V_id);
                jsonObject.put("latitude", 34.30702);
                jsonObject.put("longitude", 108.72436);
                jsonObject.put("country", "中国");
                jsonObject.put("province", "陕西省");
                jsonObject.put("city", "咸阳市");
                jsonObject.put("district", "秦都区");
                jsonObject.put("township", "钓台街道");
                String json = jsonObject.toString();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                Request request = new Request.Builder()
                        .url(url).post(requestBody)
                        .addHeader("Host", "student.wozaixiaoyuan.com")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept-Encoding", "gzip, deflate, br")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 11; V2055A Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/4277 MMWEBSDK/20220706 Mobile Safari/537.36 MMWEBID/815 MicroMessenger/8.0.25.2200(0x2800193B) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 miniProgram/wxce6d08f781975d91")
                        .addHeader("Referer", "https://gw.wozaixiaoyuan.com/h5/mobile/basicinfo/index/login/index")
                        .addHeader("Content-Length", "500")
                        .addHeader("Cookie", "")
                        .addHeader("JWSESSION", MainActivity.get_JWSESSION())
                        .addHeader("charset", "utf-8")
                        .build();

                if (V_status.equals("未签到")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Response response = null;
                            try {
                                response = client.newCall(request).execute();
                                status.setText("已签到");
                                status.setBackgroundResource(R.color.gree);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                cs.setText("签到成功"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(Sign.this, "您已签到，不能重复签到哦！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initview() {
        id = findViewById(R.id.id);
        logid = findViewById(R.id.logid);
        title = findViewById(R.id.S_title);
        type = findViewById(R.id.S_type);
        time = findViewById(R.id.S_time);
        status = findViewById(R.id.S_status);
        GPS = findViewById(R.id.GPS);
        schoolGPS = findViewById(R.id.schoolGPS);
        jingdu = findViewById(R.id.jingdu);
        weidu = findViewById(R.id.weidu);
        cs = findViewById(R.id.cs);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }


}