package its181.sa3.dogadoption.api;

import java.util.List;

import its181.sa3.dogadoption.data.model.Dog;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DogApiService {
    @GET("/api/dogs")
    Call<List<Dog>> getAllDogs(@Query("adopted") Boolean adopted);

    @GET("/api/dogs/{id}")
    Call<Dog> getDogById(@Path("id") Long id);

    @POST("/api/dogs")
    Call<Dog> addDog(@Body Dog dog);

    @PUT("/api/dogs/{id}")
    Call<Dog> updateDog(@Path("id") Long id, @Body Dog dog);

    @PUT("/api/dogs/{id}/adopt")
    Call<Dog> adoptDog(@Path("id") Long id, @Query("userId") Long userId);

    @DELETE("/api/dogs/{id}")
    Call<Void> deleteDog(@Path("id") Long id);
}
