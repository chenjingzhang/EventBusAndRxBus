package com.dxt2.rxbuss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final int Send = 0x131;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doSubsribe();
    }

    private void doSubsribe() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(NewsModel.class)//得到一个Observable对象
                //在io线程进行订阅，可以执行一些耗时操作
                .subscribeOn(Schedulers.io())
                //在主线程进行观察，可做UI更新操作
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsModel>() {
                    @Override
                    public void call(NewsModel newsModel) {
                        switch (newsModel.getStatus()) {
                            case Send:
                                Log.e("rxbus", newsModel.getMsg());
                                break;
                            default:
                                break;

                        }
                    }
                });
        RxBus.getInstance().addSubscription(this, subscription);
    }
    //通过一个Button发送事件：
    //在需要发送事件的地方调用post()方法，它间接的通过mSubject.onNext(o);将事件发送给订阅者。
    public void onclick(View view) {
        RxBus.getInstance().post(new NewsModel(Send,"fagongyitiaomessage"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);


    }
}
