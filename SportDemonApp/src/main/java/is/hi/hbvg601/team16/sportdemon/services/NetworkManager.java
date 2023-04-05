package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

import retrofit2.Call;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkManager {
    @Headers("Content-Type: application/json")

    // User Service

    @GET("/users/id/{id}") // TODO: Breyta í rétta slóð
    Call<User> getUser(@Path("id") UUID id);
    @GET("/users/username/{username}") // TODO: Breyta í rétta slóð
    Call<User> getUser(@Path("username") String username);

    @POST("/signup")
    Call<User> createAccount(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user); // Sendir User með einungis username og password

    // Workout Service



    // Exercise Service

    

}
