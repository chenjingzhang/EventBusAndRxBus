package com.dxt2.rxbuss;

import java.io.Serializable;
import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class RxBus {
    private static volatile RxBus mInstance;
    private SerializedSubject<Object, Object> mSubject;
    private HashMap<String, CompositeSubscription> mSubscriptionMap;

    private RxBus() {
        //由于Subject类是非线程安全的，
        // 所以我们通过它的子类SerializedSubject将PublishSubject转换成一个线程安全的Subject对象
        // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    //发送事件
    public void post(Object o) {
        mSubject.onNext(o);
    }

    //通过mSubject.ofType(type);得到一个Observable对象,让其它订阅者来订阅
    //返回指定类型的Observable实例
    public <T> Observable<T> tObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    //是否已有观察者订阅
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    //一个默认的订阅方法
    public <T> Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        return tObservable(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /**
     * 保存订阅后的subscription
     *
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);

        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     *
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }

}































