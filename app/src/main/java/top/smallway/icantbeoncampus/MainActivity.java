package top.smallway.icantbeoncampus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.smallway.icantbeoncampus.net.Okhttp;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login;
    private String url = "https://gw.wozaixiaoyuan.com/basicinfo/mobile/login/username";
    private static Request request;
    private static String JWSESSION;
    private final Handler mHandler = new Handler(Looper.myLooper()) {


        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SignTable.class);
                    startActivity(intent);
                    finish();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_A = username.getText().toString();
                String password_A = password.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        try {
                            Message message = new Message();
                            request = Okhttp.getInstance().login_(url,username_A,password_A);
                            Response response = client.newCall(request).execute();
                            JWSESSION=response.headers().get("JWSESSION");
                            JSONObject jsonObject= JSON.parseObject(response.body().string());
                            message.what= (int) jsonObject.get("code");
                            if (message.what==0){
                                message.obj=JWSESSION;
                            }else {
                                message.obj=jsonObject.get("message");
                            }
                            mHandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    private void initview() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }


    public  static String get_JWSESSION(){
        return JWSESSION;
    }

    public static Request get_Request(){
        return request;
    }
}