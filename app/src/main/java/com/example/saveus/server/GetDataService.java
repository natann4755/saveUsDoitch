package com.example.saveus.server;

import com.example.model.Login;
import com.example.model.LoginResponse;
import com.example.model.Result;
import com.example.model.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface GetDataService {

    @POST("/api/app/login")
    Observable<Result<LoginResponse>> login(@Body User user);

    @GET("/api/app/user")
    Observable<Result<LoginResponse>> getUser(@Header("Authorization") String token);

}
