package com.dxt2.eventbussdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {
    private Button bt_message;
    private TextView tv_message;
    private Button bt_subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_message=(TextView)this.findViewById(R.id.tv_message);
        tv_message.setText("SecondActivity");

        bt_message = (Button)this.findViewById(R.id.bt_message);
        bt_message.setText("发送事件");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2.发送事件
                EventBus.getDefault().post(new MessageEvent("欢迎关注博客"));
                finish();
            }
        });

        bt_subscription=(Button)this.findViewById(R.id.bt_subscription);
        bt_subscription.setText("发送粘性事件");
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2.发送事件
                EventBus.getDefault().post(new MessageEvent("粘性事件"));
                finish();
            }
        });


    }

}
