package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.LoginData;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.UserServiceImplementation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public UserService mUserService;

    private ProgressBar progressBar;
    private ConstraintLayout form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mUserService = new UserServiceImplementation(nmAPI);

        EditText usernameEdit = findViewById(R.id.login_username);
        EditText passwordEdit = findViewById(R.id.login_password);

        progressBar = findViewById(R.id.login_progress);
        form = findViewById(R.id.login_form);

        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            showLoading();
            Call<LoginData> callSync = mUserService.login(username, password);

            callSync.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                    if (response.isSuccessful()) {
                        // Get response
                        try {
                            if (response.body() != null) {
                                // UI
                                Toast.makeText(LoginActivity.super.getApplicationContext(),
                                        "Login completed",
                                        Toast.LENGTH_SHORT
                                ).show();
                                hideLoading();

                                // Vinnsla úr öllum gögnum
                                LoginData loginData = response.body();
                                User loginUser = loginData.getUser();

                                List<Workout> workoutList = loginData.getWorkoutList();
                                // exerciseComboMap inniheldur key value pair þar sem
                                // key er id Workouts og value er ExerciseCombo listi þess
                                Map<UUID, List<ExerciseCombo>> exerciseComboMap =
                                        loginData.getExerciseComboMap();
                                // Fyllum Workout hlutina með ExerciseCombo lista frá mappinu
                                for (Workout workout : workoutList) {
                                    workout.setExerciseComboList(exerciseComboMap.get(workout.getId()));
                                }

                                loginUser.setWorkoutList(workoutList);
                                loginUser.setWorkoutResultList(loginData.getWorkoutResultList());

                                Intent skil = new Intent();
                                skil.putExtra("USER", loginUser);
                                setResult(RESULT_OK, skil);
                                finish();
                            } else {
                                // UI
                                Toast.makeText(LoginActivity.super.getApplicationContext(),
                                        "Username and/or password incorrect. Try again.",
                                        Toast.LENGTH_SHORT
                                ).show();
                                hideLoading();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.super.getApplicationContext(),
                                    e.toString(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            hideLoading();
                        }
                    } else {
                        Toast.makeText(LoginActivity.super.getApplicationContext(),
                                response.code()+" - "+response,
                                Toast.LENGTH_SHORT
                        ).show();
                        hideLoading();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.super.getApplicationContext(),
                            t.toString(),
                            Toast.LENGTH_SHORT
                    ).show();
                    hideLoading();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserService = null;
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        form.setVisibility(View.INVISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        form.setVisibility(View.VISIBLE);
    }
}
