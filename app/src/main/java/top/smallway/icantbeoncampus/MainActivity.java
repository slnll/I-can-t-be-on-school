package top.smallway.icantbeoncampus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

import top.smallway.icantbeoncampus.net.Okhttp;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login;
    private String url = "https://gw.wozaixiaoyuan.com/basicinfo/mobile/login/username";
    private String JWSESSION;
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
                String url2 = url + "?username=" + username_A + "&password=" + password_A;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message message = new Message();
                            Map respond = Okhttp.getInstance().login_(url2);
                            String responds = (String) respond.get("response");
                            JWSESSION = (String) respond.get("JWSESSION");
                            if (Integer.parseInt(JSONObject.parseObject(responds).getString("code")) == 0) {
                                message.obj = "登陆成功";
                                message.what = Integer.parseInt(JSONObject.parseObject(responds).getString("code"));
                            } else {
                                message.what = Integer.parseInt(JSONObject.parseObject(responds).getString("code"));
                                message.obj = JSONObject.parseObject(responds).getString("message");
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

    public String get_JWSESSION() {
        return JWSESSION;
    }
}