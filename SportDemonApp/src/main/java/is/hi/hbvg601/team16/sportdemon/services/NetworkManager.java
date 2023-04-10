package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkManager {
    @Headers("Content-Type: application/json")

    // User Service

    @GET("/users/id/{id}")
    Call<User> getUser(@Path("id") UUID id);

    @GET("/users/username/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("/signup")
    Call<User> createAccount(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user); // Sendir User með einungis username og password

    // Workout Service

    @POST("/add/workout")
    Call<Workout> addWorkout(@Body Workout workout);

    // Exercise Service

    @POST("/add/exercisecombo")
    Call<ExerciseCombo> addExerciseCombo(@Body ExerciseCombo ec);

}
