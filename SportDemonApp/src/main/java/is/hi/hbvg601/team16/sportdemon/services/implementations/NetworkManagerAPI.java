package is.hi.hbvg601.team16.sportdemon.services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.LoginData;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;

public class NetworkManagerAPI {

    private static NetworkManager mAPI;

    static {
        setupNetworkManagerAPI();
    }

    private static void setupNetworkManagerAPI() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Gson demonGson = new GsonBuilder()
                // Setja stillingar á Gson hér
                .serializeNulls()
                .setLenient()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd hh:mm")
                .create();
        RxJava3CallAdapterFactory rxAdapter = RxJava3CallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080") // Local server
                .baseUrl("https://sportdemonserver-production.up.railway.app") // Railway Server
                .addConverterFactory(GsonConverterFactory.create(demonGson))
                .addCallAdapterFactory(rxAdapter)
                .client(httpClient.build())
                .build();

        mAPI = retrofit.create(NetworkManager.class);
    }

    /*
     * Network manager föll til að senda á server
     * Notar NetworkManager.java fyrir köll
     */

    // User Service

    /**
     * @param user sem á að vista
     * @return call til server repo, til að execute-a í activity
     */
    public Call<User> createAccount(User user) {
        return mAPI.createAccount(user);
    }

    public Call<LoginData> login(String username, String password) {
        User user = new User(username, password, "");
        return mAPI.login(user);
    }

    public Call<User> getUser(UUID id) {
        return mAPI.getUser(id);
    }

    // Workout Service

    public Call<Workout> addWorkout(Workout workout) {
        return mAPI.addWorkout(workout);
    }

    public Call<Workout> findWorkoutByID(UUID id) {
        return mAPI.getWorkout(id);
    }

    public Call<Void> updateWorkout(Workout workout) {
        return mAPI.updateWorkout(workout.getId(), workout);
    }

    public Observable<Void> updateWorkoutObservable(Workout workout) {
        return mAPI.updateWorkoutObservable(workout.getId(), workout);
    }

    public Call<Void> deleteWorkout(UUID id) {
        return mAPI.deleteWorkout(id);
    }

    // ExerciseCombo Service

    public Call<ExerciseCombo> addExerciseCombo(ExerciseCombo ec) {
        return mAPI.addExerciseCombo(ec);
    }

    public Call<Void> updateExerciseCombo(ExerciseCombo exerciseCombo) {
        return mAPI.updateExerciseCombo(exerciseCombo.getId(), exerciseCombo);
    }

    public Observable<Void> updateExerciseComboObservable(ExerciseCombo exerciseCombo) {
        return mAPI.updateExerciseComboObservable(exerciseCombo.getId(), exerciseCombo);
    }

    public Call<Void> deleteExerciseCombo(UUID id) {
        return mAPI.deleteExerciseCombo(id);
    }

    // Workout Result Service

    public Call<WorkoutResult> getWorkoutResult(UUID id) {
        return mAPI.getWorkoutResult(id);
    }

    public Call<WorkoutResult> addWorkoutResult(WorkoutResult wr) {
        return mAPI.addWorkoutResult(wr);
    }

    public Call<Void> deleteWorkoutResult(UUID id) {
        return mAPI.deleteWorkoutResult(id);
    }

}
