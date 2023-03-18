package top.smallway.icantbeoncampus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignTable extends AppCompatActivity {
    private String url = "https://student.wozaixiaoyuan.com/sign/getSignMessage.json";
    private String type, status;
    private Person 数据;
    private RecyclerView recyclerView;
    private final Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(msg.obj));
            JSONArray jsonArray = JSONArray.parseArray(String.valueOf(jsonObject.get("data")));
            List<Person> personList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                if (obj.getString("area")==null) {
                    type = "定位签到";
                } else {
                    type = "校区签到(" + obj.getString("area")+")";
                }
                String start = obj.getString("start");
                String end = obj.getString("end");
                String time = start + "至" + end;
                String id=obj.getString("id");
                String logId=obj.getString("logId");
                if (obj.getString("type").equals("1")) {
                    status = "已签到";
                } else {
                    status = "未签到";
                }

                数据 = new Person(title, type, time, status,id,logId);
                personList.add(数据);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SignTable.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                MyAdapter myAdapter = new MyAdapter(personList);
                recyclerView.addItemDecoration(new SpaceItemDecoration(5));
                recyclerView.setAdapter(myAdapter);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_table);
        initview();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    String res = get_signTable(url);
                    Log.d("TAG", res);
                    message.obj = res;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initview() {
        recyclerView = findViewById(R.id.sign_table);
    }


    private String get_signTable(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("page", "1")
                .add("size", "5")
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
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}