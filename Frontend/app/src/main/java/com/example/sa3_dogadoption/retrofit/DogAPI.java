package com.example.sa3_dogadoption.retrofit;

import com.example.sa3_dogadoption.model.Dog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DogAPI {
    // Not working, need to fix
    @GET("/")
    Call<List<Dog>> getAllDogs();

    @POST("/add-dog")
    Call<Dog> add(@Body Dog dog);

    @POST("/update-dog")
    Call<Dog> update(@Body Dog dog);

    @POST("/delete-dog")
    Call<Dog> delete(@Body Dog dog);

    @POST("/get-dog")
    Call<Dog> getDogById(@Body Dog dog);

}
