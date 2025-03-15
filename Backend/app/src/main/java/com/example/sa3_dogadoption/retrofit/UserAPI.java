package com.example.sa3_dogadoption.retrofit;

import com.example.sa3_dogadoption.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface UserAPI {
    @GET("/")
    Call<List<User>> getAllUsers();

    @POST("/add-user")
    Call<User> save (@Body User user);
}
