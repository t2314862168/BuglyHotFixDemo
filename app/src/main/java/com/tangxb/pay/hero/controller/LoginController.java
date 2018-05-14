package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.entity.UserEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by tangxuebing on 2018/5/8.
 */

public class LoginController {
    /**
     * 构建一个登录的用户
     *
     * @return
     */
    public Observable<UserEntity> login() {
        Observable<UserEntity> observable = Observable.create(new ObservableOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<UserEntity> e) throws Exception {
                e.onNext(new UserEntity());
            }
        }).delay(1500L, TimeUnit.MILLISECONDS);
        return observable;
    }
}
