package com.dxt2.eventbussdemo;
//https://github.com/LJYcoder/DevRing

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView tv_message;
    private Button bt_message;
    private Button bt_subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_message);
        tv_message.setText("MainActivity");
        bt_subscription = findViewById(R.id.bt_subscription);
        bt_subscription.setText("订阅事件");
        bt_message = findViewById(R.id.bt_message);
        bt_message.setText("跳转到SecondActivity");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
        //点击 注册
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EventBus.getDefault().isRegistered(MainActivity.this)) {
                    //1在需要订阅事件的地方注册事件
                    EventBus.getDefault().register(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "请勿重复注册事件", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //4.取消事件订阅
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //3.处理事件
    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行，用TextView来展示收到的事件消息：
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        tv_message.setText(messageEvent.getMessage());
    }

    @Subscribe(sticky = true)
    public void onMoonStickyEvent(MessageEvent messageEvent) {
        tv_message.setText(messageEvent.getMessage());
    }
//消息处理的方法可以随便取名，但是需要添加一个注解@Subscribe，并且要指定线程模型（默认为POSTING）。
}
