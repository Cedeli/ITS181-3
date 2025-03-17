package its181.sa3.dogadoption.data.remote;

import java.util.List;

import its181.sa3.dogadoption.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Long id);

    @POST("/api/users")
    Call<User> registerUser(@Body User user);

    @GET("/api/users/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @DELETE("/api/users/{id}")
    Call<Void> deleteUser(@Path("id") Long id);
}
