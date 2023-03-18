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

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.smallway.icantbeoncampus.net.Okhttp;


public class Sign extends AppCompatActivity implements LocationListener {
    private TextView id, logid, title, type, time, status,jingdu,weidu;
    private Double latitude, longitude;
    private Button GPS, schoolGPS;
    private LocationManager locationManager;
    private String url="https://student.wozaixiaoyuan.com/sign/doSign.json/";

    @Override
    public void onLocationChanged(Location location) {
        // 在这里获取当前经纬度
        latitude = location.getLatitude(); // 获取纬度
        longitude = location.getLongitude(); // 获取经度
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
                OkHttpClient client2 = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", V_logid)
                        .add("signId", V_id)
                        .add("latitude",String.valueOf(latitude))
                        .add("longitude",String.valueOf(longitude))
                        .add("country","")
                        .add("province","")
                        .add("city","")
                        .add("district","")
                        .add("township","")
                        .build();
                Request request = new Request.Builder()
                        .url(url).post(requestBody)
                        .addHeader("Host", "student.wozaixiaoyuan.com")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .addHeader("Accept-Encoding", " deflate, br")
                        .addHeader("Accept-Language", "en-us,en")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("User-Agent", "")
                        .addHeader("Referer", "")
                        .addHeader("Content-Length", "500")
                        .addHeader("JWSESSION", MainActivity.get_JWSESSION())
                        .build();
            }
        });
        schoolGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", V_logid)
                        .add("signId", V_id)
                        .add("latitude","34.30702")
                        .add("longitude","108.72436")
                        .add("country","中国")
                        .add("province","陕西省")
                        .add("city","咸阳市")
                        .add("district","秦都区")
                        .add("township","钓台街道")
                        .build();
                Request request = new Request.Builder()
                        .url(url).post(requestBody)
                        .addHeader("Host", "student.wozaixiaoyuan.com")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")

                        .addHeader("Accept-Language", "en-us,en")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("User-Agent", "")
                        .addHeader("Referer", "")
                        .addHeader("Content-Length", "500")
                        .addHeader("JWSESSION", MainActivity.get_JWSESSION())
                        .build();
                try {
                    if (V_type.equals("未签到")){
                        Response response = client.newCall(request).execute();
                        jingdu.setText(response.body().toString());
                    }else{
                        Toast.makeText(Sign.this, "您已签到，不能重复签到哦！", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
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
        jingdu=findViewById(R.id.jingdu);
        weidu=findViewById(R.id.weidu);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

}