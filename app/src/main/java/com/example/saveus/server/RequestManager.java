package com.example.saveus.server;


import com.example.model.Login;
import com.example.model.LoginResponse;
import com.example.model.Result;
import com.example.model.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestManager {




    public static Observable<Result<LoginResponse>> login(User user) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        return service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }



    public static Observable<Result<LoginResponse>> getUser(String token) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        return service.getUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }






}
