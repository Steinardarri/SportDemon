package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.rxjava3.core.Observable;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.ui.workoutactivity.ExerciseComboRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutActivity extends AppCompatActivity {

    private WorkoutService mWorkoutService;
    private HomeService mHomeService;

    // Intent code
    private static final int RESULT_SUCCESS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);
        this.mHomeService = new HomeServiceImplementation(nmAPI);

        Workout mWorkout = mHomeService.getCurrentWorkout(this);
        TextView title = findViewById(R.id.workout_title);
        title.setText(mWorkout.getTitle());

        // Setja upp RecyclerView fyrir ExerciseCombos
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        mECRecyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));


        ExerciseComboRecyclerViewAdapter adapter = new ExerciseComboRecyclerViewAdapter(
                WorkoutActivity.this, mWorkout.getExerciseCombo()
        );
        adapter.setClickListener(
                (View v, int position, List<ExerciseCombo> data) -> {
                    Intent i = new Intent(
                            WorkoutActivity.this, ExerciseComboActivity.class
                    );
                    i.putExtra("EXERCISECOMBO", data.get(position));
                    exerciseComboResultLauncher.launch(i);
                });
        mECRecyclerView.setAdapter(adapter);

        findViewById(R.id.workout_add_button).setOnClickListener(v -> {
            ExerciseCombo newEC = new ExerciseCombo();
            newEC.setWorkout(mWorkout);

            SpotsDialog loadingDialog = new SpotsDialog(this, "Setting up new Exercise");
            loadingDialog.show();
            // Setja upp nýtt ExerciseCombo á server
            Call<ExerciseCombo> callSync = mWorkoutService.saveExerciseCombo(newEC);
            callSync.enqueue(new Callback<ExerciseCombo>() {
                @Override
                public void onResponse(@NonNull Call<ExerciseCombo> call, @NonNull Response<ExerciseCombo> response) {
                    if (response.isSuccessful()) {
                        // Get response
                        try {
                            loadingDialog.dismiss();
                            Intent i = new Intent(WorkoutActivity.this, ExerciseComboActivity.class);
                            i.putExtra("EXERCISECOMBO", response.body());
                            exerciseComboResultLauncher.launch(i);
                        } catch (Exception e) {
                            // UI
                            Toast.makeText(WorkoutActivity.this,
                                    e.toString(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            loadingDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(WorkoutActivity.this,
                                response.message(),
                                Toast.LENGTH_SHORT
                        ).show();
                        loadingDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExerciseCombo> call, @NonNull Throwable t) {
                    Toast.makeText(WorkoutActivity.this,
                            t.toString(),
                            Toast.LENGTH_SHORT
                    ).show();
                    loadingDialog.dismiss();
                }
            });
        });

        findViewById(R.id.workout_edit_button).setOnClickListener(v -> {
            // TODO edit button
        });
    }

    private final ActivityResultLauncher<Intent> exerciseComboResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                SpotsDialog loadingDialog = new SpotsDialog(this, "Loading");
                loadingDialog.show();
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    assert data != null;
                    ExerciseCombo ec = (ExerciseCombo) data.getSerializableExtra("EXERCISECOMBO");

                    Workout w = mHomeService.getCurrentWorkout(this);
                    w.addExerciseCombo(ec);
                    mHomeService.setCurrentWorkout(w,this);

                    User u = mHomeService.getCurrentUser(this);
                    u.addWorkout(w);
                    mHomeService.setCurrentUser(u, this);

                    saveToServer();
                }
                refreshList();
                loadingDialog.dismiss();
            }
    );

    private void refreshList() {
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        ExerciseComboRecyclerViewAdapter adapter =
                (ExerciseComboRecyclerViewAdapter) mECRecyclerView.getAdapter();
        assert adapter != null;
        Workout w = mHomeService.getCurrentWorkout(this);
        if(w != null) adapter.setData(w.getExerciseCombo());
        mECRecyclerView.setAdapter(adapter);
    }

    private void saveToServer() {
        Workout workout = mHomeService.getCurrentWorkout(this);
        Call<Workout> callSync = mWorkoutService.saveWorkout(workout);

        SpotsDialog loadingDialog = new SpotsDialog(this, "Saving to server");
        loadingDialog.show();
        callSync.enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
                if (response.isSuccessful()) {
                    // UI
                    Toast.makeText(WorkoutActivity.this,
                            "Saving successful",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
                Toast.makeText(WorkoutActivity.this,
                        t.toString(),
                        Toast.LENGTH_SHORT
                ).show();
                loadingDialog.dismiss();
            }
        });
    }
}
