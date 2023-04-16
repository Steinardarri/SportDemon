package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkManager {
    @Headers("Content-Type: application/json")

    // User Service

    @POST("/users/signup")
    Call<User> createAccount(@Body User user);

    @POST("/users/login")
    Call<User> login(@Body User user); // Sendir User með einungis username og password

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") UUID id);

    @PUT("/users/{id}")
    Call<User> updateUser(@Path("id") UUID id);

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") UUID id);

    // Workout Service

    @POST("/add/workout")
    Call<Workout> addWorkout(@Body Workout workout);

    // Exercise Service

    @POST("/add/exercisecombo")
    Call<ExerciseCombo> addExerciseCombo(@Body ExerciseCombo ec);

}
