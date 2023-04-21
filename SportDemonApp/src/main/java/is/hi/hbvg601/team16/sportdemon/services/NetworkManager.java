package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.LoginData;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import retrofit2.Call;
import retrofit2.http.*;

public interface NetworkManager {
    @Headers("Content-Type: application/json")

    // Users

    @POST("/users/signup")
    Call<User> createAccount(@Body User user);

    @POST("/users/login")
    Call<LoginData> login(@Body User user); // Sendir User með einungis username og password

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") UUID id);

    @PUT("/users/{id}")
    Call<Void> updateUser(@Path("id") UUID id, @Body User user);

    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") UUID id);

    // Workouts

    @GET("/workouts/{id}")
    Call<Workout> getWorkout(@Path("id") UUID id);

    @POST("/workouts")
    Call<Workout> addWorkout(@Body Workout workout);

    @PUT("/workouts/{id}")
    Call<Void> updateWorkout(@Path("id") UUID id, @Body Workout workout);

    @PUT("/workouts/{id}")
    // Observable til að gera zip og senda mörg köll í einu
    Observable<Void> updateWorkoutObservable(@Path("id") UUID id, @Body Workout workout);


    @DELETE("/workouts/{id}")
    Call<Void> deleteWorkout(@Path("id") UUID id);

    // ExerciseCombos

    @GET("/exerciseCombos/{id}")
    Call<ExerciseCombo> getExerciseCombo(@Path("id") UUID id);

    @POST("/exerciseCombos")
    Call<ExerciseCombo> addExerciseCombo(@Body ExerciseCombo exerciseCombo);

    @PUT("/exerciseCombos/{id}")
    Call<Void> updateExerciseCombo(@Path("id") UUID id, @Body ExerciseCombo exerciseCombo);

    @PUT("/exerciseCombos/{id}")
    // Observable til að gera zip og senda mörg köll í einu
    Observable<Void> updateExerciseComboObservable(@Path("id") UUID id, @Body ExerciseCombo exerciseCombo);

    @DELETE("/exerciseCombos/{id}")
    Call<Void> deleteExerciseCombo(@Path("id") UUID id);

    // WorkoutResults

    @GET("/workoutResults/{id}")
    Call<WorkoutResult> getWorkoutResult(@Path("id") UUID id);

    @POST("/workoutResults")
    Call<WorkoutResult> addWorkoutResult(@Body WorkoutResult workoutResult);

    @PUT("/workoutResults/{id}")
    Call<Void> updateWorkoutResult(@Path("id") UUID id, @Body WorkoutResult workoutResult);

    @DELETE("/workoutResults/{id}")
    Call<Void> deleteWorkoutResult(@Path("id") UUID id);

}
